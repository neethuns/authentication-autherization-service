package com.maveric.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {

        @Id
        private String userId;
        @NotBlank(message="First Name can not be blank")
        @Size(min=5,max=50)
        private String firstName;
        private String middleName;
        @NotBlank(message="Last Name can not be blank")
        @Size(min=5,max=50)
        private String lastName;
        @Length(min=10,max=15)
        private String phoneNumber;
        @NotBlank(message="Email can not be blank")
        @Email(message = "Check the EmailId ")
        private String email;
        @NotBlank(message="Address can not be blank")
        private String address;
        private LocalDate dateOfBirth;
        @NotBlank(message="Employee ID can not be blank")
        private String employeeId;
        private String bloodGroup;
        private String gender;
        @NotBlank(message="Password can not be blank")
        private String password;
        private Boolean isActive;

}