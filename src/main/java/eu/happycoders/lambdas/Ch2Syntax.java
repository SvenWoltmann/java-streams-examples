package eu.happycoders.lambdas;

import static eu.happycoders.streams.Genre.GOTHIC;
import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Ch2Syntax {

  static void main() {
    System.out.println("== parameters");
    // no parameter: empty parentheses
    Supplier<Book> firstBook = () -> BOOKS.get(0);

    // one parameter: the parentheses are optional
    Predicate<Book> isGothic = book -> book.genre() == GOTHIC;

    // several parameters: the parentheses are required
    Comparator<Book> byYear = (a, b) -> Integer.compare(a.year(), b.year());

    System.out.println(firstBook.get().title());
    System.out.println(isGothic.test(BOOKS.get(1)));
    System.out.println(byYear.compare(BOOKS.get(0), BOOKS.get(1)));

    System.out.println("== body: expression or block");
    // expression body: the value of the expression is the return value
    Function<Book, String> title = book -> book.title();

    // block body: statements in braces, and a return if a value is expected
    Function<Book, String> label =
        book -> {
          String decade = (book.year() / 10 * 10) + "s";
          return book.title() + " (" + decade + ")";
        };

    // an expression body that returns nothing: the result of println() is discarded
    Consumer<Book> print = book -> System.out.println(book.title());

    System.out.println(title.apply(BOOKS.get(9)));
    System.out.println(label.apply(BOOKS.get(9)));
    print.accept(BOOKS.get(9));

    System.out.println("== explicitly typed");
    Comparator<Book> byYearTyped = (Book a, Book b) -> Integer.compare(a.year(), b.year());
    System.out.println(byYearTyped.compare(BOOKS.get(0), BOOKS.get(1)));

    System.out.println("== where inference fails: comparing().reversed()");
    // Does not compile - without a target type, comparing() falls back to Object:
    // Comparator<Book> byYearDescending =
    //     Comparator.comparing(book -> book.year()).reversed();
    Comparator<Book> byYearDescending = Comparator.comparing((Book book) -> book.year()).reversed();
    Comparator<Book> byYearDescendingRef = Comparator.comparing(Book::year).reversed();
    System.out.println(BOOKS.stream().sorted(byYearDescending).map(Book::year).toList());
    System.out.println(BOOKS.stream().sorted(byYearDescendingRef).map(Book::year).toList());

    System.out.println("== var (Java 11)");
    // var gives an annotation a place to sit; it is all or nothing
    BiFunction<Book, Book, Integer> yearDifference = (var a, var b) -> a.year() - b.year();
    System.out.println(yearDifference.apply(BOOKS.get(1), BOOKS.get(0)));

    System.out.println("== unnamed parameter (Java 22)");
    Map<String, List<Book>> byAuthor = new HashMap<>();
    for (Book book : BOOKS) {
      byAuthor.computeIfAbsent(book.author(), _ -> new ArrayList<>()).add(book);
    }
    System.out.println(byAuthor.get("Jules Verne").stream().map(Book::title).toList());
  }
}
