package eu.happycoders.lambdas;

import static eu.happycoders.streams.Library.BOOKS;

import java.util.List;

/**
 * Chapter "Reading lambdas in stack traces": the frame {@code lambda$main$0} is the lambda, and its
 * line number is the line of the lambda.
 */
public class LambdaTrace {

  public static void main(String[] args) {
    List<Integer> numbers = BOOKS.stream().map(book -> Integer.parseInt(book.title())).toList();
    System.out.println(numbers);
  }
}
