package io.github.rysefoxx.infrastructure.persistence.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing a CactusPlayer in the database.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
@Getter
@Setter
@Entity
public class CactusPlayerEntity {

  @Id
  private UUID uuid;
  @Embedded
  private AincraftEmbeddable aincraftData = new AincraftEmbeddable();
  @Embedded
  private FactoryEmbeddable factoryData = new FactoryEmbeddable();

}