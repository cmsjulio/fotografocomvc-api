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
@Table(name = "T_ACCESS_TOKEN")
public class AccessToken {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID_ACCESS_TOKEN")
  private long id;

  @ManyToOne
  @JoinColumn(name = "FK_USER", referencedColumnName = "ID_BASEUSER")
  private BaseUser baseUser;

  @Column(nullable = false, unique = true)
  private String tokenString;

  public boolean isRevoked = false;

  public boolean isExpired = false;


}
