package com.moha.ebook.mapper;

import com.moha.ebook.dto.LivreDto;
import com.moha.ebook.entities.Livre;
import org.springframework.stereotype.Component;

@Component
public class LivreMapper {

    public LivreDto toLivreDto(Livre livre) {
        LivreDto livreDto = LivreDto.builder()
                .idLivre(livre.getIdLivre())
                .titre(livre.getTitre())
                .auteur(livre.getAuteur())
                .description(livre.getDescription())
                .image(livre.getImage())
                .path(livre.getPath())
                .datePublication(livre.getDatePublication())
                .categories(livre.getCategories())
                .idUtilisateur(livre.getUtilisateur().getIdUtilisateur())
                .build();
        return livreDto;
    }

    public Livre toLivre(LivreDto livreDto) {
        Livre livre = Livre.builder()
                .idLivre(livreDto.getIdLivre())
                .titre(livreDto.getTitre())
                .auteur(livreDto.getAuteur())
                .description(livreDto.getDescription())
                .image(livreDto.getImage())
                .path(livreDto.getPath())
                .datePublication(livreDto.getDatePublication())
                .categories(livreDto.getCategories())
                .build();
        return livre;
    }

}
