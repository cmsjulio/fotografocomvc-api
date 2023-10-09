package com.fotografocomvc.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="T_BASEUSER")
public class BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_BASEUSER", nullable = false, unique = true)
    private Long id;

    @Column(name = "ST_USERNAME", nullable = false, unique = true)
    private String username; //email

    @Column(name = "ST_PASSWORD", nullable = false)
    private String password;

    @OneToOne(mappedBy = "baseUser")
    private Photographer photographer;

    @OneToOne(mappedBy = "baseUser")
    private Customer customer;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "T_BASEUSER_ROLE", joinColumns = @JoinColumn(name = "FK_USER", referencedColumnName = "ID_BASEUSER"),
            inverseJoinColumns = @JoinColumn(name = "FK_ROLE", referencedColumnName = "ID_ROLE"))
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "baseUser")
    private List<AccessToken> accessToken;

    @OneToMany(mappedBy = "baseUser")
    private List<RefreshToken> refreshToken;


}
