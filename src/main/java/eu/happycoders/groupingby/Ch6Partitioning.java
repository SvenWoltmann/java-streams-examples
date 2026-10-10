package eu.happycoders.groupingby;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

import eu.happycoders.streams.Book;
import java.util.List;
import java.util.Map;

public class Ch6Partitioning {

  static void main() {
    System.out.println("== partitioningBy");
    Map<Boolean, List<Book>> booksBefore1850 =
        BOOKS.stream().collect(partitioningBy(book -> book.year() < 1850));
    System.out.println(booksBefore1850.get(true));

    System.out.println("== with downstream");
    Map<Boolean, List<String>> titlesBefore1850 =
        BOOKS.stream()
            .collect(partitioningBy(book -> book.year() < 1850, mapping(Book::title, toList())));
    System.out.println(titlesBefore1850);

    System.out.println("== empty partition");
    Map<Boolean, Long> countBefore1800 =
        BOOKS.stream().collect(partitioningBy(book -> book.year() < 1800, counting()));
    System.out.println(countBefore1800);
    Map<Boolean, Long> groupedBefore1800 =
        BOOKS.stream().collect(groupingBy(book -> book.year() < 1800, counting()));
    System.out.println(groupedBefore1800);
  }
}
