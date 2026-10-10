package eu.happycoders.groupingby;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.groupingBy;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Genre;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ch1HowItWorks {

  static void main() {
    System.out.println("== Intro");
    Map<Genre, List<Book>> booksByGenre = BOOKS.stream().collect(groupingBy(Book::genre));
    System.out.println(booksByGenre.get(Genre.GOTHIC));

    System.out.println("== Loop");
    Map<Genre, List<Book>> loop = new HashMap<>();
    for (Book book : BOOKS) {
      loop.computeIfAbsent(book.genre(), genre -> new ArrayList<>()).add(book);
    }
    System.out.println(loop.get(Genre.GOTHIC));

    System.out.println("== Key order");
    System.out.println(booksByGenre.keySet());

    System.out.println("== Types");
    System.out.println(
        booksByGenre.getClass().getName()
            + " "
            + booksByGenre.get(Genre.GOTHIC).getClass().getName());
  }
}
