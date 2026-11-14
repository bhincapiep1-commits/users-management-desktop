
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import java.util.Scanner;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ConsoleIO {

  private final Scanner scanner;
  private final java.io.PrintStream output;

  public String readRequired(final String prompt) {
    output.print(prompt);
    final String value = scanner.nextLine().trim();

    if (value.isEmpty()) {
      throw new IllegalArgumentException("This field is required.");
    }

    return value;
  }

  public String readOptional(final String prompt) {
    output.print(prompt);
    return scanner.nextLine().trim();
  }

  public int readInt(final String prompt) {
    output.print(prompt);
    final String value = scanner.nextLine().trim();

    try {
      return Integer.parseInt(value);
    } catch (final NumberFormatException exception) {
      throw new IllegalArgumentException("Please enter a valid integer.");
    }
  }

  public void println(final String message) {
    output.println(message);
  }

  public void println() {
    output.println();
  }

  public void printf(final String format, final Object... args) {
    output.printf(format, args);
  }
}