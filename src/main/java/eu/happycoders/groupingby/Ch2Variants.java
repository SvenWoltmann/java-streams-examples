package eu.happycoders.groupingby;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Genre;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Ch2Variants {

  static void main() {
    System.out.println("== Variant 1");
    Map<String, List<Book>> booksByAuthor = BOOKS.stream().collect(groupingBy(Book::author));
    booksByAuthor.get("H. G. Wells").forEach(System.out::println);

    System.out.println("== Variant 2");
    Map<Genre, Long> bookCountByGenre = BOOKS.stream().collect(groupingBy(Book::genre, counting()));
    System.out.println(bookCountByGenre);

    System.out.println("== Variant 3 EnumMap");
    Map<Genre, Long> bookCountByGenreEnum =
        BOOKS.stream()
            .collect(groupingBy(Book::genre, () -> new EnumMap<>(Genre.class), counting()));
    System.out.println(bookCountByGenreEnum);

    System.out.println("== Variant 3 LinkedHashMap");
    Map<Genre, Long> bookCountByGenreLinked =
        BOOKS.stream().collect(groupingBy(Book::genre, LinkedHashMap::new, counting()));
    System.out.println(bookCountByGenreLinked);

    System.out.println("== Variant 3 TreeMap");
    Map<String, List<String>> titlesByAuthor =
        BOOKS.stream()
            .collect(groupingBy(Book::author, TreeMap::new, mapping(Book::title, toList())));
    System.out.println(titlesByAuthor);
  }
}
