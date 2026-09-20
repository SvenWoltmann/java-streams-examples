package eu.happycoders.streams;

import static eu.happycoders.streams.Library.BOOKS;

import java.io.IOException;
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
    Path a = Path.of("target", "a.txt");
    Path b = Path.of("target", "b.txt");
    Files.createDirectories(a.getParent());
    Files.writeString(a, "Dracula\n");
    Files.writeString(b, "Kidnapped\nMoby-Dick\n");
    List<Path> files = List.of(a, b);
    // Calling Files.readAllLines(file) directly in the flatMap() lambda does not
    // compile: the method throws the checked IOException, and Function.apply()
    // does not declare it. FileUtil.readLines() wraps it into UncheckedIOException
    // and returns a Stream, so a method reference is enough here.
    List<String> lines = files.stream().flatMap(FileUtil::readLines).toList();
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
      System.out.println(stream.count());
    } catch (IllegalStateException _) {
      System.out.println("IllegalStateException");
    }
  }
}
