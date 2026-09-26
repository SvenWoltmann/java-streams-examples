package eu.happycoders.functionalinterfaces;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class Ch7Wildcards {

  static void main() {
    System.out.println("== Predicate<? super T>: a Predicate<Object> filters a Stream<Book>");
    Predicate<Object> nonNull = Objects::nonNull;
    List<Book> withGap = new ArrayList<>(BOOKS);
    withGap.add(null);
    System.out.println(withGap.stream().filter(nonNull).count());

    System.out.println(
        "== Function<? super T, ? extends R>: a Function<Object, String> maps books");
    Function<Object, String> describe = Object::toString;
    System.out.println(BOOKS.stream().map(describe).findFirst().orElse("-"));

    System.out.println("== our own method: with the wildcard, the Predicate<Object> fits");
    System.out.println(select(withGap, nonNull).size());
    // Does not compile - the parameter type Predicate<Book> takes no Predicate<Object>:
    // selectStrict(withGap, nonNull);
    //
    //   error: incompatible types: Predicate<Object> cannot be converted to Predicate<Book>
  }

  static List<Book> select(List<Book> books, Predicate<? super Book> condition) {
    return books.stream().filter(condition).toList();
  }

  static List<Book> selectStrict(List<Book> books, Predicate<Book> condition) {
    return books.stream().filter(condition).toList();
  }
}
