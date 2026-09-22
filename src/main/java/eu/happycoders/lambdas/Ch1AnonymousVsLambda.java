package eu.happycoders.lambdas;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ch1AnonymousVsLambda {

  static void main() {
    System.out.println("== anonymous class");
    List<Book> books = new ArrayList<>(BOOKS);
    books.sort(
        new Comparator<Book>() {
          @Override
          public int compare(Book a, Book b) {
            return Integer.compare(a.year(), b.year());
          }
        });
    System.out.println(books.stream().map(Book::year).toList());

    System.out.println("== lambda expression");
    books = new ArrayList<>(BOOKS);
    books.sort((a, b) -> Integer.compare(a.year(), b.year()));
    System.out.println(books.stream().map(Book::year).toList());
  }
}
