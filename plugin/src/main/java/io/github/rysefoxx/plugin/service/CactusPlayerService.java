package io.github.rysefoxx.plugin.service;

import io.github.rysefoxx.application.service.LoadCactusPlayerUseCase;
import io.github.rysefoxx.application.service.SaveCactusPlayerUseCase;
import io.github.rysefoxx.application.service.UnloadCactusPlayerUseCase;
import io.github.rysefoxx.core.domain.model.CactusPlayer;
import io.github.rysefoxx.plugin.util.BukkitMainThread;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Cache for CactusPlayer instances.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public final class CactusPlayerService {

  private final LoadCactusPlayerUseCase loadCactusPlayerUseCase;
  private final UnloadCactusPlayerUseCase unloadCactusPlayerUseCase;
  private final SaveCactusPlayerUseCase saveCactusPlayerUseCase;

  private final Map<UUID, CactusPlayer> cache = new ConcurrentHashMap<>();

  /**
   * Gets the CactusPlayer for the given player.
   *
   * @param uuid the UUID of the player
   * @return the CactusPlayer for the given player
   */
  public CactusPlayer get(@NotNull UUID uuid) {
    return this.cache.get(uuid);
  }

  /**
   * Saves the CactusPlayer for the given player.
   *
   * @param cactusPlayer the CactusPlayer to save
   */
  public void save(@NotNull CactusPlayer cactusPlayer) {
    BukkitMainThread.of(
        this.saveCactusPlayerUseCase.handleAsync(cactusPlayer),
        unused -> {
        },
        Throwable::log
    );
  }

  /**
   * Loads a CactusPlayer for the given player.
   *
   * @param player    the player to load the CactusPlayer for
   * @param onSuccess the callback to be executed on success
   * @param onError   the callback to be executed on error
   */
  public void load(Player player,
                   Consumer<CactusPlayer> onSuccess,
                   Consumer<Throwable> onError) {
    loadInternal(player, onSuccess, onError);
  }

  /**
   * Unloads the CactusPlayer for the given player.
   *
   * @param player the player to unload the CactusPlayer for
   */
  public void unload(Player player) {
    unloadInternal(player);
  }

  /**
   * Checks if the CactusPlayer for the given player is loaded.
   *
   * @param uuid the UUID of the player
   * @return true if the CactusPlayer is loaded, false otherwise
   */
  public boolean isLoaded(UUID uuid) {
    return this.cache.containsKey(uuid);
  }

  private void loadInternal(@NotNull Player player,
                            Consumer<CactusPlayer> onSuccess,
                            Consumer<Throwable> onError) {
    BukkitMainThread.of(
        this.loadCactusPlayerUseCase.handleAsync(player.getUniqueId()),
        cactusPlayer -> {
          this.cache.put(player.getUniqueId(), cactusPlayer);
          onSuccess.accept(cactusPlayer);
        },
        onError
    );
  }

  private void unloadInternal(@NotNull Player player) {
    CactusPlayer cactusPlayer = this.cache.remove(player.getUniqueId());
    if (cactusPlayer == null) {
      return;
    }

    BukkitMainThread.of(
        this.unloadCactusPlayerUseCase.handleAsync(cactusPlayer),
        unused -> {
        },
        Throwable::log
    );
  }
}