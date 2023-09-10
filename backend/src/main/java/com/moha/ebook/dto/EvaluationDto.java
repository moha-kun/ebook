package com.moha.ebook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EvaluationDto {

    private Long idEvaluation;
    private Double note;
    private Long idUtilisateur;
    private Long idLivre;


}
