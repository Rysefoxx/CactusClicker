package io.github.rysefoxx.core.domain.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Rysefoxx
 * @since 22.04.2025
 */
@Getter
@Setter
public class CactusPlayer {

  private final UUID uuid;
  private final AincraftData aincraftData;
  private final FactoryData factoryData;

  public CactusPlayer(@NotNull UUID uuid) {
    this.uuid = uuid;
    this.aincraftData = new AincraftData();
    this.factoryData = new FactoryData();
  }
}