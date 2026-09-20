package eu.happycoders.streams;

import static eu.happycoders.streams.Library.BOOKS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
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

    System.out.println("== the loop wins");
    int[][] matrix = {
      {1, 0, 2},
      {0, 2, 0},
      {2, 1, 0}
    };
    System.out.println(findAllWithLoop(matrix, 2));
    System.out.println(findAllWithStream(matrix, 2));

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

  /** Coordinates of every cell that holds the given value - as a nested loop. */
  static List<String> findAllWithLoop(int[][] matrix, int value) {
    List<String> hits = new ArrayList<>();
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[row].length; col++) {
        if (matrix[row][col] == value) {
          hits.add(row + "/" + col);
        }
      }
    }
    return hits;
  }

  /** The same thing as a stream pipeline. */
  static List<String> findAllWithStream(int[][] matrix, int value) {
    return IntStream.range(0, matrix.length)
        .boxed()
        .flatMap(
            row ->
                IntStream.range(0, matrix[row].length)
                    .filter(col -> matrix[row][col] == value)
                    .mapToObj(col -> row + "/" + col))
        .toList();
  }
}
