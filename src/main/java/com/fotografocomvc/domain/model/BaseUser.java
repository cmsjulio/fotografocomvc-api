package com.fotografocomvc.domain.model;

import com.fotografocomvc.domain.model.enums.Role;
import jakarta.persistence.*;
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
    private String username;

    @Column(name = "ST_PASSWORD", nullable = false)
    private String password;

    @Column(name = "TP_ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "baseUser")
    private Photographer photographer;

    @OneToOne(mappedBy = "baseUser")
    private Customer customer;

    //OneToOne - Photographer (nullable=true)


}
