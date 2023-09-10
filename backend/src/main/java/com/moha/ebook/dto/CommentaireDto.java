package com.moha.ebook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CommentaireDto {

    private Long idCommentaire;
    private String contenu;
    private LocalDate dateCommentaire;
    private Long idPost;
    private Long idUtilisateur;

}
