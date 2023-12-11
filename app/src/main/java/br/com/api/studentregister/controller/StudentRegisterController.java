package br.com.api.studentregister.controller;

import br.com.api.studentregister.model.StudentRegisterModel;
import br.com.api.studentregister.model.dto.request.StudentRegisterRequestDto;
import br.com.api.studentregister.model.dto.request.StudentRegisterUpdateRequestDto;
import br.com.api.studentregister.model.dto.response.StudentRegisterResponseDto;
import br.com.api.studentregister.service.StudentRegisterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Log4j2
@RequestMapping(path = "/students")
public class StudentRegisterController {

    private final StudentRegisterService service;

    @Autowired
    public StudentRegisterController(StudentRegisterService service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentRegisterResponseDto> registerStudent(@Valid @RequestBody StudentRegisterRequestDto studentRegisterRequestDto) {
        StudentRegisterResponseDto responseDto = service.registerStudent(studentRegisterRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/active")
    public ResponseEntity<List<StudentRegisterModel>> searchStudentRegister() {
        return ResponseEntity.ok(service.searchStudentRegister());
    }

    @GetMapping(path = "pageable/active") //Inserindo paginação
    public ResponseEntity<Page<StudentRegisterModel>> searchStudentRegister(@PageableDefault(size = 1, sort = {"name_student"}, page = 0) Pageable pageable) {
        return ResponseEntity.ok(service.searchStudentRegisterPageable(pageable));
    }

    @GetMapping(path = "/{cpf}")
    public ResponseEntity<StudentRegisterModel> getStudentRegisterByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(service.getStudentRegisteredInTheSystemByCpf(cpf));

    }

    //serve para pegar os dados dos usuários autenticados
    //Dentre isso, o seu nível de autenticação e autorização
    @GetMapping(path = "by-cpf/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentRegisterModel> getStudentRegisterByCpfAuthenticationPrincipal(@PathVariable("cpf") String cpf,
                                                                                               @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        return ResponseEntity.ok(service.getStudentRegisteredInTheSystemByCpf(cpf));
    }

    @GetMapping(path = "/reduced")
    public ResponseEntity<List<StudentRegisterResponseDto>> searchStudentsUsingDto() {
        return ResponseEntity.ok(service.getStudentUsingDto());
    }

    @GetMapping(path = "/inactive")
    public ResponseEntity<List<StudentRegisterModel>> searchStudentRegisterInactive() {
        return ResponseEntity.ok(service.searchStudentRegisterInactive());
    }

    @PutMapping(path = "/{cpf}")
    @Transactional
    public ResponseEntity<StudentRegisterResponseDto> changeStudentRegister(@PathVariable String cpf, @RequestBody @Valid StudentRegisterUpdateRequestDto updateRequestDto) {
        return ResponseEntity.ok(service.changeStudentRegister(cpf, updateRequestDto));
    }

    @DeleteMapping(path = "/inactive/{cpf}")
    @Transactional
    public void inactiveStudentRegister(@PathVariable String cpf) {
        service.inactiveStudentRegister(cpf);
    }
}
