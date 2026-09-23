package eu.happycoders.lambdas;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.List;

/**
 * Inside a lambda, {@code this} is the enclosing instance: the lambda reads the field {@code
 * minYear}, and {@code this::describe} refers to this class's own method.
 */
public class BookFilter {

  private final int minYear;

  public BookFilter(int minYear) {
    this.minYear = minYear;
  }

  public List<String> recentTitles() {
    return BOOKS.stream().filter(book -> book.year() > minYear).map(this::describe).toList();
  }

  private String describe(Book book) {
    return book.title() + " (" + book.year() + ")";
  }
}
