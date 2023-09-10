package com.moha.ebook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UtilisateurDto {

    private Long idUtilisateur;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean enabled;
    private String image;

}
