package eu.happycoders.parallel;

import static eu.happycoders.streams.Library.BOOKS;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Ch5Rules {

  static void main() {
    System.out.println("== Rule 1: a shared ArrayList filled in forEach(), five runs");
    for (int run = 0; run < 5; run++) {
      try {
        List<Integer> result = new ArrayList<>();
        IntStream.range(0, 100_000).parallel().forEach(result::add);
        System.out.println(result.size());
      } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println(e);
      }
    }

    System.out.println("== The fix: let the stream build the list");
    List<Integer> result = IntStream.range(0, 100_000).parallel().boxed().toList();
    System.out.println(result.size());

    System.out.println("== Rule 2: a lambda that counts (numbers vary)");
    int[] counter = {0};
    List<String> numbered =
        BOOKS.parallelStream().map(book -> ++counter[0] + ". " + book.title()).toList();
    System.out.println(numbered);

    System.out.println("== The fix: a stream of indices");
    List<String> numberedByIndex =
        IntStream.range(0, BOOKS.size())
            .parallel()
            .mapToObj(i -> (i + 1) + ". " + BOOKS.get(i).title())
            .toList();
    System.out.println(numberedByIndex);

    System.out.println("== Rule 3: an identity that is not neutral");
    int sequential = IntStream.rangeClosed(1, 5).reduce(10, Integer::sum);
    int parallel = IntStream.rangeClosed(1, 5).parallel().reduce(10, Integer::sum);
    System.out.println(sequential + " " + parallel);
  }
}
