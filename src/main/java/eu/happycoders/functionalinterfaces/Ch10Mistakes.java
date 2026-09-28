package eu.happycoders.functionalinterfaces;

import static eu.happycoders.streams.Genre.GOTHIC;
import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class Ch10Mistakes {

  static void main() {
    System.out.println("== Function<Book, Boolean> where a Predicate<Book> belongs");
    Function<Book, Boolean> isGothicFunction = book -> book.genre() == GOTHIC;
    // Does not compile - a Function has no negate(), and filter() takes no Function:
    // BOOKS.stream().filter(isGothicFunction).count();
    System.out.println(BOOKS.stream().filter(isGothicFunction::apply).count());

    Predicate<Book> isGothic = book -> book.genre() == GOTHIC;
    System.out.println(BOOKS.stream().filter(isGothic.negate()).count());

    System.out.println("== Function<Book, Void> where a Consumer<Book> belongs");
    Function<Book, Void> printFunction =
        book -> {
          System.out.println(book.title());
          return null;
        };
    printFunction.apply(BOOKS.get(9));

    Consumer<Book> print = book -> System.out.println(book.title());
    print.accept(BOOKS.get(9));

    System.out.println("== UnaryOperator<Integer> where an IntUnaryOperator belongs");
    UnaryOperator<Integer> nextYearBoxed = year -> year + 1;
    System.out.println(BOOKS.stream().mapToInt(Book::year).boxed().map(nextYearBoxed).toList());

    IntUnaryOperator nextYear = year -> year + 1;
    System.out.println(BOOKS.stream().mapToInt(Book::year).map(nextYear).boxed().toList());
  }
}
