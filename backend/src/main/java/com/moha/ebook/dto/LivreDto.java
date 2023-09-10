package com.moha.ebook.dto;

import com.moha.ebook.entities.Categorie;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LivreDto {

    private Long idLivre;
    private String titre;
    private String auteur;
    private String description;
    private String image;
    private String path;
    private LocalDate datePublication;
    private List<Categorie> categories;
    private Long idUtilisateur;

}
