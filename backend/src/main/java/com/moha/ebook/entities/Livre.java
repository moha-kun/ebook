package com.moha.ebook.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "livre")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livre")
    private Long idLivre;

    @Column(name = "titre")
    private String titre;

    @Column(name = "auteur")
    private String auteur;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "path")
    private String path;

    @Column(name = "date_publication")
    private LocalDate datePublication;

    @ElementCollection(targetClass = Categorie.class)
    @Enumerated(EnumType.STRING)
    private List<Categorie> categories;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    @JsonBackReference
    private Utilisateur utilisateur;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "livre")
    private List<Evaluation> evaluations;
}
