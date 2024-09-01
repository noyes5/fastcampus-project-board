package com.mirishop.userservice.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotBlank
    @Size(min = 8, message = "Password의 크기는 8글자 이상이어야 합니다.")
    private String oldPassword;

    @NotBlank
    @Size(min = 8, message = "Password의 크기는 8글자 이상이어야 합니다.")
    private String newPassword;
}
