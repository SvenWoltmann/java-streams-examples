package eu.happycoders.tomap;

import static eu.happycoders.streams.Genre.SCIENCE_FICTION;
import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import eu.happycoders.streams.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ch10Mistakes {

  static void main() {
    System.out.println("== Remove duplicates with null: works with two books per author");
    Map<String, String> onlyBookByAuthor =
        BOOKS.stream().collect(toMap(Book::author, Book::title, (first, second) -> null));
    System.out.println(onlyBookByAuthor);

    System.out.println("== ... and fails with three");
    List<Book> books = new ArrayList<>(BOOKS);
    books.add(new Book("The Invisible Man", "H. G. Wells", 1897, SCIENCE_FICTION));
    Map<String, String> withThird =
        books.stream().collect(toMap(Book::author, Book::title, (first, second) -> null));
    System.out.println(withThird);

    System.out.println("== Count first, then collect");
    Map<String, Long> bookCountByAuthor =
        BOOKS.stream().collect(groupingBy(Book::author, counting()));
    Map<String, String> onlyTitle =
        BOOKS.stream()
            .filter(book -> bookCountByAuthor.get(book.author()) == 1)
            .collect(toMap(Book::author, Book::title));
    System.out.println(onlyTitle);

    System.out.println("== Swallowing duplicates: entries");
    System.out.println(
        BOOKS.stream().collect(toMap(Book::author, Book::year, (first, second) -> first)).size());
  }
}
