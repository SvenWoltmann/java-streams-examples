package eu.happycoders.tomap;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Genre;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Ch8VsGroupingBy {

  static void main() {
    System.out.println("== Lists with toMap()");
    Map<String, List<String>> titlesByAuthor =
        BOOKS.stream()
            .collect(
                toMap(
                    Book::author,
                    book -> List.of(book.title()),
                    (first, second) -> Stream.concat(first.stream(), second.stream()).toList()));
    System.out.println(titlesByAuthor.get("Jules Verne"));

    System.out.println("== Lists with groupingBy()");
    Map<String, List<String>> grouped =
        BOOKS.stream().collect(groupingBy(Book::author, mapping(Book::title, toList())));
    System.out.println(grouped.get("Jules Verne"));

    System.out.println("== Counting");
    Map<String, Integer> countToMap =
        BOOKS.stream().collect(toMap(Book::author, book -> 1, Integer::sum));
    Map<String, Long> countGrouping = BOOKS.stream().collect(groupingBy(Book::author, counting()));
    System.out.println(
        countToMap.get("H. G. Wells").getClass().getSimpleName()
            + " "
            + countGrouping.get("H. G. Wells").getClass().getSimpleName());

    System.out.println("== Nested: groupingBy() with toMap()");
    Map<Genre, Map<String, Integer>> yearByTitleByGenre =
        BOOKS.stream()
            .collect(groupingBy(Book::genre, TreeMap::new, toMap(Book::title, Book::year)));
    System.out.println(yearByTitleByGenre.get(Genre.GOTHIC));
    yearByTitleByGenre.forEach((g, m) -> System.out.println(g + "=" + m));
  }
}
