package eu.happycoders.lambdas;

import static eu.happycoders.streams.Genre.GOTHIC;
import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ch6WhereUsed {

  static void main() throws InterruptedException {
    System.out.println("== sorting with a Comparator");
    List<Book> books = new ArrayList<>(BOOKS);
    books.sort(Comparator.comparing(Book::year));
    System.out.println(books.stream().map(Book::year).toList());

    System.out.println("== collections: removeIf() and forEach()");
    books.removeIf(book -> book.year() < 1850);
    books.forEach(book -> System.out.println(book.title()));

    System.out.println("== maps: computeIfAbsent()");
    Map<String, List<Book>> byAuthor = new HashMap<>();
    for (Book book : BOOKS) {
      byAuthor.computeIfAbsent(book.author(), _ -> new ArrayList<>()).add(book);
    }
    System.out.println(byAuthor.get("H. G. Wells").stream().map(Book::title).toList());

    System.out.println("== Optional: map() and orElseGet()");
    String firstGothicTitle =
        BOOKS.stream()
            .filter(book -> book.genre() == GOTHIC)
            .findFirst()
            .map(Book::title)
            .orElseGet(() -> "no gothic novel found");
    System.out.println(firstGothicTitle);

    System.out.println("== threads: Runnable");
    Thread thread =
        new Thread(() -> System.out.println("running in " + Thread.currentThread().getName()));
    thread.start();
    thread.join();
  }
}
