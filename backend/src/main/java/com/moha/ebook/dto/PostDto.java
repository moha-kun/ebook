package com.moha.ebook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PostDto {

    private long idPost;
    private String contenu;
    private LocalDate datePost;
    private long idUtilisateur;
    private long idLivre;

}
