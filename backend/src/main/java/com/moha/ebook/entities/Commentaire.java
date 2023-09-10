package com.moha.ebook.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "commentaire")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commentaire")
    private Long idCommentaire;

    @Column(name = "contenu")
    private String contenu;

    @Column(name = "date_commentaire")
    private LocalDate dateCommentaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_post")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;
}
