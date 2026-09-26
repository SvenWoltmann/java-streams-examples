package eu.happycoders.functionalinterfaces;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

public class Ch6Primitives {

  static void main() {
    System.out.println("== ToIntFunction: mapToInt() - object in, int out");
    ToIntFunction<Book> year = Book::year;
    int newest = BOOKS.stream().mapToInt(year).max().orElseThrow();
    System.out.println(newest);

    System.out.println("== IntPredicate: IntStream.filter()");
    IntPredicate nineteenthCentury = y -> y >= 1801 && y <= 1900;
    System.out.println(BOOKS.stream().mapToInt(Book::year).filter(nineteenthCentury).count());

    System.out.println("== IntUnaryOperator: IntStream.map() - int in, int out");
    IntUnaryOperator decade = y -> y / 10 * 10;
    System.out.println(BOOKS.stream().mapToInt(Book::year).map(decade).distinct().boxed().toList());

    System.out.println("== IntFunction: IntStream.mapToObj() - int in, object out");
    IntFunction<String> century = y -> ((y - 1) / 100 + 1) + "th century";
    System.out.println(BOOKS.stream().mapToInt(Book::year).mapToObj(century).distinct().toList());

    System.out.println("== IntBinaryOperator: IntStream.reduce()");
    IntBinaryOperator max = Math::max;
    System.out.println(BOOKS.stream().mapToInt(Book::year).reduce(0, max));

    System.out.println("== IntSupplier: IntStream.generate()");
    IntSupplier zero = () -> 0;
    System.out.println(IntStream.generate(zero).limit(3).boxed().toList());

    System.out.println("== ObjIntConsumer: IntStream.collect()");
    ObjIntConsumer<StringBuilder> appendYear = (sb, y) -> sb.append(y).append(' ');
    StringBuilder years =
        BOOKS.stream()
            .mapToInt(Book::year)
            .limit(3)
            .collect(StringBuilder::new, appendYear, StringBuilder::append);
    System.out.println(years.toString().trim());

    System.out.println("== the price of Function<Integer, Integer>: boxed() first");
    Function<Integer, Integer> nextYearBoxed = y -> y + 1;
    List<Integer> boxed = BOOKS.stream().mapToInt(Book::year).boxed().map(nextYearBoxed).toList();
    IntUnaryOperator nextYear = y -> y + 1;
    int[] primitive = BOOKS.stream().mapToInt(Book::year).map(nextYear).toArray();
    System.out.println(boxed.get(0) + " " + primitive[0]);
  }
}
