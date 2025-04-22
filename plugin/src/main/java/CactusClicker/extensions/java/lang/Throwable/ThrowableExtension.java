package CactusClicker.extensions.java.lang.Throwable;

import io.github.rysefoxx.plugin.CactusClickerPlugin;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Extension methods for the {@link Throwable} class.
 */
@SuppressWarnings("unused")
@Extension
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThrowableExtension {

  /**
   * Logs the given throwable with the given message.
   *
   * @param ex The throwable to log.
   */
  public static void log(@This @NotNull Throwable ex) {
    Logger logger = JavaPlugin.getPlugin(CactusClickerPlugin.class).getLogger();
    logger.log(Level.SEVERE, "An error occurred: {0}".interpolate(ex.getMessage()));
  }

}