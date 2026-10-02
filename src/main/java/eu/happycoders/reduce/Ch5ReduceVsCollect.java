package eu.happycoders.reduce;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.reducing;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Genre;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BinaryOperator;

public class Ch5ReduceVsCollect {

  static void main() {
    System.out.println("== An ArrayList as identity: sequential");
    System.out.println(titlesWithReduce(false));

    System.out.println("== An ArrayList as identity: parallel, five runs");
    for (int i = 0; i < 5; i++) {
      try {
        List<String> titles = titlesWithReduce(true);
        System.out.println(titles.size() + " titles");
      } catch (RuntimeException e) {
        System.out.println(e);
      }
    }

    System.out.println("== Correct: toList()");
    System.out.println(BOOKS.parallelStream().map(Book::title).toList().size() + " titles");

    System.out.println(
        "== Correct: collect() with the same three roles as lambdas, on the book stream");
    for (int i = 0; i < 5; i++) {
      ArrayList<String> collectedTitles =
          BOOKS.parallelStream()
              .collect(
                  () -> new ArrayList<>(),
                  (list, book) -> {
                    list.add(book.title());
                  },
                  (a, b) -> {
                    a.addAll(b);
                  });
      System.out.println(collectedTitles.size() + " titles");
    }

    System.out.println("== Correct: the explicit form of collect() after map()");
    ArrayList<String> collected =
        BOOKS.parallelStream()
            .map(Book::title)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    System.out.println(collected.size() + " titles");

    System.out.println("== String concatenation with reduce()");
    String concatenated = BOOKS.stream().map(Book::title).reduce("", String::concat);
    System.out.println(concatenated.length() + " characters");

    System.out.println("== Characters copied by reduce(\"\", String::concat)");
    long copied = 0;
    int prefix = 0;
    for (Book book : BOOKS) {
      prefix += book.title().length();
      copied += prefix;
    }
    System.out.println(copied);

    System.out.println("== The same for 1,000 copies of the library (11,000 titles)");
    long copiedLarge = 0;
    long prefixLarge = 0;
    for (int i = 0; i < 1_000; i++) {
      for (Book book : BOOKS) {
        prefixLarge += book.title().length();
        copiedLarge += prefixLarge;
      }
    }
    System.out.println(prefixLarge + " characters, " + copiedLarge + " copied");

    System.out.println("== String concatenation with joining()");
    String joined = BOOKS.stream().map(Book::title).collect(joining(", "));
    System.out.println(joined);

    System.out.println("== Collectors.reducing(): the oldest book per genre");
    Map<Genre, Optional<Book>> oldestPerGenre =
        BOOKS.stream()
            .collect(
                groupingBy(
                    Book::genre,
                    TreeMap::new,
                    reducing(BinaryOperator.minBy(comparingInt(Book::year)))));
    oldestPerGenre.forEach(
        (genre, book) -> System.out.println(genre + ": " + book.map(Book::title).orElse("-")));
  }

  private static List<String> titlesWithReduce(boolean parallel) {
    var stream = parallel ? BOOKS.parallelStream() : BOOKS.stream();
    return stream.reduce(
        new ArrayList<>(),
        (list, book) -> {
          list.add(book.title());
          return list;
        },
        (a, b) -> {
          a.addAll(b);
          return a;
        });
  }
}
