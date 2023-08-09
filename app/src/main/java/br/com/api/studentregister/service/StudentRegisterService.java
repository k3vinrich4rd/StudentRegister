package br.com.api.studentregister.service;


import br.com.api.studentregister.enums.ExceptionsEnums;
import br.com.api.studentregister.enums.StatusRegistrationEnum;
import br.com.api.studentregister.exception.ObjectNotFoundException;
import br.com.api.studentregister.exception.UnprocessableEntityException;
import br.com.api.studentregister.model.StudentRegisterModel;
import br.com.api.studentregister.model.dto.request.StudentRegisterRequestDto;
import br.com.api.studentregister.model.dto.request.StudentRegisterUpdateRequestDto;
import br.com.api.studentregister.model.dto.response.StudentRegisterResponseDto;
import br.com.api.studentregister.repository.IStudentRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentRegisterService {
    private final IStudentRegisterRepository iStudentRegisterRepository;

    @Autowired
    public StudentRegisterService(IStudentRegisterRepository iStudentRegisterRepository) {
        this.iStudentRegisterRepository = iStudentRegisterRepository;
    }

    public StudentRegisterResponseDto registerStudent(StudentRegisterRequestDto studentRegisterRequestDto) {
        StudentRegisterModel studentRegisterModel = studentRegisterRequestDto.convertStudentRegisterToRequestDto();
        studentRegisterModel.setStatusRegistrationEnum(StatusRegistrationEnum.ACTIVE);

        StudentRegisterModel registerModel = iStudentRegisterRepository.save(studentRegisterModel);
        return StudentRegisterResponseDto.convertStudentRegisterToResponseDto(registerModel);
    }

    public List<StudentRegisterModel> searchStudentRegister() {
        return iStudentRegisterRepository.searchStudentsWithRegistrationStatusActive();
    }

    //Cenário de uso: Estudante passa mal
    //Funcionário da escola que não tem autorização para acessar os registro de todos estudantes
    public StudentRegisterModel getStudentRegisteredInTheSystemByCpf(String cpf) {
        Optional<StudentRegisterModel> studentRegisterOptional = iStudentRegisterRepository.findByCpf(cpf);
        if (studentRegisterOptional.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        StudentRegisterModel studentRegisterModelFound = studentRegisterOptional.get();
        if (!studentRegisterModelFound.getStatusRegistrationEnum().equals(StatusRegistrationEnum.ACTIVE)) {
            throw new UnprocessableEntityException(ExceptionsEnums.FOUND_BUT_INACTIVE.getMessage());
        }

        return studentRegisterModelFound;
    }

    public List<StudentRegisterResponseDto> getStudentUsingDto() {
        List<StudentRegisterModel> studentRegisterModelList =
                iStudentRegisterRepository.searchStudentsWithRegistrationStatusActive();
        return studentRegisterModelList
                .stream()
                .map(StudentRegisterResponseDto::new)
                .collect(Collectors.toList());
    }

    public void inactiveStudentRegister(String cpf) {
        Optional<StudentRegisterModel> studentRegisterModelOptional = iStudentRegisterRepository.findByCpf(cpf);
        if (studentRegisterModelOptional.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        StudentRegisterModel studentRegisterModel = studentRegisterModelOptional.get();
        if (studentRegisterModel.getStatusRegistrationEnum().equals(StatusRegistrationEnum.ACTIVE)) {
            studentRegisterModel.setStatusRegistrationEnum(StatusRegistrationEnum.INACTIVE);
            iStudentRegisterRepository.save(studentRegisterModel);
            return;
        }
        throw new ObjectNotFoundException();
    }

    public StudentRegisterResponseDto changeStudentRegister(String cpf, StudentRegisterUpdateRequestDto updateRequestDto) {
        StudentRegisterModel studentRegistered = getStudentRegisteredInTheSystemByCpf(cpf);

        studentRegistered.setNameStudent(updateRequestDto.getNameStudent());
        studentRegistered.setBirthDate(updateRequestDto.getBirthDate());
        studentRegistered.setAddress(updateRequestDto.getAddress());
        studentRegistered.setResponsible1(updateRequestDto.getResponsible1());
        studentRegistered.setResponsible2(updateRequestDto.getResponsible2());
        studentRegistered.setCellPhone(updateRequestDto.getCellPhone());
        studentRegistered.setContactEmergency(updateRequestDto.getContactEmergency());
        studentRegistered.setStatusRegistrationEnum(updateRequestDto.getStatusRegistrationEnum());
        StudentRegisterModel studentRegisterModel = iStudentRegisterRepository.save(studentRegistered);
        return StudentRegisterResponseDto.convertStudentRegisterToResponseDto(studentRegisterModel);

    }

    public List<StudentRegisterModel> searchStudentRegisterInactive() {
        return iStudentRegisterRepository.searchStudentsWithRegistrationStatusInactive();
    }
}
