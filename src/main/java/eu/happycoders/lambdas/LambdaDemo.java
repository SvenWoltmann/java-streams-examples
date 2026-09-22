package eu.happycoders.lambdas;

import static eu.happycoders.streams.Genre.GOTHIC;

import eu.happycoders.streams.Book;
import java.util.function.Predicate;

/**
 * Chapter "How the JVM executes a lambda". Compile, then look at the class with {@code javap -p -c
 * target/classes/eu/happycoders/lambdas/LambdaDemo.class}: the lambda body is the private static
 * method {@code lambda$main$0(Book)}, and main() creates the lambda with a single {@code
 * invokedynamic} instruction.
 */
public class LambdaDemo {

  public static void main(String[] args) {
    Predicate<Book> isGothic = book -> book.genre() == GOTHIC;
    System.out.println(isGothic.getClass().getName());
  }
}
