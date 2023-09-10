package com.moha.ebook.dao;

import com.moha.ebook.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
    select t from Token t inner join Utilisateur u on t.utilisateur.idUtilisateur = u.idUtilisateur
    where u.idUtilisateur = :utilisateurId and (t.expired = false or t.revoked = false)
    """)
    public List<Token> findAllValidTokensByUtilisateur(Long utilisateurId);

    public Optional<Token> findByToken(String token);

}
