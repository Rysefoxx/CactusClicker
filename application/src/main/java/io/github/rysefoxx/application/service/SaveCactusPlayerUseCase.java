package io.github.rysefoxx.application.service;

import io.github.rysefoxx.application.port.out.CactusPlayerRepositoryPort;
import io.github.rysefoxx.core.domain.model.CactusPlayer;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;

/**
 * Service class for saving a CactusPlayer.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
@RequiredArgsConstructor
public class SaveCactusPlayerUseCase {

  private final CactusPlayerRepositoryPort repository;

  /**
   * Saves a CactusPlayer asynchronously.
   *
   * @param cactusPlayer the CactusPlayer to save
   * @return a CompletableFuture that completes when the save operation is done
   */
  public CompletableFuture<Void> handleAsync(CactusPlayer cactusPlayer) {
    return this.repository.saveAsync(cactusPlayer);
  }

}