package eu.happycoders.streams;

import static eu.happycoders.streams.Genre.SCIENCE_FICTION;
import static eu.happycoders.streams.Library.BOOKS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ch1Intro {

  static List<String> scienceFictionTitlesWithLoop(List<Book> books) {
    List<String> titles = new ArrayList<>();
    for (Book book : books) {
      if (book.genre() == SCIENCE_FICTION) {
        titles.add(book.title());
      }
    }
    Collections.sort(titles);
    return titles;
  }

  static List<String> scienceFictionTitlesWithStream(List<Book> books) {
    return books.stream()
        .filter(book -> book.genre() == SCIENCE_FICTION)
        .map(Book::title)
        .sorted()
        .toList();
  }

  static long countBooksAfter(List<Book> books, int year) {
    return books.stream() // ⟵ Source
        .filter(book -> book.year() > year) // ⟵ Intermediate operation
        .count(); // ⟵ Terminal operation
  }

  static void main() {
    System.out.println("== loop");
    System.out.println(scienceFictionTitlesWithLoop(BOOKS));
    System.out.println("== stream");
    System.out.println(scienceFictionTitlesWithStream(BOOKS));
    System.out.println("== anatomy: count after 1880");
    System.out.println(countBooksAfter(BOOKS, 1880));
  }
}
