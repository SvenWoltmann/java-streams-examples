package eu.happycoders.lambdas;

import static eu.happycoders.streams.Genre.GOTHIC;
import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Ch3TargetType {

  static void main() throws ExecutionException, InterruptedException {
    System.out.println("== the four contexts of a target type");
    // assignment: the target type is the declared type of the variable
    Predicate<Book> isGothic = book -> book.genre() == GOTHIC;

    // method argument: the target type is the parameter type of filter()
    Stream<Book> gothicBooks = BOOKS.stream().filter(book -> book.genre() == GOTHIC);

    // return statement: the target type is the return type of the method
    Predicate<Book> after1890 = publishedAfter(1890);

    // cast: the target type is the type in the cast
    Object byYear = (Comparator<Book>) (a, b) -> Integer.compare(a.year(), b.year());

    System.out.println(isGothic.test(BOOKS.get(1)));
    System.out.println(gothicBooks.map(Book::title).toList());
    System.out.println(BOOKS.stream().filter(after1890).map(Book::title).toList());
    System.out.println(byYear instanceof Comparator);

    // Does not compile - var would take the type from the lambda, and the lambda from var:
    // var isGothicVar = book -> book.genre() == GOTHIC;

    System.out.println("== functional interfaces");
    Predicate<Book> predicate = book -> book.year() < 1850;
    Function<Book, String> function = book -> book.title();
    Consumer<Book> consumer = book -> System.out.println(book.title());
    Supplier<Book> supplier = () -> BOOKS.get(0);
    System.out.println(predicate.test(BOOKS.get(0)));
    System.out.println(function.apply(BOOKS.get(0)));
    consumer.accept(BOOKS.get(0));
    System.out.println(supplier.get().title());

    System.out.println("== overloaded methods");
    // Does not compile - the implicitly typed lambda fits both overloads:
    // run(s -> s.trim());
    System.out.println(run((String s) -> s.trim()));
    System.out.println(
        run(
            s -> {
              s.trim();
            }));
    System.out.println(
        run(
            s -> {
              return s.trim();
            }));

    System.out.println("== ExecutorService.submit(): Runnable or Callable");
    try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
      Future<?> runnable = executor.submit(() -> System.out.println("running"));
      Future<String> callable = executor.submit(() -> "done");
      runnable.get();
      System.out.println(callable.get());
    }
  }

  static Predicate<Book> publishedAfter(int year) {
    return book -> book.year() > year;
  }

  static String run(Consumer<String> consumer) {
    consumer.accept(" x ");
    return "Consumer overload";
  }

  static String run(Function<String, String> function) {
    return "Function overload: '" + function.apply(" x ") + "'";
  }
}
