package eu.happycoders.streams;

import static eu.happycoders.streams.Genre.ADVENTURE;
import static eu.happycoders.streams.Library.AUTHORS;
import static eu.happycoders.streams.Library.BOOKS;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ch4Intermediate {

  static void main() {
    System.out.println("== filter()");
    List<Book> adventures = BOOKS.stream().filter(book -> book.genre() == ADVENTURE).toList();
    adventures.forEach(System.out::println);

    System.out.println("== map()");
    List<String> titles = BOOKS.stream().map(Book::title).toList();
    System.out.println(titles);
    List<String> labels =
        BOOKS.stream().map(book -> book.title() + " (" + book.year() + ")").toList();
    System.out.println(labels);

    System.out.println("== flatMap()");
    List<Book> allBooks = AUTHORS.stream().flatMap(author -> author.books().stream()).toList();
    System.out.println(allBooks.size());
    List<String> words =
        Stream.of("The Time Machine", "Moby-Dick")
            .flatMap(title -> Stream.of(title.split(" ")))
            .toList();
    System.out.println(words);

    System.out.println("== mapMulti()");
    List<String> earlyTitles =
        BOOKS.stream()
            .<String>mapMulti(
                (book, downstream) -> {
                  if (book.year() < 1850) {
                    downstream.accept(book.title());
                  }
                })
            .toList();
    System.out.println(earlyTitles);

    System.out.println("== mapToInt() / boxed()");
    IntStream years = BOOKS.stream().mapToInt(Book::year);
    System.out.println(years.max().getAsInt());
    List<Integer> yearList = BOOKS.stream().mapToInt(Book::year).boxed().toList();
    System.out.println(yearList);
    List<String> squares = IntStream.rangeClosed(1, 3).mapToObj(n -> n + "² = " + n * n).toList();
    System.out.println(squares);

    System.out.println("== distinct()");
    List<String> authors = BOOKS.stream().map(Book::author).distinct().toList();
    System.out.println(authors);

    System.out.println("== sorted()");
    System.out.println(BOOKS.stream().map(Book::title).sorted().toList());
    List<Book> byYearDescending =
        BOOKS.stream().sorted(Comparator.comparingInt(Book::year).reversed()).toList();
    byYearDescending.forEach(book -> System.out.println(book.year() + " " + book.title()));
    List<Book> byAuthorThenYear =
        BOOKS.stream()
            .sorted(Comparator.comparing(Book::author).thenComparing(Book::year))
            .toList();
    byAuthorThenYear.forEach(book -> System.out.println(book.author() + " " + book.year()));

    System.out.println("== limit() / skip()");
    System.out.println(BOOKS.stream().map(Book::title).limit(3).toList());
    System.out.println(BOOKS.stream().map(Book::title).skip(8).toList());
    System.out.println(BOOKS.stream().map(Book::title).skip(3).limit(3).toList());

    System.out.println("== takeWhile() / dropWhile()");
    System.out.println(
        BOOKS.stream().takeWhile(book -> book.year() < 1860).map(Book::title).toList());
    System.out.println(
        BOOKS.stream().dropWhile(book -> book.year() < 1890).map(Book::title).toList());
    System.out.println(Stream.of(1, 2, 8, 3, 4).takeWhile(n -> n < 5).toList());
    System.out.println(Stream.of(1, 2, 8, 3, 4).filter(n -> n < 5).toList());

    System.out.println("== peek()");
    List<String> result =
        BOOKS.stream()
            .filter(book -> book.year() > 1890)
            .peek(book -> System.out.println("after filter: " + book.title()))
            .map(Book::title)
            .peek(title -> System.out.println("after map:    " + title))
            .toList();
    System.out.println(result);

    System.out.println("== gather()");
    List<List<String>> pairs =
        BOOKS.stream().map(Book::title).gather(Gatherers.windowFixed(2)).toList();
    pairs.forEach(System.out::println);
  }
}
