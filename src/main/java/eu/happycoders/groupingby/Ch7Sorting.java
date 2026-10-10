package eu.happycoders.groupingby;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Genre;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Ch7Sorting {

  static void main() {
    System.out.println("== Sort by value");
    Map<String, Long> bookCountByAuthor =
        BOOKS.stream().collect(groupingBy(Book::author, counting()));

    Map<String, Long> sortedByCount =
        bookCountByAuthor.entrySet().stream()
            .sorted(
                Map.Entry.<String, Long>comparingByValue()
                    .reversed()
                    .thenComparing(Map.Entry.comparingByKey()))
            .collect(
                toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (first, second) -> first,
                    LinkedHashMap::new));
    System.out.println(sortedByCount);

    System.out.println("== Top 3");
    List<String> topAuthors =
        bookCountByAuthor.entrySet().stream()
            .sorted(
                Map.Entry.<String, Long>comparingByValue()
                    .reversed()
                    .thenComparing(Map.Entry.comparingByKey()))
            .limit(3)
            .map(Map.Entry::getKey)
            .toList();
    System.out.println(topAuthors);

    System.out.println("== Sort within groups");
    Map<Genre, List<String>> sortedTitlesByGenre =
        BOOKS.stream()
            .sorted(comparing(Book::title))
            .collect(
                groupingBy(
                    Book::genre, () -> new EnumMap<>(Genre.class), mapping(Book::title, toList())));
    sortedTitlesByGenre.forEach((genre, titles) -> System.out.println(genre + "=" + titles));
  }
}
