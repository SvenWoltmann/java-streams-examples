package eu.happycoders.streams;

import static eu.happycoders.streams.Library.BOOKS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Ch3Sources {

  static void main() throws IOException {
    System.out.println("== Collection.stream()");
    Stream<Book> fromList = BOOKS.stream();
    System.out.println(fromList.count());
    Set<String> authors = Set.of("Jane Austen", "Bram Stoker");
    System.out.println(authors.stream().sorted().toList());

    System.out.println("== Stream.of()");
    Stream<String> genres = Stream.of("Novel", "Gothic", "Adventure");
    System.out.println(genres.toList());

    System.out.println("== Arrays.stream()");
    String[] words = {"streams", "are", "lazy"};
    System.out.println(Arrays.stream(words).map(String::toUpperCase).toList());
    int[] years = {1813, 1818, 1851};
    System.out.println(Arrays.stream(years).sum());

    System.out.println("== primitive streams");
    IntStream oneToFive = IntStream.rangeClosed(1, 5);
    System.out.println(oneToFive.sum());
    System.out.println(IntStream.range(0, 3).boxed().toList());
    System.out.println(LongStream.of(1L, 2L, 3L).max().getAsLong());
    System.out.println(DoubleStream.of(1.5, 2.5).average().getAsDouble());

    System.out.println("== Files.lines()");
    Path file = Path.of("target", "books.txt");
    Files.createDirectories(file.getParent());
    Files.writeString(file, "Dracula\nFrankenstein\nKidnapped\n");
    try (Stream<String> lines = Files.lines(file)) {
      System.out.println(lines.filter(line -> line.startsWith("D")).toList());
    }

    System.out.println("== String.chars()");
    long vowels = "Moby-Dick".chars().filter(c -> "aeiou".indexOf(c) >= 0).count();
    System.out.println(vowels);

    System.out.println("== Stream.iterate() / generate()");
    System.out.println(Stream.iterate(1, n -> n * 2).limit(6).toList());
    System.out.println(Stream.iterate(1, n -> n < 100, n -> n * 2).toList());
    System.out.println(Stream.generate(() -> "x").limit(3).toList());

    System.out.println("== Stream.empty() / ofNullable()");
    System.out.println(Stream.empty().count());
    String maybeNull = null;
    System.out.println(Stream.ofNullable(maybeNull).count());
    System.out.println(Stream.ofNullable("Dracula").count());

    List<String> ignored = List.of();
    System.out.println(ignored.stream().count());
  }
}
