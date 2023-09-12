package com.moha.ebook.dto;

import com.moha.ebook.entities.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;

}
