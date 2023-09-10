package com.moha.ebook.mapper;

import com.moha.ebook.dto.EvaluationDto;
import com.moha.ebook.entities.Evaluation;
import org.springframework.stereotype.Component;

@Component
public class EvaluationMapper {

    public EvaluationDto toEvaluationDto(Evaluation evaluation) {
        EvaluationDto evaluationDto = EvaluationDto.builder()
                .idEvaluation(evaluation.getIdEvaluation())
                .note(evaluation.getNote())
                .idUtilisateur(evaluation.getUtilisateur().getIdUtilisateur())
                .idLivre(evaluation.getLivre().getIdLivre())
                .build();
        return evaluationDto;
    }

    public Evaluation toEvaluation(EvaluationDto evaluationDto) {
        Evaluation evaluation = Evaluation.builder()
                .idEvaluation(evaluationDto.getIdEvaluation())
                .note(evaluationDto.getNote())
                .build();
        return evaluation;
    }

}
