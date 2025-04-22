package io.github.rysefoxx.application.port.out;

import io.github.rysefoxx.core.domain.model.CactusPlayer;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

/**
 * Repository interface for managing CactusPlayer entities.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
public interface CactusPlayerRepositoryPort {

  /**
   * Finds a CactusPlayer by UUID. This happens synchronously.
   *
   * @param uuid The UUID of the player to find
   * @return An Optional containing the found CactusPlayer, or an empty Optional if not found
   */
  Optional<CactusPlayer> findById(@NotNull UUID uuid);

  /**
   * Saves the given CactusPlayer to the database. This happens synchronously.
   *
   * @param cactusPlayer The CactusPlayer to save
   */
  void save(@NotNull CactusPlayer cactusPlayer);

  /**
   * Finds a player by UUID asynchronously. If the player does not exist, it will be created and saved.
   *
   * @param uuid The UUID of the player to find
   * @return A CompletableFuture containing the found or newly created player
   */
  default CompletableFuture<CactusPlayer> findByIdAsync(@NotNull UUID uuid) {
    return CompletableFuture.supplyAsync(() ->
        findById(uuid).orElseGet(() -> {
          CactusPlayer cactusPlayer = new CactusPlayer(uuid);
          save(cactusPlayer);
          return cactusPlayer;
        })
    );
  }

  /**
   * Saves the given CactusPlayer asynchronously.
   *
   * @param cactusPlayer The CactusPlayer to save
   * @return A CompletableFuture that completes when the save operation is done
   */
  default CompletableFuture<Void> saveAsync(@NotNull CactusPlayer cactusPlayer) {
    return CompletableFuture.runAsync(() -> save(cactusPlayer));
  }
}