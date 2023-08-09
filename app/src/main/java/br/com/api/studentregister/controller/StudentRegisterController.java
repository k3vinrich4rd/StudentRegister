package br.com.api.studentregister.controller;

import br.com.api.studentregister.model.StudentRegisterModel;
import br.com.api.studentregister.model.dto.request.StudentRegisterRequestDto;
import br.com.api.studentregister.model.dto.request.StudentRegisterUpdateRequestDto;
import br.com.api.studentregister.model.dto.response.StudentRegisterResponseDto;
import br.com.api.studentregister.service.StudentRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/students")
public class StudentRegisterController {

    private final StudentRegisterService service;

    @Autowired
    public StudentRegisterController(StudentRegisterService service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<StudentRegisterResponseDto> registerStudent(@Valid @RequestBody StudentRegisterRequestDto studentRegisterRequestDto) {
        StudentRegisterResponseDto responseDto = service.registerStudent(studentRegisterRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/active")
    public ResponseEntity<List<StudentRegisterModel>> searchStudentRegister() {
        return ResponseEntity.ok(service.searchStudentRegister());
    }

    @GetMapping(path = "/{cpf}")
    public ResponseEntity<StudentRegisterModel> getStudentRegisterByCpf(@PathVariable String cpf) {
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
