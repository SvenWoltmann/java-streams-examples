package eu.happycoders.tomap;

import static eu.happycoders.streams.Library.BOOKS;
import static java.util.stream.Collectors.toMap;

import eu.happycoders.streams.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class Ch7Applications {

  static void main() {
    Map<String, Integer> yearByTitle = BOOKS.stream().collect(toMap(Book::title, Book::year));

    System.out.println("== Filter a map");
    Map<String, Integer> before1850 =
        yearByTitle.entrySet().stream()
            .filter(entry -> entry.getValue() < 1850)
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    System.out.println(before1850);

    System.out.println("== Filter without a stream: removeIf()");
    Map<String, Integer> copy = new HashMap<>(yearByTitle);
    copy.values().removeIf(year -> year >= 1850);
    System.out.println(copy);

    System.out.println("== Transform the values");
    Map<String, Integer> decadeByTitle =
        yearByTitle.entrySet().stream()
            .collect(toMap(Map.Entry::getKey, entry -> entry.getValue() / 10 * 10));
    System.out.println(decadeByTitle.get("Dracula"));

    System.out.println("== Invert a map");
    try {
      Map<Integer, String> titleByYear =
          yearByTitle.entrySet().stream().collect(toMap(Map.Entry::getValue, Map.Entry::getKey));
      System.out.println(titleByYear);
    } catch (IllegalStateException e) {
      System.out.println(e);
    }
    Map<Integer, String> titlesByYear =
        yearByTitle.entrySet().stream()
            .collect(
                toMap(
                    Map.Entry::getValue,
                    Map.Entry::getKey,
                    (first, second) -> first + ", " + second,
                    TreeMap::new));
    System.out.println(titlesByYear.get(1865));

    System.out.println("== Two lists to a map");
    List<String> titles = List.of("Frankenstein", "Dracula", "Kidnapped");
    List<Integer> years = List.of(1818, 1897, 1886);
    Map<String, Integer> zipped =
        IntStream.range(0, titles.size()).boxed().collect(toMap(titles::get, years::get));
    System.out.println(zipped);
  }
}
