package com.fotografocomvc.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="T_IMAGE")
public class Image {

    @Id
    @Column(name = "ID_IMAGE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "NM_NAME")
    private String name;

    @Column(name = "TP_FORMAT_TYPE")
    private String formatType;

    @Column(name = "BT_ORIGINAL_IMAGE", nullable = true, length = 100000)
    private byte[] originalImage;

    @Column(name = "BT_WATERMARKED_IMAGE", nullable = true, length = 100000)
    private byte[] watermarkedImage;

    @OneToMany(mappedBy = "profilePic")
    private List<Photographer> photographer;
}