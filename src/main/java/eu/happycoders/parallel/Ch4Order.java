package eu.happycoders.parallel;

import static eu.happycoders.streams.Genre.ADVENTURE;
import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;

public class Ch4Order {

  static void main() {
    System.out.println("== forEach() against forEachOrdered(): first letters of the titles");
    BOOKS.parallelStream().map(Book::title).forEach(title -> System.out.print(title.charAt(0)));
    System.out.println();
    BOOKS.parallelStream()
        .map(Book::title)
        .forEachOrdered(title -> System.out.print(title.charAt(0)));
    System.out.println();

    System.out.println("== findAny(): five runs (results vary)");
    for (int i = 0; i < 5; i++) {
      Book book =
          BOOKS.parallelStream().filter(b -> b.genre() == ADVENTURE).findAny().orElseThrow();
      System.out.println(book.title());
    }

    System.out.println("== findFirst(): five runs");
    for (int i = 0; i < 5; i++) {
      Book book =
          BOOKS.parallelStream().filter(b -> b.genre() == ADVENTURE).findFirst().orElseThrow();
      System.out.println(book.title());
    }
  }
}
