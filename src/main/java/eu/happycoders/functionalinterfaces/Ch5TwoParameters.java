package eu.happycoders.functionalinterfaces;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import eu.happycoders.streams.Book;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public class Ch5TwoParameters {

  static void main() throws IOException {
    System.out.println("== BiFunction: apply() and andThen()");
    BiFunction<String, Integer, String> label = (title, year) -> title + " (" + year + ")";
    System.out.println(label.apply("Dracula", 1897));
    System.out.println(label.andThen(String::toUpperCase).apply("Dracula", 1897));

    System.out.println("== BiConsumer: Map.forEach()");
    Map<String, List<Book>> byAuthor =
        BOOKS.stream().collect(groupingBy(Book::author, TreeMap::new, toList()));
    BiConsumer<String, List<Book>> printCount =
        (author, books) -> System.out.println(author + ": " + books.size());
    byAuthor.forEach(printCount);

    System.out.println("== BiFunction: Map.computeIfPresent() and Map.replaceAll()");
    Map<String, Integer> booksPerAuthor = new TreeMap<>();
    for (Book book : BOOKS) {
      booksPerAuthor.merge(book.author(), 1, Integer::sum);
    }
    booksPerAuthor.computeIfPresent("Jules Verne", (author, count) -> count + 1);
    System.out.println(booksPerAuthor.get("Jules Verne"));
    booksPerAuthor.replaceAll((author, count) -> count * 10);
    System.out.println(booksPerAuthor.get("Jules Verne"));

    System.out.println("== BiPredicate: Files.find()");
    BiPredicate<Path, BasicFileAttributes> isJavaFile =
        (path, attributes) -> attributes.isRegularFile() && path.toString().endsWith(".java");
    try (Stream<Path> files = Files.find(Path.of("src/main/java"), 10, isJavaFile)) {
      System.out.println(files.count() + " Java files");
    }
  }
}
