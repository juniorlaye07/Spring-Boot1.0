package com.devweb.rh.repository;

import com.devweb.rh.modele.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository //crer un objet et garde dans les authowides
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query("SELECT u from User u WHERE u.login IS null ")//requete jpql :requete specifiques en cas de besoin
    public List<User> users();
}