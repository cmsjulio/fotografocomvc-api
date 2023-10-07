package com.fotografocomvc.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="T_LOCATION")
public class Location {

    @Id
    @Column(name = "ID_LOCATION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "NM_LOCATION_CITY")
    private String locationCity;

    @Column(name = "NM_LOCATION_STATE")
    private String locationState;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private List<Photographer> photographer;
}
