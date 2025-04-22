package CactusClicker.extensions.java.lang.String;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.jetbrains.annotations.NotNull;

/**
 * Extension methods for the {@link String} class.
 */
@SuppressWarnings("unused")
@Extension
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringExtension {

  /**
   * Interpolates the given string with the given arguments.
   *
   * @param template The template string.
   * @param args     The arguments to interpolate.
   * @return The interpolated string.
   */
  public static @NotNull String interpolate(@This String template, Object ... args) {
    String result = template;
    for (int i = 0; i < args.length; i++) {
      result = result.replace("{" + i + "}", args[i].toString());
    }
    return result;
  }
}