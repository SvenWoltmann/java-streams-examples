package eu.happycoders.groupingby;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Genre;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Ch4Nested {

  static void main() {
    System.out.println("== Nested");
    Map<Genre, Map<String, List<String>>> titlesByAuthorByGenre =
        BOOKS.stream()
            .collect(
                groupingBy(
                    Book::genre,
                    () -> new EnumMap<>(Genre.class),
                    groupingBy(Book::author, TreeMap::new, mapping(Book::title, toList()))));
    titlesByAuthorByGenre.forEach(
        (genre, titlesByAuthor) -> System.out.println(genre + ": " + titlesByAuthor));

    System.out.println("== Access");
    System.out.println(titlesByAuthorByGenre.get(Genre.ADVENTURE).get("Jules Verne"));
  }
}
