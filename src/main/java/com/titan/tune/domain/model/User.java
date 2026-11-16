package com.titan.tune.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User implements UserDetails {
    @Id
    @Column(name = "tracking_id")
    private String trackingId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; //  Assure-toi que ce champ s'appelle bien "password"

    private String firstName;
    private String lastName;
    private String alias;
    @Column(length = 1000)
    private String description;

    private String phone;
    private String avatarUrl;
    private Boolean isPremium;
    private String premiumPlan;
    private Double monthlyPrice;
    private String role;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (isPremium == null) isPremium = false;
        if (monthlyPrice == null) monthlyPrice = 0.0;
        if (role == null) role = "USER";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
    
    @Override public String getUsername() { return email; }
    
    //  On force la méthode ici pour éviter le conflit avec UserDetails
    @Override public String getPassword() { return password; } 
    
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}