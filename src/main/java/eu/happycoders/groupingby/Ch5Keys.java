package eu.happycoders.groupingby;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Genre;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class Ch5Keys {

  record AuthorAndGenre(String author, Genre genre) {}

  static void main() {
    System.out.println("== Decade");
    Map<Integer, List<String>> titlesByDecade =
        BOOKS.stream()
            .collect(
                groupingBy(
                    book -> book.year() / 10 * 10, TreeMap::new, mapping(Book::title, toList())));
    titlesByDecade.forEach((decade, titles) -> System.out.println(decade + "=" + titles));

    System.out.println("== Composite key record");
    Map<AuthorAndGenre, Long> bookCountByAuthorAndGenre =
        BOOKS.stream()
            .collect(
                groupingBy(
                    book -> new AuthorAndGenre(book.author(), book.genre()),
                    LinkedHashMap::new,
                    counting()));
    bookCountByAuthorAndGenre.forEach((key, count) -> System.out.println(key + "=" + count));

    System.out.println("== Access record key");
    AuthorAndGenre key = new AuthorAndGenre("Jules Verne", Genre.ADVENTURE);
    System.out.println(bookCountByAuthorAndGenre.get(key));

    System.out.println("== null key");
    List<Book> books = new ArrayList<>(BOOKS);
    books.add(new Book("The Invisible Man", "H. G. Wells", 1897, null));
    try {
      Map<Genre, List<Book>> booksByGenre = books.stream().collect(groupingBy(Book::genre));
      System.out.println(booksByGenre);
    } catch (NullPointerException e) {
      System.out.println(e);
      StackTraceElement[] st = e.getStackTrace();
      for (int i = 0; i < 2; i++) {
        System.out.println("    at " + st[i]);
      }
    }

    System.out.println("== null key filtered");
    Map<Genre, Long> countFiltered =
        books.stream()
            .filter(book -> book.genre() != null)
            .collect(groupingBy(Book::genre, counting()));
    System.out.println(countFiltered);

    System.out.println("== null key Optional");
    Map<Optional<Genre>, List<String>> titlesByOptionalGenre =
        books.stream()
            .collect(
                groupingBy(
                    book -> Optional.ofNullable(book.genre()), mapping(Book::title, toList())));
    System.out.println(titlesByOptionalGenre.get(Optional.empty()));
  }
}
