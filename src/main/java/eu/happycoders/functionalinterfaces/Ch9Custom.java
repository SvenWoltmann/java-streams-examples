package eu.happycoders.functionalinterfaces;

import static eu.happycoders.functionalinterfaces.ThrowingFunction.unchecked;
import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class Ch9Custom {

  static void main() {
    System.out.println("== TitleFormatter: a domain name for Function<Book, String>");
    TitleFormatter withYear = book -> book.title() + " (" + book.year() + ")";
    System.out.println(withYear.format(BOOKS.get(9)));

    System.out.println("== but map() expects a Function - only the method reference fits");
    List<String> labels = BOOKS.stream().map(withYear::format).toList();
    System.out.println(labels.get(9));
    // Does not compile - TitleFormatter is not a Function, even though it has the same shape:
    // BOOKS.stream().map(withYear).toList();
    //
    //   error: method map in interface Stream<T> cannot be applied to given types;
    //     required: Function<? super Book,? extends R>
    //     found:    TitleFormatter

    System.out.println("== the other way round: a Function assigned to a TitleFormatter");
    Function<Book, String> title = Book::title;
    TitleFormatter plain = title::apply;
    System.out.println(plain.format(BOOKS.get(9)));

    System.out.println("== ThrowingFunction: a checked exception inside a pipeline");
    // Does not compile - Files.readAllLines() throws an IOException, and Function declares none:
    // List<String> lines = Stream.of(Path.of("README.md")).map(Files::readAllLines).toList();
    //
    //   error: unreported exception IOException; must be caught or declared to be thrown
    List<Path> files = List.of(Path.of("README.md"), Path.of("pom.xml"));
    List<Integer> lineCounts =
        files.stream().map(unchecked(Files::readAllLines)).map(List::size).toList();
    System.out.println(lineCounts.size() + " files read");
  }
}
