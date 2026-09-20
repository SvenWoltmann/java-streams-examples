package eu.happycoders.streams;

import static eu.happycoders.streams.Library.BOOKS;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Ch8Mistakes {

  static void main() throws IOException {
    System.out.println("== side effects: don't");
    List<String> titles = new ArrayList<>();
    BOOKS.stream().filter(book -> book.year() > 1890).forEach(book -> titles.add(book.title()));
    System.out.println(titles);

    System.out.println("== side effects: do");
    List<String> titlesCollected =
        BOOKS.stream().filter(book -> book.year() > 1890).map(Book::title).toList();
    System.out.println(titlesCollected);

    System.out.println("== checked exceptions");
    Path a = Files.createTempFile("a", ".txt");
    Path b = Files.createTempFile("b", ".txt");
    Files.writeString(a, "Dracula\n");
    Files.writeString(b, "Kidnapped\nMoby-Dick\n");
    List<Path> files = List.of(a, b);
    // does not compile: Files.readAllLines() throws IOException
    // List<String> lines = files.stream()
    //     .flatMap(file -> Files.readAllLines(file).stream())
    //     .toList();
    List<String> lines = files.stream().flatMap(file -> readLines(file).stream()).toList();
    System.out.println(lines);

    System.out.println("== loop is fine");
    int total = 0;
    for (Book book : BOOKS) {
      total += book.title().length();
    }
    System.out.println(total);
    System.out.println(BOOKS.stream().mapToInt(book -> book.title().length()).sum());

    System.out.println("== reuse");
    Stream<Book> stream = BOOKS.stream();
    stream.forEach(book -> {});
    try {
      stream.count();
    } catch (IllegalStateException e) {
      System.out.println("IllegalStateException");
    }
  }

  static List<String> readLines(Path file) {
    try {
      return Files.readAllLines(file);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
