package br.com.api.studentregister.model.dto.response;

import br.com.api.studentregister.enums.StatusRegistrationEnum;
import br.com.api.studentregister.model.StudentRegisterModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudentRegisterResponseDto {

    private String nameStudent;

    private LocalDate birthDate;

    private String address;

    private String responsible1;

    private String responsible2;

    private String cellPhone;

    String contactEmergency;

    private StatusRegistrationEnum statusRegistrationEnum;

    public static StudentRegisterResponseDto convertStudentRegisterToResponseDto(StudentRegisterModel model) {
        return new StudentRegisterResponseDto(model.getNameStudent(), model.getBirthDate(),
                model.getAddress(), model.getResponsible1(), model.getResponsible2(), model.getCellPhone(),
                model.getContactEmergency(), model.getStatusRegistrationEnum());
    }

    public StudentRegisterResponseDto(StudentRegisterModel studentRegisterModel) {
        this(
                studentRegisterModel.getNameStudent(),
                studentRegisterModel.getBirthDate(),
                studentRegisterModel.getAddress(),
                studentRegisterModel.getResponsible1(),
                studentRegisterModel.getResponsible2(),
                studentRegisterModel.getCellPhone(),
                studentRegisterModel.getContactEmergency(),
                studentRegisterModel.getStatusRegistrationEnum());
    }

}
