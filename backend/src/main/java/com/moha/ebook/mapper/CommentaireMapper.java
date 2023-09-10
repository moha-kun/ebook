package com.moha.ebook.mapper;

import com.moha.ebook.dto.CommentaireDto;
import com.moha.ebook.entities.Commentaire;
import org.springframework.stereotype.Component;

@Component
public class CommentaireMapper {

    public CommentaireDto toCommentaireDto(Commentaire commentaire) {
        CommentaireDto commentaireDto = CommentaireDto.builder()
                .idCommentaire(commentaire.getIdCommentaire())
                .dateCommentaire(commentaire.getDateCommentaire())
                .contenu(commentaire.getContenu())
                .idUtilisateur(commentaire.getUtilisateur().getIdUtilisateur())
                .idPost(commentaire.getPost().getIdPost())
                .build();
        return commentaireDto;
    }

    public Commentaire toCommentaire(CommentaireDto commentaireDto) {
        Commentaire commentaire = Commentaire.builder()
                .idCommentaire(commentaireDto.getIdCommentaire())
                .dateCommentaire(commentaireDto.getDateCommentaire())
                .contenu(commentaireDto.getContenu())
                .build();
        return commentaire;
    }

}
