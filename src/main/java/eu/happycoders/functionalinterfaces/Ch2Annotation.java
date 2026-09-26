package eu.happycoders.functionalinterfaces;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;

public class Ch2Annotation {

  static void main() {
    System.out.println("== an annotated interface of our own: TitleFormatter");
    TitleFormatter withYear = book -> book.title() + " (" + book.year() + ")";
    System.out.println(withYear.format(BOOKS.get(9)));

    // Does not compile - a second abstract method, and the annotation makes javac say so:
    //
    //   @FunctionalInterface
    //   public interface TitleFormatter {
    //     String format(Book book);
    //     String formatShort(Book book);
    //   }
    //
    //   error: Unexpected @FunctionalInterface annotation
    //     TitleFormatter is not a functional interface
    //       multiple non-overriding abstract methods found in interface TitleFormatter

    System.out.println("== without the annotation, the same interface is still functional");
    PlainFormatter plain = Book::title;
    System.out.println(plain.format(BOOKS.get(9)));
  }

  /** No annotation - still a functional interface, the compiler just does not guard it. */
  interface PlainFormatter {
    String format(Book book);
  }
}
