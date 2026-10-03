package eu.happycoders.tomap;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toMap;

import eu.happycoders.streams.Book;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class Ch3Merging {

  static void main() {
    System.out.println("== Count books per author");
    Map<String, Integer> bookCountByAuthor =
        BOOKS.stream().collect(toMap(Book::author, book -> 1, Integer::sum));
    System.out.println(bookCountByAuthor);

    System.out.println("== Concatenate titles");
    Map<String, String> titlesByAuthor =
        BOOKS.stream()
            .collect(toMap(Book::author, Book::title, (first, second) -> first + ", " + second));
    System.out.println(titlesByAuthor.get("Jules Verne"));

    System.out.println("== groupingBy() with joining()");
    Map<String, String> joined =
        BOOKS.stream().collect(groupingBy(Book::author, mapping(Book::title, joining(", "))));
    System.out.println(joined.get("Jules Verne"));

    System.out.println("== Oldest book per author");
    Map<String, Book> oldestBookByAuthor =
        BOOKS.stream()
            .collect(
                toMap(
                    Book::author,
                    Function.identity(),
                    BinaryOperator.minBy(Comparator.comparingInt(Book::year))));
    System.out.println(oldestBookByAuthor.get("H. G. Wells").title());
  }
}
