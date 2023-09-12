package com.fotografocomvc.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_REFRESH_TOKEN")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID_REFRESH_TOKEN")
  private long id;

  @OneToOne(mappedBy = "refreshToken")
  private BaseUser baseUser;

  @Column(nullable = false, unique = true)
  private String tokenString;

  public boolean isRevoked;

  public boolean isExpired;


}
