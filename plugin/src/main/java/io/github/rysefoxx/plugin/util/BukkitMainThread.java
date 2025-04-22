package io.github.rysefoxx.plugin.util;

import io.github.rysefoxx.plugin.CactusClickerPlugin;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.logging.Level;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class to run tasks on the main thread.
 *
 * @author Rysefoxx
 * @since 22.04.2025
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BukkitMainThread {

  /**
   * Run a task and handle the result on the main thread.
   *
   * @param future    The future to run
   * @param onSuccess The success handler
   * @param onError   The error handler
   * @param <T>       The type of the result
   */
  public static <T> void of(@NotNull CompletableFuture<T> future, Consumer<? super T> onSuccess, Consumer<? super Throwable> onError) {
    future.thenAcceptAsync(onSuccess, runOnMainThread())
        .exceptionally(ex -> {
          runOnMainThread().execute(() -> {
            JavaPlugin.getPlugin(CactusClickerPlugin.class).getLogger().log(Level.SEVERE, "An error occurred while executing a task", ex);
            onError.accept(ex);
          });
          return null;
        });
  }

  @Contract(pure = true)
  private static @NotNull Executor runOnMainThread() {
    return runnable -> Bukkit.getServer().getScheduler().runTask(JavaPlugin.getPlugin(CactusClickerPlugin.class), runnable);
  }
}