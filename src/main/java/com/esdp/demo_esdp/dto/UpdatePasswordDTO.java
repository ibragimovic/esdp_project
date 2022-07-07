package com.esdp.demo_esdp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdatePasswordDTO {

    @NotBlank
    String oldPassword = "";

    @NotBlank
    @Size(min = 8, message = "Length must be >= 8")
    String newPassword = "";

    @NotBlank
    @Size(min = 8, message = "Length must be >= 8")
    String repeatingPassword = "";
}
