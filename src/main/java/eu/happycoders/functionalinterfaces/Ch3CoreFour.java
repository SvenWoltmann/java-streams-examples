package eu.happycoders.functionalinterfaces;

import static eu.happycoders.streams.Genre.GOTHIC;
import static eu.happycoders.streams.Genre.NOVEL;
import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Library;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Ch3CoreFour {

  static void main() {
    Book dracula = BOOKS.get(9);

    System.out.println("== Function: apply()");
    Function<Book, String> title = Book::title;
    System.out.println(title.apply(dracula));

    System.out.println("== Function: andThen() and compose()");
    Function<String, Integer> length = String::length;
    Function<Book, Integer> titleLength = title.andThen(length);
    System.out.println(titleLength.apply(dracula));
    Function<Book, Integer> titleLengthComposed = length.compose(title);
    System.out.println(titleLengthComposed.apply(dracula));

    System.out.println("== Function: identity() in toMap()");
    Map<String, Book> byTitle = BOOKS.stream().collect(toMap(Book::title, Function.identity()));
    System.out.println(byTitle.get("Dracula").year());

    System.out.println("== Predicate: test()");
    Predicate<Book> isGothic = book -> book.genre() == GOTHIC;
    System.out.println(isGothic.test(dracula));

    System.out.println("== Predicate: and(), or(), negate()");
    Predicate<Book> before1850 = book -> book.year() < 1850;
    System.out.println(titles(isGothic.and(before1850)));
    System.out.println(titles(isGothic.or(before1850)));
    System.out.println(titles(isGothic.negate()));

    System.out.println("== Predicate: not() (since Java 11) and isEqual()");
    System.out.println(titles(Predicate.not(isGothic)));
    Predicate<Object> isNovel = Predicate.isEqual(NOVEL);
    System.out.println(BOOKS.stream().map(Book::genre).filter(isNovel).count());

    System.out.println("== Consumer: accept() and andThen()");
    Consumer<Book> printTitle = book -> System.out.println(book.title());
    Consumer<Book> printYear = book -> System.out.println("  " + book.year());
    printTitle.accept(dracula);
    BOOKS.stream().filter(isGothic).forEach(printTitle.andThen(printYear));

    System.out.println("== Supplier: get()");
    Supplier<Book> firstBook = () -> BOOKS.getFirst();
    System.out.println(firstBook.get().title());

    System.out.println("== Supplier: orElseGet() calls the supplier only when needed");
    Optional<Book> emma = Library.findByTitle("Emma");
    Book fallback = emma.orElseGet(() -> new Book("Emma", "Jane Austen", 1815, NOVEL));
    System.out.println(fallback);

    System.out.println("== Supplier: toCollection() calls the supplier to create the collection");
    TreeSet<String> sortedTitles =
        BOOKS.stream().map(Book::title).collect(toCollection(TreeSet::new));
    System.out.println(sortedTitles.first() + " ... " + sortedTitles.last());
  }

  static List<String> titles(Predicate<Book> condition) {
    return BOOKS.stream().filter(condition).map(Book::title).toList();
  }
}
