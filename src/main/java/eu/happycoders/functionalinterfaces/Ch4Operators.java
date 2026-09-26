package eu.happycoders.functionalinterfaces;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.Comparator.comparingInt;

import eu.happycoders.streams.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class Ch4Operators {

  static void main() {
    System.out.println("== UnaryOperator: List.replaceAll()");
    UnaryOperator<String> upper = String::toUpperCase;
    List<String> titles = new ArrayList<>(BOOKS.stream().map(Book::title).toList());
    titles.replaceAll(upper);
    System.out.println(titles.subList(0, 3));

    System.out.println("== BinaryOperator: reduce()");
    BinaryOperator<Integer> sum = Integer::sum;
    int yearSum = BOOKS.stream().map(Book::year).reduce(0, sum);
    System.out.println(yearSum);

    System.out.println("== BinaryOperator: maxBy() picks the later book");
    BinaryOperator<Book> later = BinaryOperator.maxBy(comparingInt(Book::year));
    Optional<Book> latest = BOOKS.stream().reduce(later);
    System.out.println(latest.map(Book::title).orElse("-"));

    System.out.println("== BinaryOperator: Map.merge() counts the books per author");
    Map<String, Integer> booksPerAuthor = new TreeMap<>();
    for (Book book : BOOKS) {
      booksPerAuthor.merge(book.author(), 1, Integer::sum);
    }
    System.out.println(booksPerAuthor);
  }
}
