package com.com.jwtdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="userupc", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Basic
    String dni;
    String nombres;
    String apellidos;
    @Column(unique = true, nullable = false)
    String email;
    String celular;
    @Column(unique = true, nullable = false)
    String username;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;
    Boolean enabled;
   // @OneToMany (fetch = FetchType.LAZY, mappedBy="user", cascade = CascadeType.ALL)
   // List<GastosViaje> gastosViajes;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
          return List.of(new SimpleGrantedAuthority(role.name()));
      //  return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
