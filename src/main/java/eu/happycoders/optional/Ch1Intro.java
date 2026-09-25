package eu.happycoders.optional;

import static eu.happycoders.streams.Genre.GOTHIC;
import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Library;
import java.util.Optional;

public class Ch1Intro {

  static void main() {
    System.out.println("== findFirst() returns an Optional");
    Optional<Book> firstGothic = BOOKS.stream().filter(book -> book.genre() == GOTHIC).findFirst();
    System.out.println(firstGothic);

    System.out.println("== a method that returns null: the signature does not say so");
    try {
      Book book = LibraryBeforeJava8.getByTitle("Ulysses");
      System.out.println(book.year());
    } catch (NullPointerException e) {
      System.out.println(e);
    }

    System.out.println("== the two methods of the examples");
    System.out.println(Library.findByTitle("Dracula"));
    System.out.println(Library.findByTitle("Ulysses"));
    System.out.println(Library.findByTitle("Treasure Island").flatMap(Library::nextBookBy));
    System.out.println(Library.findByTitle("Dracula").flatMap(Library::nextBookBy));
  }
}
