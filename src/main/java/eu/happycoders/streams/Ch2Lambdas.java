package eu.happycoders.streams;

import static eu.happycoders.streams.Library.BOOKS;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Ch2Lambdas {

  static void main() {
    System.out.println("== anonymous class");
    Predicate<Book> publishedBefore1850 =
        new Predicate<Book>() {
          @Override
          public boolean test(Book book) {
            return book.year() < 1850;
          }
        };
    System.out.println(BOOKS.stream().filter(publishedBefore1850).toList());

    System.out.println("== lambda");
    Predicate<Book> publishedBefore1850Lambda = book -> book.year() < 1850;
    System.out.println(BOOKS.stream().filter(publishedBefore1850Lambda).toList());

    System.out.println("== lambda forms");
    // Inferred parameter type: the compiler reads Book from Function<Book, String>
    Function<Book, String> title = book -> book.title();

    // Explicit parameter type - needed only where the compiler cannot infer it
    Function<Book, String> titleTyped = (Book book) -> book.title();

    // Block body: more than one statement, and therefore its own return
    Function<Book, String> titleBlock =
        book -> {
          String t = book.title();
          return t.toUpperCase();
        };
    Supplier<Book> firstBook = () -> BOOKS.getFirst();
    Consumer<Book> print = book -> System.out.println(book.title());
    print.accept(firstBook.get());
    System.out.println(title.apply(BOOKS.get(1)));
    System.out.println(titleTyped.apply(BOOKS.get(1)));
    System.out.println(titleBlock.apply(BOOKS.get(1)));

    System.out.println("== method references");
    // Instance method of a type: title() is called on every element
    List<String> titles = BOOKS.stream().map(Book::title).toList();

    // Static method: every element becomes the argument of String.valueOf()
    List<String> years = BOOKS.stream().map(Book::year).map(String::valueOf).toList();

    // Method of a specific object: println() on System.out, element as argument
    titles.forEach(System.out::println);

    // Constructor: creates the array that toArray() writes the titles into
    String[] titleArray = titles.toArray(String[]::new);
    System.out.println(years);
    System.out.println(titleArray.length);
  }
}
