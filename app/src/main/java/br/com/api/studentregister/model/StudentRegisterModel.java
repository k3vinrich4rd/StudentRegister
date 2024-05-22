package br.com.api.studentregister.model;

import br.com.api.studentregister.enums.StatusRegistrationEnum;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "student_register")
public class StudentRegisterModel implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(length = 70, nullable = false, name = "name_student")
    String nameStudent;

    @Column(length = 14, nullable = false, unique = true)
    String cpf;

    @Column(length = 100, nullable = false)
    String address;

    @Column(length = 20, nullable = false, name = "birth_date")
    LocalDate birthDate;

    @Column(length = 70, nullable = false, name = "responsible_1")
    String responsible1;

    @Column(length = 70, name = "responsible_2")
    String responsible2;

    @Column(length = 13, nullable = false, name = "cell_phone")
    String cellPhone;

    @Column(length = 13, nullable = false, name = "contact_emergency")
    String contactEmergency;

    @Enumerated(EnumType.STRING)
    StatusRegistrationEnum statusRegistrationEnum;

    public StudentRegisterModel(String nameStudent, String cpf, String address, LocalDate birthDate, String responsible1, String responsible2, String contactEmergency, String cellPhone) {
        this.nameStudent = nameStudent;
        this.cpf = cpf;
        this.address = address;
        this.birthDate = birthDate;
        this.responsible1 = responsible1;
        this.responsible2 = responsible2;
        this.contactEmergency = contactEmergency;
        this.cellPhone = cellPhone;
    }

}
