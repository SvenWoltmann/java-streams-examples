package eu.happycoders.reduce;

import static eu.happycoders.streams.Genre.ADVENTURE;
import static eu.happycoders.streams.Library.BOOKS;
import static java.util.Comparator.comparingInt;

import eu.happycoders.streams.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Ch3Applications {

  static void main() {
    System.out.println("== Product: 5! = 1 * 2 * 3 * 4 * 5");
    int factorial = IntStream.rangeClosed(1, 5).reduce(1, (a, b) -> a * b);
    System.out.println(factorial);

    System.out.println("== Minimum with BinaryOperator.minBy()");
    Optional<Book> oldest = BOOKS.stream().reduce(BinaryOperator.minBy(comparingInt(Book::year)));
    System.out.println(oldest.map(Book::title).orElse("-"));

    System.out.println("== The same with min()");
    Optional<Book> oldestMin = BOOKS.stream().min(comparingInt(Book::year));
    System.out.println(oldestMin.map(Book::title).orElse("-"));

    System.out.println("== BigDecimal: sum of an order");
    List<BigDecimal> prices = Prices.ORDER;
    BigDecimal total = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    System.out.println(total);

    System.out.println("== A class of our own: Money");
    List<Money> moneyPrices = List.of(Money.of("12.99"), Money.of("8.50"), Money.of("14.95"));
    Money moneyTotal = moneyPrices.stream().reduce(Money.ZERO, Money::add);
    System.out.println(moneyTotal);

    System.out.println("== double: sum() compensates rounding errors, reduce() does not");
    System.out.println(DoubleStream.of(0.1, 0.2, 0.3).reduce(0, Double::sum));
    System.out.println(DoubleStream.of(0.1, 0.2, 0.3).sum());

    System.out.println("== Combining predicates with and()");
    List<Predicate<Book>> conditions =
        List.of(
            book -> book.genre() == ADVENTURE,
            book -> book.year() > 1860,
            book -> book.author().startsWith("Robert"));
    Predicate<Book> all = conditions.stream().reduce(book -> true, Predicate::and);
    System.out.println(BOOKS.stream().filter(all).map(Book::title).toList());

    System.out.println("== More readable: allMatch()");
    System.out.println(
        BOOKS.stream()
            .filter(book -> conditions.stream().allMatch(condition -> condition.test(book)))
            .map(Book::title)
            .toList());

    System.out.println("== Combining predicates with or()");
    Predicate<Book> any = conditions.stream().reduce(book -> false, Predicate::or);
    System.out.println(BOOKS.stream().filter(any).count());

    System.out.println("== Chaining functions with andThen()");
    List<Function<String, String>> steps =
        List.of(String::strip, String::toUpperCase, title -> title.replace(' ', '_'));
    Function<String, String> pipeline =
        steps.stream().reduce(Function.identity(), Function::andThen);
    System.out.println(pipeline.apply("  The Time Machine "));

    System.out.println("== No condition at all: the identity decides");
    List<Predicate<Book>> noConditions = List.of();
    Predicate<Book> allOfNone = noConditions.stream().reduce(book -> true, Predicate::and);
    System.out.println(BOOKS.stream().filter(allOfNone).count());
  }
}
