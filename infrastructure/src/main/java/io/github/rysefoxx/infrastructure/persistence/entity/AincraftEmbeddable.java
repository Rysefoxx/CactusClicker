package io.github.rysefoxx.infrastructure.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Rysefoxx
 * @since 22.04.2025
 */
@Embeddable
@Getter
@Setter
public class AincraftEmbeddable {
  private int test = 3;

}