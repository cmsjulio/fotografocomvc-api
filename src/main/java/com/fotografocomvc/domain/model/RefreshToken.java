package com.fotografocomvc.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_REFRESH_TOKEN")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @OneToOne
  @JoinColumn(name = "FK_BASEUSER", referencedColumnName = "ID_BASEUSER")
  private BaseUser baseUser;

  @Column(nullable = false, unique = true)
  private String tokenString;

  @Column(nullable = false)
  private Instant expiryDate;


}
