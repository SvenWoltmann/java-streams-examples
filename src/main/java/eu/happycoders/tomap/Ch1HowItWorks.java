package eu.happycoders.tomap;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.toMap;

import eu.happycoders.streams.Book;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Ch1HowItWorks {

  static void main() {
    System.out.println("== Intro: title -> year");
    Map<String, Integer> yearByTitle = BOOKS.stream().collect(toMap(Book::title, Book::year));
    System.out.println(yearByTitle.get("Moby-Dick"));

    System.out.println("== The same as a loop");
    Map<String, Integer> loop = new HashMap<>();
    for (Book book : BOOKS) {
      loop.put(book.title(), book.year());
    }
    System.out.println(loop.equals(yearByTitle));

    System.out.println("== Full map");
    System.out.println(yearByTitle);

    System.out.println("== Function.identity(): title -> book");
    Map<String, Book> bookByTitle = BOOKS.stream().collect(toMap(Book::title, Function.identity()));
    System.out.println(bookByTitle.get("Dracula"));
  }
}
