package com.titan.tune.domain.repository;

import com.titan.tune.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; //  C'est cet import qui manquait !
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
    // Maintenant que List est importé, cette ligne va compiler sans erreur
    List<User> findByRole(String role); 
}