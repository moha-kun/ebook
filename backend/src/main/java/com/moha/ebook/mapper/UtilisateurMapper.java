package com.moha.ebook.mapper;

import com.moha.ebook.dto.UtilisateurDto;
import com.moha.ebook.entities.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper {

    public UtilisateurDto toUtilisateurDto(Utilisateur utilisateur) {
        UtilisateurDto utilisateurDto = UtilisateurDto.builder()
                .idUtilisateur(utilisateur.getIdUtilisateur())
                .firstName(utilisateur.getFirstName())
                .lastName(utilisateur.getLastName())
                .email(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .enabled(utilisateur.getEnabled())
                .image(utilisateur.getImage())
                .build();
        return utilisateurDto;
    }

    public Utilisateur toUtilisateur(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = Utilisateur.builder()
                .idUtilisateur(utilisateurDto.getIdUtilisateur())
                .firstName(utilisateurDto.getFirstName())
                .lastName(utilisateurDto.getLastName())
                .email(utilisateurDto.getEmail())
                .password(utilisateurDto.getPassword())
                .enabled(utilisateurDto.getEnabled())
                .image(utilisateurDto.getImage())
                .build();
        return utilisateur;
    }

}
