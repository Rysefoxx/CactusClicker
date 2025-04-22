package CactusClicker.extensions.org.bukkit.entity.Player;

import io.github.rysefoxx.core.domain.model.CactusPlayer;
import io.github.rysefoxx.plugin.CactusClickerPlugin;
import io.github.rysefoxx.plugin.service.CactusPlayerService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Extension methods for the {@link Player} class.
 */
@SuppressWarnings("unused")
@Extension
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerExtension {

  private static final CactusPlayerService CACTUS_PLAYER_SERVICE = JavaPlugin.getPlugin(CactusClickerPlugin.class).getContext().getBean(CactusPlayerService.class);

  /**
   * Returns the CactusPlayer instance for the given player.
   *
   * @param player the player to get the CactusPlayer for
   * @return the CactusPlayer instance
   */
  public static CactusPlayer asCactusPlayer(@This @NotNull Player player) {
    if (CACTUS_PLAYER_SERVICE.isLoaded(player.getUniqueId())) {
      return CACTUS_PLAYER_SERVICE.get(player.getUniqueId());
    }

    throw new IllegalStateException("CactusPlayer is not loaded for this player.");
  }

}