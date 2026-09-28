package eu.happycoders.functionalinterfaces;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ch1Definition {

  static void main() {
    System.out.println("== the simplest functional interface: Runnable");
    Runnable printFirstTitle = () -> System.out.println(BOOKS.getFirst().title());
    printFirstTitle.run();

    System.out.println("== a functional interface may have more methods: Comparator");
    // an instance of the named class - needs the class YearComparator on top of this line
    Comparator<Book> byYearClass = new YearComparator();

    // the lambda is the complete implementation - it needs no class
    Comparator<Book> byYearLambda = (a, b) -> Integer.compare(a.year(), b.year());

    List<Book> books = new ArrayList<>(BOOKS);
    books.sort(byYearClass);
    System.out.println(books.getFirst().title());
    books.sort(byYearLambda);
    System.out.println(books.getFirst().title());

    System.out.println("== the abstract method can be called like any other");
    Book frankenstein = BOOKS.get(1);
    Book dracula = BOOKS.get(9);
    System.out.println(byYearLambda.compare(frankenstein, dracula));
    System.out.println(byYearLambda.compare(dracula, frankenstein));

    System.out.println("== default methods do not count: reversed() and thenComparing()");
    books.sort(byYearLambda.reversed());
    System.out.println(books.getFirst().title());
    Comparator<Book> byAuthorAndYear =
        Comparator.comparing(Book::author).thenComparing(byYearLambda);
    books.sort(byAuthorAndYear);
    System.out.println(books.stream().map(Book::title).toList());
  }
}
