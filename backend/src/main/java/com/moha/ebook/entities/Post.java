package com.moha.ebook.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post")
    private long idPost;

    @Column(name = "contenu")
    private String contenu;

    @Column(name = "date_post")
    private LocalDate datePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_livre")
    private Livre livre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Commentaire> commentaires;
}
