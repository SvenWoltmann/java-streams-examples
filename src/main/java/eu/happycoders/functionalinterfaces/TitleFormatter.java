package eu.happycoders.functionalinterfaces;

import eu.happycoders.streams.Book;

/**
 * A functional interface of our own: one abstract method, so a lambda can implement it. The
 * annotation makes the compiler reject a second abstract method.
 */
@FunctionalInterface
public interface TitleFormatter {

  String format(Book book);
}
