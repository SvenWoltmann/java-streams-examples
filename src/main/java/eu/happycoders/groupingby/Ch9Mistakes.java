package eu.happycoders.groupingby;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.groupingBy;

import eu.happycoders.streams.Book;
import eu.happycoders.streams.Genre;
import java.util.List;
import java.util.Map;

public class Ch9Mistakes {

  static void main() {
    System.out.println("== get() null");
    Map<Genre, List<Book>> booksAfter1880ByGenre =
        BOOKS.stream().filter(book -> book.year() > 1880).collect(groupingBy(Book::genre));
    try {
      int novelCount = booksAfter1880ByGenre.get(Genre.NOVEL).size();
      System.out.println(novelCount);
    } catch (NullPointerException e) {
      System.out.println(e);
    }
    int novelCount = booksAfter1880ByGenre.getOrDefault(Genre.NOVEL, List.of()).size();
    System.out.println(novelCount);
  }
}
