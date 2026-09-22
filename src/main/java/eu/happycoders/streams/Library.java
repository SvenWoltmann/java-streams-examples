package eu.happycoders.streams;

import static eu.happycoders.streams.Genre.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Library {

  private Library() {}

  public static final List<Book> BOOKS =
      List.of(
          new Book("Pride and Prejudice", "Jane Austen", 1813, NOVEL),
          new Book("Frankenstein", "Mary Shelley", 1818, GOTHIC),
          new Book("Moby-Dick", "Herman Melville", 1851, ADVENTURE),
          new Book("From the Earth to the Moon", "Jules Verne", 1865, SCIENCE_FICTION),
          new Book("Alice's Adventures in Wonderland", "Lewis Carroll", 1865, FANTASY),
          new Book("Around the World in Eighty Days", "Jules Verne", 1873, ADVENTURE),
          new Book("Treasure Island", "Robert Louis Stevenson", 1883, ADVENTURE),
          new Book("Kidnapped", "Robert Louis Stevenson", 1886, ADVENTURE),
          new Book("The Time Machine", "H. G. Wells", 1895, SCIENCE_FICTION),
          new Book("Dracula", "Bram Stoker", 1897, GOTHIC),
          new Book("The War of the Worlds", "H. G. Wells", 1898, SCIENCE_FICTION));

  public static final List<Author> AUTHORS =
      BOOKS.stream()
          .collect(groupingBy(Book::author, LinkedHashMap::new, toList()))
          .entrySet()
          .stream()
          .map(entry -> new Author(entry.getKey(), entry.getValue()))
          .toList();

  /**
   * Title, author initials, and decade of a book - the body of the "lambda that is too long" in the
   * lambda article, moved into a method that a method reference can name.
   */
  public static String label(Book book) {
    String decade = (book.year() / 10 * 10) + "s";
    String authorInitials =
        Arrays.stream(book.author().split(" "))
            .map(name -> name.substring(0, 1))
            .collect(Collectors.joining());
    return book.title() + " (" + authorInitials + ", " + decade + ")";
  }
}
