package com.esdp.demo_esdp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserUpdateForm {
    @NotBlank
    @Email
    private String email = "";

    private Long id;

    @Size(min=4, max=24, message = "Length must be >= 4 and <= 24")
    @Pattern(regexp = "^[^\\d\\s]+$", message = "Should contain only letters")
    private String name = "";

    @Size(min=4, max=24, message = "Length must be >= 4 and <= 24")
    @Pattern(regexp = "^[^\\d\\s]+$", message = "Should contain only letters")
    private String lastName = "";

    @Size(min=13,max = 13,message = "Length must be = 13, format +996552902002")
    @NotBlank
    @Pattern(regexp = "^(\\+)[0-9]+$", message = "Should contain only numbers")
    private String telNumber = "";

    @NotBlank
    private String login = "";

}
