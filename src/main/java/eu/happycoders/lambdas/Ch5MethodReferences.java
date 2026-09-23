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
    System.out.println("== static method: lambda, then String::valueOf");
    List<String> yearsLambda =
        BOOKS.stream().map(book -> book.year()).map(year -> String.valueOf(year)).toList();
    List<String> years = BOOKS.stream().map(book -> book.year()).map(String::valueOf).toList();
    System.out.println(yearsLambda);
    System.out.println(years);

    System.out.println(
        "== instance method of a particular object: lambda, then System.out::println");
    BOOKS.stream().map(book -> book.title()).forEach(title -> System.out.println(title));
    BOOKS.stream().map(book -> book.title()).forEach(System.out::println);

    System.out.println("== this:: and super::");
    new Ch5MethodReferences().greetings();

    System.out.println(
        "== instance method of an arbitrary object of a type: lambdas, then references");
    List<String> titlesLambda =
        BOOKS.stream()
            .map(book -> book.title())
            .sorted((a, b) -> a.compareToIgnoreCase(b))
            .toList();
    List<String> titles =
        BOOKS.stream().map(Book::title).sorted(String::compareToIgnoreCase).toList();
    System.out.println(titlesLambda);
    System.out.println(titles);

    System.out.println("== constructor: lambdas, then ArrayList::new, StringBuilder::new");
    Supplier<List<Book>> newListLambda = () -> new ArrayList<>();
    Function<String, StringBuilder> builderLambda = s -> new StringBuilder(s);
    Supplier<List<Book>> newList = ArrayList::new;
    Function<String, StringBuilder> builder = StringBuilder::new;
    System.out.println(newListLambda.get().size() + " " + newList.get().size());
    System.out.println(
        builderLambda.apply("Dracula").reverse() + " " + builder.apply("Dracula").reverse());

    System.out.println("== array constructor: lambda, then String[]::new");
    String[] titleArrayLambda =
        BOOKS.stream().map(book -> book.title()).toArray(length -> new String[length]);
    String[] titleArray = BOOKS.stream().map(book -> book.title()).toArray(String[]::new);
    System.out.println(titleArrayLambda.length + " " + titleArray.length);

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
