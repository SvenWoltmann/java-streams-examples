package eu.happycoders.lambdas;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Library;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Ch8Mistakes {

  /** Recursion through a field works, because a field has a default value before it is assigned. */
  private final Function<Integer, Integer> factorial =
      n -> n <= 1 ? 1 : n * this.factorial.apply(n - 1);

  static void main() {
    // Does not compile - Runnable.run() does not declare InterruptedException:
    // Runnable pause = () -> Thread.sleep(1_000);

    System.out.println("== a lambda that is too long");
    List<String> labels =
        BOOKS.stream()
            .map(
                book -> {
                  String decade = (book.year() / 10 * 10) + "s";
                  String authorInitials =
                      Arrays.stream(book.author().split(" "))
                          .map(name -> name.substring(0, 1))
                          .collect(Collectors.joining());
                  return book.title() + " (" + authorInitials + ", " + decade + ")";
                })
            .toList();
    System.out.println(labels);

    System.out.println("== the same as a named method");
    List<String> labelsFromMethod = BOOKS.stream().map(Library::label).toList();
    System.out.println(labelsFromMethod);

    System.out.println("== recursion");
    // Does not compile - the variable does not exist yet while the lambda is created:
    // Function<Integer, Integer> factorial =
    //     n -> n <= 1 ? 1 : n * factorial.apply(n - 1);
    System.out.println(new Ch8Mistakes().factorial.apply(5));

    System.out.println("== a lambda in a stack trace (see also LambdaTrace)");
    try {
      List<Integer> numbers = BOOKS.stream().map(book -> Integer.parseInt(book.title())).toList();
      System.out.println(numbers);
    } catch (NumberFormatException e) {
      System.out.println(e);
      System.out.println("  at " + e.getStackTrace()[3]);
    }
  }
}
