package eu.happycoders.lambdas;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.List;

public class Ch4VariableAccess {

  static void main() {
    System.out.println("== capturing a local variable");
    int minYear = 1890;
    List<Book> recentBooks = BOOKS.stream().filter(book -> book.year() > minYear).toList();
    System.out.println(recentBooks.stream().map(Book::title).toList());

    // Does not compile - count is not effectively final:
    // int count = 0;
    // BOOKS.forEach(book -> count++);

    System.out.println("== the one-element-array workaround (don't)");
    int[] count = {0};
    BOOKS.forEach(book -> count[0]++);
    System.out.println(count[0]);

    System.out.println("== let the pipeline compute the value (do)");
    long counted = BOOKS.stream().count();
    System.out.println(counted);

    // Does not compile - the lambda parameter must not shadow a local variable:
    // String s = "Dracula";
    // Function<String, String> trimmed = s -> s.trim();

    System.out.println("== this means the enclosing instance");
    new Scope().run();
  }
}
