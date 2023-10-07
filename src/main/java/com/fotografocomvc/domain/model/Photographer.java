package com.fotografocomvc.domain.model;

import com.fotografocomvc.domain.model.enums.Gender;
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
@Table(name = "T_PHOTOGRAPHER")
public class Photographer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_PHOTOGRAPHER", nullable = false, unique = true)
    private Long id;

    @Column(name = "ST_NAME", nullable = false)
    private String name;

    @Column(name = "TP_GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "ST_BIO")
    private String bio;

    @Column(name = "ST_PHONE")
    private String phone;

    @Column(name = "ST_ABOUTME")
    private String aboutMe;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_BASEUSER_FK", referencedColumnName = "ID_BASEUSER")
    private BaseUser baseUser;

    @Column(name = "NM_SHORT_INFO")
    private String shortInfo;

    @ManyToOne
    @JoinColumn(name = "FK_LOCATION", referencedColumnName = "ID_LOCATION")
    private Location location;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_IMAGE_FK", referencedColumnName = "ID_IMAGE")
    private Image profilePic;

}
