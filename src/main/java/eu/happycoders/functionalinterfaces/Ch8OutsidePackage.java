package eu.happycoders.functionalinterfaces;

import static eu.happycoders.streams.Library.BOOKS;

import eu.happycoders.streams.Book;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ch8OutsidePackage {

  static void main() throws InterruptedException, ExecutionException, IOException {
    System.out.println("== Runnable (Java 1.0): a task without result");
    Runnable task = () -> System.out.println("running in " + Thread.currentThread().getName());
    Thread thread = new Thread(task);
    thread.start();
    thread.join();

    System.out.println("== Callable (Java 5): a task with a result");
    Callable<Integer> countBooks = () -> BOOKS.size();
    try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
      Future<Integer> count = executor.submit(countBooks);
      System.out.println(count.get());
    }

    System.out.println("== Comparator (Java 1.2): comparing() and thenComparing()");
    List<Book> books = new ArrayList<>(BOOKS);
    books.sort(Comparator.comparing(Book::author).thenComparing(Book::year));
    System.out.println(books.stream().map(Book::title).toList().subList(0, 3));
    // Since Java 26, Comparator also has min() and max() (JDK-8356995) - not in this Java 25 build:
    // Book later = Comparator.comparingInt(Book::year).max(books.get(0), books.get(1));

    System.out.println("== FileFilter (Java 1.2): File.listFiles()");
    FileFilter directories = File::isDirectory;
    File[] packages = new File("src/main/java/eu/happycoders").listFiles(directories);
    System.out.println(Arrays.stream(packages).map(File::getName).sorted().toList());

    System.out.println("== DirectoryStream.Filter (Java 7): Files.newDirectoryStream()");
    DirectoryStream.Filter<Path> javaFiles = path -> path.toString().endsWith(".java");
    Path streamsPackage = Path.of("src/main/java/eu/happycoders/streams");
    try (DirectoryStream<Path> paths = Files.newDirectoryStream(streamsPackage, javaFiles)) {
      int count = 0;
      for (Path _ : paths) {
        count++;
      }
      System.out.println(count);
    }

    System.out.println("== TemporalAdjuster (Java 8): Year.with()");
    TemporalAdjuster nextCentury = temporal -> temporal.plus(100, ChronoUnit.YEARS);
    System.out.println(Year.of(1897).with(nextCentury));
  }
}
