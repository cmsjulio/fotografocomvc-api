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
@Table(name = "T_GALLERY")
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_GALLERY", nullable = false, unique = true)
    private Long id;

    //OneToOne  - Photographer (nullable=false)

    //OneToMany - Photo

}
