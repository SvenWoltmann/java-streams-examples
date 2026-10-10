package eu.happycoders.groupingby;

import static eu.happycoders.streams.Library.AUTHORS;
import static eu.happycoders.streams.Library.BOOKS;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.teeing;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Genre;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class Ch3Downstream {

  record OldestAndNewest(String oldest, String newest) {}

  static void main() {
    System.out.println("== mapping");
    Map<String, List<String>> titlesByAuthor =
        BOOKS.stream().collect(groupingBy(Book::author, mapping(Book::title, toList())));
    System.out.println(titlesByAuthor);

    System.out.println("== joining");
    Map<String, String> joinedTitlesByAuthor =
        BOOKS.stream().collect(groupingBy(Book::author, mapping(Book::title, joining(", "))));
    System.out.println(joinedTitlesByAuthor.get("Robert Louis Stevenson"));

    System.out.println("== toCollection TreeSet");
    Map<Genre, Set<String>> authorsByGenre =
        BOOKS.stream()
            .collect(groupingBy(Book::genre, mapping(Book::author, toCollection(TreeSet::new))));
    System.out.println(authorsByGenre.get(Genre.ADVENTURE));
    Map<Genre, List<String>> authorListByGenre =
        BOOKS.stream().collect(groupingBy(Book::genre, mapping(Book::author, toList())));
    System.out.println(authorListByGenre.get(Genre.ADVENTURE));

    System.out.println("== counting");
    Map<String, Long> bookCountByAuthor =
        BOOKS.stream().collect(groupingBy(Book::author, counting()));
    System.out.println(bookCountByAuthor);

    System.out.println("== summingInt as Integer count");
    Map<String, Integer> intCountByAuthor =
        BOOKS.stream().collect(groupingBy(Book::author, summingInt(book -> 1)));
    System.out.println(intCountByAuthor.get("Jules Verne"));

    System.out.println("== averagingInt");
    Map<Genre, Double> averageYearByGenre =
        BOOKS.stream().collect(groupingBy(Book::genre, averagingInt(Book::year)));
    System.out.println(averageYearByGenre);

    System.out.println("== summarizingInt");
    Map<Genre, IntSummaryStatistics> yearStatsByGenre =
        BOOKS.stream().collect(groupingBy(Book::genre, summarizingInt(Book::year)));
    IntSummaryStatistics stats = yearStatsByGenre.get(Genre.SCIENCE_FICTION);
    System.out.println(stats);
    System.out.println(stats.getMin() + "–" + stats.getMax());

    System.out.println("== minBy");
    Map<Genre, Optional<Book>> oldestBookByGenre =
        BOOKS.stream().collect(groupingBy(Book::genre, minBy(comparingInt(Book::year))));
    System.out.println(oldestBookByGenre.get(Genre.ADVENTURE));

    System.out.println("== collectingAndThen");
    Map<Genre, String> oldestTitleByGenre =
        BOOKS.stream()
            .collect(
                groupingBy(
                    Book::genre,
                    collectingAndThen(
                        minBy(comparingInt(Book::year)), oldest -> oldest.orElseThrow().title())));
    System.out.println(oldestTitleByGenre);

    System.out.println("== filtering");
    Map<Genre, List<String>> titlesAfter1880ByGenre =
        BOOKS.stream()
            .collect(
                groupingBy(
                    Book::genre,
                    filtering(book -> book.year() > 1880, mapping(Book::title, toList()))));
    System.out.println(titlesAfter1880ByGenre);

    System.out.println("== filter before");
    Map<Genre, List<String>> filteredBefore =
        BOOKS.stream()
            .filter(book -> book.year() > 1880)
            .collect(groupingBy(Book::genre, mapping(Book::title, toList())));
    System.out.println(filteredBefore);

    System.out.println("== flatMapping");
    Map<Integer, List<String>> titlesByBookCount =
        AUTHORS.stream()
            .collect(
                groupingBy(
                    author -> author.books().size(),
                    flatMapping(author -> author.books().stream().map(Book::title), toList())));
    System.out.println(titlesByBookCount);

    System.out.println("== teeing");
    Map<Genre, OldestAndNewest> oldestAndNewestByGenre =
        BOOKS.stream()
            .collect(
                groupingBy(
                    Book::genre,
                    teeing(
                        minBy(comparingInt(Book::year)),
                        maxBy(comparingInt(Book::year)),
                        (oldest, newest) ->
                            new OldestAndNewest(
                                oldest.orElseThrow().title(), newest.orElseThrow().title()))));
    System.out.println(oldestAndNewestByGenre.get(Genre.SCIENCE_FICTION));
  }
}
