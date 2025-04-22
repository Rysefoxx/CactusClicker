package io.github.rysefoxx.application.service;

import io.github.rysefoxx.application.port.out.CactusPlayerRepositoryPort;
import io.github.rysefoxx.core.domain.model.CactusPlayer;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;

/**
 * Service class for loading a CactusPlayer.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
@RequiredArgsConstructor
public class LoadCactusPlayerUseCase {

  private final CactusPlayerRepositoryPort repository;

  /**
   * Loads a CactusPlayer by UUID. If the player does not exist, it will be created and saved.
   *
   * @param uuid The UUID of the player to load
   * @return A CompletableFuture containing the loaded CactusPlayer
   */
  public CompletableFuture<CactusPlayer> handleAsync(UUID uuid) {
    return this.repository.findByIdAsync(uuid);
  }

}