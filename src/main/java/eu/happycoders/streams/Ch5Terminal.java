package eu.happycoders.streams;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ch5Terminal {

  static void main() {
    System.out.println("== forEach()");
    BOOKS.stream()
        .filter(book -> book.year() > 1890)
        .forEach(book -> System.out.println(book.title()));

    System.out.println("== toList() / toSet()");
    List<String> titles = BOOKS.stream().map(Book::title).toList();
    System.out.println(titles.size());
    Set<Genre> genres = BOOKS.stream().map(Book::genre).collect(toSet());
    System.out.println(genres.size());
    List<String> mutable = BOOKS.stream().map(Book::title).collect(Collectors.toList());
    mutable.add("Emma");
    System.out.println(mutable.size());
    try {
      titles.add("Emma");
    } catch (UnsupportedOperationException e) {
      System.out.println("UnsupportedOperationException");
    }

    System.out.println("== toMap()");
    Map<String, Integer> yearByTitle = BOOKS.stream().collect(toMap(Book::title, Book::year));
    System.out.println(yearByTitle.get("Dracula"));
    try {
      BOOKS.stream().collect(toMap(Book::author, Book::title));
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    }
    Map<String, String> latestTitleByAuthor =
        BOOKS.stream().collect(toMap(Book::author, Book::title, (first, second) -> second));
    System.out.println(latestTitleByAuthor.get("H. G. Wells"));

    System.out.println("== joining()");
    String authors = BOOKS.stream().map(Book::author).distinct().collect(joining(", "));
    System.out.println(authors);

    System.out.println("== groupingBy()");
    Map<Genre, List<Book>> byGenre = BOOKS.stream().collect(groupingBy(Book::genre));
    System.out.println(byGenre.get(Genre.GOTHIC));
    Map<Genre, Long> countByGenre = BOOKS.stream().collect(groupingBy(Book::genre, counting()));
    System.out.println(countByGenre);

    System.out.println("== toArray()");
    String[] titleArray = BOOKS.stream().map(Book::title).toArray(String[]::new);
    System.out.println(titleArray.length + " " + titleArray[0]);
    int[] years = BOOKS.stream().mapToInt(Book::year).toArray();
    System.out.println(years.length + " " + years[0]);

    System.out.println("== reduce()");
    int sum = IntStream.rangeClosed(1, 5).reduce(0, (a, b) -> a + b);
    System.out.println(sum);
    Optional<Book> oldest = BOOKS.stream().reduce((a, b) -> a.year() <= b.year() ? a : b);
    System.out.println(oldest.map(Book::title).orElse("none"));
    String longestTitle =
        BOOKS.stream().map(Book::title).reduce("", (a, b) -> a.length() >= b.length() ? a : b);
    System.out.println(longestTitle);

    System.out.println("== count() / min() / max() / sum() / average()");
    System.out.println(BOOKS.stream().filter(b -> b.year() < 1850).count());
    Optional<Book> newest = BOOKS.stream().max(Comparator.comparingInt(Book::year));
    System.out.println(newest.map(Book::title).orElse("none"));
    IntStream yearStream = BOOKS.stream().mapToInt(Book::year);
    System.out.println(yearStream.average().getAsDouble());
    IntSummaryStatistics stats = BOOKS.stream().mapToInt(Book::year).summaryStatistics();
    System.out.println(stats);
    System.out.println(IntStream.rangeClosed(1, 100).sum());

    System.out.println("== findFirst() / findAny()");
    Optional<Book> firstGothic =
        BOOKS.stream().filter(book -> book.genre() == Genre.GOTHIC).findFirst();
    System.out.println(firstGothic.map(Book::title).orElse("none"));
    Optional<Book> firstNovel = BOOKS.stream().filter(book -> book.year() > 1900).findFirst();
    System.out.println(firstNovel.isPresent());
    System.out.println(firstNovel.map(Book::title).orElse("none"));
    firstGothic.ifPresent(book -> System.out.println("found: " + book.title()));

    System.out.println("== anyMatch() / allMatch() / noneMatch()");
    boolean hasStoker = BOOKS.stream().anyMatch(book -> book.author().equals("Bram Stoker"));
    System.out.println(hasStoker);
    System.out.println(BOOKS.stream().allMatch(book -> book.year() < 1900));
    System.out.println(BOOKS.stream().noneMatch(book -> book.genre() == Genre.NOVEL));
  }
}
