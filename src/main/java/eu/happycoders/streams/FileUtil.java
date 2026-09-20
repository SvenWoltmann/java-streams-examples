package eu.happycoders.streams;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/** Wraps the checked IOException of Files.readAllLines() so that a lambda can call it. */
public class FileUtil {

  private FileUtil() {}

  public static Stream<String> readLines(Path file) {
    try {
      return Files.readAllLines(file).stream();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
