package br.com.api.studentregister.model.dto.request;
import br.com.api.studentregister.enums.StatusRegistrationEnum;
import lombok.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Validated
@AllArgsConstructor(access = AccessLevel.PRIVATE)

public class StudentRegisterUpdateRequestDto {

    @Length(max = 70, message = "Error, the number of characters informed must be less than or equal to 70 characters")
    @NotBlank(message = " Error, the field 'nameStudent' was not informed")
    String nameStudent;

    @NotNull(message = "Error, the field 'birth date' was not informed")
    LocalDate birthDate;

    @NotBlank(message = " Error, the field 'address' was not informed")
    @Length(max = 100, message = "Error, the number of characters informed must be less than or equal to 100 characters")
    String address;

    @NotBlank(message = "Error, the field 'responsible1' was not informed")
    @Length(min = 4, message = "Error, the number of characters informed must be greater than 4 characters")
    @Length(max = 70, message = "Error, the number of characters informed must be less than or equal to 70 characters")
    String responsible1;

    @Length(max = 70, message = "Error, the number of characters informed must be less than or equal to 70 characters")
    String responsible2;

    @NotBlank(message = "Error, the field 'cellPhone' was not informed")
    @Length(min = 9, message = "Error, the number of characters informed must be greater than 9 characters")
    @Length(max = 13, message = "Error, the number of characters informed must be less than or equal to 11 characters")
    String cellPhone;

    @NotBlank(message = "Error, the field 'contactEmergency' was not informed")
    @Length(min = 9, message = "Error, the number of characters informed must be greater than 9 characters")
    @Length(max = 13, message = "Error, the number of characters informed must be less than or equal to 11 characters")
    String contactEmergency;

    @NotNull(message = "Error, the field 'statusRegistrationEnum' was not informed")
    StatusRegistrationEnum statusRegistrationEnum;

}
