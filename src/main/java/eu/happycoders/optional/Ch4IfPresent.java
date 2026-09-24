package eu.happycoders.optional;

import static eu.happycoders.streams.Library.findByTitle;

public class Ch4IfPresent {

  static void main() {
    System.out.println("== ifPresent()");
    findByTitle("Dracula").ifPresent(book -> System.out.println("Found: " + book.title()));
    findByTitle("Ulysses").ifPresent(book -> System.out.println("Found: " + book.title()));

    System.out.println("== ifPresentOrElse()");
    findByTitle("Ulysses")
        .ifPresentOrElse(
            book -> System.out.println("Found: " + book.title()),
            () -> System.out.println("Not in the library"));
  }
}
