package io.github.rysefoxx.plugin.listener;

import io.github.rysefoxx.plugin.service.CactusPlayerService;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listener for player connection events.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PlayerConnectionListener implements Listener {

  private final CactusPlayerService cactusPlayerService;

  @EventHandler
  private void onJoin(@NotNull PlayerJoinEvent event) {
    Player player = event.getPlayer();
    player.sendMessage("Lade deinen CactusPlayer...");

    this.cactusPlayerService.load(player,
        cactusPlayer -> {
          player.sendMessage("CactusPlayer wurde erfolgreich geladen.");
        },
        throwable -> player.sendMessage("Es ist ein Fehler beim Laden der Daten aufgetreten."));
  }

  @EventHandler
  private void onQuit(@NotNull PlayerQuitEvent event) {
    Player player = event.getPlayer();
    this.cactusPlayerService.unload(player);
  }

}