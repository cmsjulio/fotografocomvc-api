package com.fotografocomvc.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
