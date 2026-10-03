package eu.happycoders.tomap;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.toMap;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Genre;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Ch4MapType {

  static void main() {
    System.out.println("== HashMap: order");
    Map<String, Integer> yearByTitle = BOOKS.stream().collect(toMap(Book::title, Book::year));
    System.out.println(yearByTitle.getClass().getName());
    yearByTitle.keySet().forEach(System.out::println);

    System.out.println("== LinkedHashMap: stream order");
    Map<String, Integer> inOrder =
        BOOKS.stream()
            .collect(toMap(Book::title, Book::year, (first, second) -> first, LinkedHashMap::new));
    inOrder.keySet().forEach(System.out::println);

    System.out.println("== EnumMap: first title per genre");
    Map<Genre, String> firstTitleByGenre =
        BOOKS.stream()
            .collect(
                toMap(
                    Book::genre,
                    Book::title,
                    (first, second) -> first,
                    () -> new EnumMap<>(Genre.class)));
    System.out.println(firstTitleByGenre);
  }
}
