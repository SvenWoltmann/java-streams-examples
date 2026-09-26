package eu.happycoders.functionalinterfaces;

import eu.happycoders.streams.Book;
import java.util.Comparator;

/** A named class implementing the functional interface Comparator - the long way. */
public class YearComparator implements Comparator<Book> {

  @Override
  public int compare(Book a, Book b) {
    return Integer.compare(a.year(), b.year());
  }
}
