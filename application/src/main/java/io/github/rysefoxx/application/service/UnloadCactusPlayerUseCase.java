package io.github.rysefoxx.application.service;

import io.github.rysefoxx.application.port.out.CactusPlayerRepositoryPort;
import io.github.rysefoxx.core.domain.model.CactusPlayer;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;

/**
 * Service class for unloading a CactusPlayer.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
@RequiredArgsConstructor
public class UnloadCactusPlayerUseCase {

  private final CactusPlayerRepositoryPort repository;

  /**
   * Unloads a CactusPlayer asynchronously.
   *
   * @param cactusPlayer the CactusPlayer to unload
   * @return a CompletableFuture that completes when the unload operation is done
   */
  public CompletableFuture<Void> handleAsync(CactusPlayer cactusPlayer) {
    return this.repository.saveAsync(cactusPlayer);
  }
}