package eu.happycoders.lambdas;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Ch5MethodReferences extends Greeter {

  static void main() {
    System.out.println("== static method: String::valueOf");
    List<String> years = BOOKS.stream().map(Book::year).map(String::valueOf).toList();
    System.out.println(years);

    System.out.println("== instance method of a particular object: System.out::println");
    BOOKS.stream().map(Book::title).forEach(System.out::println);

    System.out.println("== this:: and super::");
    new Ch5MethodReferences().greetings();

    System.out.println("== instance method of an arbitrary object of a type: Book::title");
    List<String> titles =
        BOOKS.stream().map(Book::title).sorted(String::compareToIgnoreCase).toList();
    System.out.println(titles);

    System.out.println("== constructor: ArrayList::new, StringBuilder::new, String[]::new");
    // () -> new ArrayList<>()
    Supplier<List<Book>> newList = ArrayList::new;

    // s -> new StringBuilder(s)
    Function<String, StringBuilder> builder = StringBuilder::new;

    // length -> new String[length]
    String[] titleArray = BOOKS.stream().map(Book::title).toArray(String[]::new);

    System.out.println(newList.get().size());
    System.out.println(builder.apply("Dracula").reverse());
    System.out.println(titleArray.length);

    System.out.println("== ambiguous reference: Integer::toString");
    // Does not compile - toString(int) and toString() both fit:
    // List<String> numbers = Stream.of(1, 2, 3).map(Integer::toString).toList();
    List<String> numbers = Stream.of(1, 2, 3).map(i -> i.toString()).toList();
    List<String> numbersRef = Stream.of(1, 2, 3).map(String::valueOf).toList();
    System.out.println(numbers);
    System.out.println(numbersRef);
  }

  @Override
  String greet(String name) {
    return "hello " + name;
  }

  void greetings() {
    Function<String, String> mine = this::greet;
    Function<String, String> inherited = super::greet;
    System.out.println(mine.apply("Mary"));
    System.out.println(inherited.apply("Mary"));
  }
}
