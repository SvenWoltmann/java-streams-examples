# Java Streams Examples

[![Build](https://github.com/SvenWoltmann/java-streams-examples/actions/workflows/build.yml/badge.svg)](https://github.com/SvenWoltmann/java-streams-examples/actions/workflows/build.yml)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-streams-examples&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-streams-examples)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-streams-examples&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-streams-examples)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-streams-examples&metric=security_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-streams-examples)

## Article

The code refers to the following articles:
* English: [Java Streams (with Examples)](https://www.happycoders.eu/java/java-streams/)
* German: [Java Streams (mit Beispielen)](https://www.happycoders.eu/de/java/java-streams/)

and, in the package `eu.happycoders.lambdas`, to the articles
* English: [Java Lambda Expressions (with Examples)](https://www.happycoders.eu/java/java-lambda-expressions/)
* German: [Java Lambda-Ausdrücke (mit Beispielen)](https://www.happycoders.eu/de/java/java-lambda-ausdruecke/)

and, in the package `eu.happycoders.functionalinterfaces`, to the articles
* English: [Java Functional Interfaces (with Examples)](https://www.happycoders.eu/java/java-functional-interfaces/)
* German: [Funktionale Interfaces in Java (mit Beispielen)](https://www.happycoders.eu/de/java/java-funktionale-interfaces/)

and, in the package `eu.happycoders.reduce`, to the articles
* English: [Java Stream reduce() (with Examples)](https://www.happycoders.eu/java/java-stream-reduce/)
* German: [Java Stream reduce() (mit Beispielen)](https://www.happycoders.eu/de/java/java-stream-reduce/)

and, in the package `eu.happycoders.tomap`, to the article
* German: [Java Collectors.toMap() (mit Beispielen)](https://www.happycoders.eu/de/java/java-collectors-tomap/)

## Contents

All examples work on the same data model: the records `Book` and `Author`, the enum `Genre`, and the `Library` class with eleven public-domain classics.

Every class with a `main()` method is one chapter of the article and prints the outputs shown there. Run one with `java -cp target/classes eu.happycoders.streams.<ClassName>` after `mvn compile`:

* `Ch1Intro` – the same task as a loop and as a stream; the anatomy of a stream pipeline
* `Ch2Lambdas` – lambda expressions, method references, functional interfaces
* `Ch3Sources` – creating streams from collections, arrays, ranges, files, and generators
* `Ch4Intermediate` – `filter()`, `map()`, `flatMap()`, `mapMulti()`, `distinct()`, `sorted()`, `limit()`, `skip()`, `takeWhile()`, `dropWhile()`, `peek()`, `gather()`
* `Ch5Terminal` – `forEach()`, `collect()` and collectors, `toList()`, `toArray()`, `reduce()`, `count()`, `min()`, `max()`, `findFirst()`, `anyMatch()`, and the others
* `Ch6Lazy` – lazy evaluation: element-by-element processing, short-circuiting, stateful operations, single use
* `Ch7Parallel` – parallel streams
* `Ch8Mistakes` – common mistakes and how to avoid them

The lambda article has its own chapters in `eu.happycoders.lambdas`, on the same data model (run them with `java -cp target/classes eu.happycoders.lambdas.<ClassName>`):

* `Ch1AnonymousVsLambda` – the same comparator as an anonymous class and as a lambda
* `Ch2Syntax` – parameters, expression and block body, explicit types, `var`, the unnamed parameter `_`
* `Ch3TargetType` – the four contexts of a target type, functional interfaces, overloaded methods
* `Ch4VariableAccess` – effectively final, the one-element-array workaround, `this` (with `Scope` and `BookFilter`)
* `Ch5MethodReferences` – the four kinds of method references, `this::` and `super::`, an ambiguous reference
* `Ch6WhereUsed` – lambdas in `Comparator`, collections, maps, `Optional`, and threads
* `Ch7UnderTheHood` – the generated class, identity of capturing and non-capturing lambdas (with `LambdaDemo` for `javap`)
* `Ch8Mistakes` – a lambda that is too long, recursion, a lambda in a stack trace (with `LambdaTrace`)

The functional-interfaces article has its chapters in `eu.happycoders.functionalinterfaces` (run them with `java -cp target/classes eu.happycoders.functionalinterfaces.<ClassName>` from the repository root - two chapters read files relative to it):

* `Ch1Definition` – `Runnable` as the simplest case; `Comparator` implemented by a named class (`YearComparator`) and by a lambda; the abstract method called directly; default methods
* `Ch2Annotation` – `@FunctionalInterface` on `TitleFormatter`, the error for a second abstract method, an interface without the annotation
* `Ch3CoreFour` – `Function`, `Predicate`, `Consumer`, `Supplier` with `andThen()`, `compose()`, `identity()`, `and()`, `or()`, `negate()`, `not()`, `isEqual()`, `orElseGet()`, `toCollection()`
* `Ch4Operators` – `UnaryOperator` in `List.replaceAll()`, `BinaryOperator` in `reduce()`, `maxBy()`, `Map.merge()`
* `Ch5TwoParameters` – `BiFunction`, `BiConsumer`, `BiPredicate` in `Map.forEach()`, `computeIfPresent()`, `replaceAll()`, `Files.find()`
* `Ch6Primitives` – `ToIntFunction`, `IntPredicate`, `IntUnaryOperator`, `IntFunction`, `IntBinaryOperator`, `IntSupplier`, `ObjIntConsumer` on an `IntStream`; boxing
* `Ch7Wildcards` – `Predicate<? super T>` and `Function<? super T, ? extends R>`, and a method of our own with and without the wildcard
* `Ch8OutsidePackage` – `Runnable`, `Callable`, `Comparator`, `FileFilter`, `DirectoryStream.Filter`, `TemporalAdjuster`
* `Ch9Custom` – `TitleFormatter` vs. `Function` (nominal typing), `ThrowingFunction` for checked exceptions
* `Ch10Mistakes` – `Function<T, Boolean>` instead of `Predicate`, `Function<T, Void>` instead of `Consumer`, `UnaryOperator<Integer>` instead of `IntUnaryOperator`

The `reduce()` article has its chapters in `eu.happycoders.reduce` (run them with `java -cp target/classes eu.happycoders.reduce.<ClassName>`):

* `Ch1HowItWorks` – the sum of 1 to 5 with `reduce()` and as a loop; every call of the accumulator
* `Ch2Variants` – the three variants of `reduce()` on full and empty streams, and the two of `IntStream`
* `Ch3Applications` – product, minimum with `BinaryOperator.minBy()`, a `BigDecimal` sum (with `Prices`) and a sum of the record `Money`, `DoubleStream.sum()` against `reduce()`, predicates and functions chained with `reduce()`, and `and()`, `andThen()` and `allMatch()` as the more readable alternatives
* `Ch4Parallel` – accumulator and combiner calls in a parallel stream; a non-neutral identity, a non-associative accumulator, and the accumulator used as combiner, each with its fix
* `Ch5ReduceVsCollect` – an `ArrayList` as identity, `collect()` and `toList()`, string concatenation against `joining()`, `Collectors.reducing()` as downstream collector
* `Ch6Fold` – `Gatherers.fold()` in a parallel stream
* `Ch7Mistakes` – `get()` on an empty `Optional`, `mapToInt()` instead of boxing, overflow with `Math::multiplyExact`, `BigInteger`

The `toMap()` article has its chapters in `eu.happycoders.tomap` (run them with `java -cp target/classes eu.happycoders.tomap.<ClassName>`):

* `Ch1HowItWorks` – title to year with `toMap()` and as a loop; `Function.identity()` for a lookup map
* `Ch2Variants` – the three variants of `toMap()`: a duplicate key, the merge function, `TreeMap::new`; a throwing merge function against `collectingAndThen()`
* `Ch3Merging` – counting with `Integer::sum`, concatenating titles (and `groupingBy()` with `joining()`), the oldest book with `BinaryOperator.minBy()`
* `Ch4MapType` – the order of the `HashMap`, `LinkedHashMap` in stream order, `EnumMap` per genre
* `Ch5Null` – the `NullPointerException` for a `null` value with two and three arguments, `filter()` and `collect()` with three arguments as ways out, a `null` key in a `TreeMap`
* `Ch6Unmodifiable` – `toUnmodifiableMap()`: `UnsupportedOperationException`, merge function, `null` key
* `Ch7Applications` – filtering, transforming and inverting a map; two lists to one map
* `Ch8VsGroupingBy` – lists and counts with `toMap()` and `groupingBy()`, nested maps with `toMap()` as downstream collector
* `Ch9Parallel` – the later number wins: `toMap()` against `toConcurrentMap()` in a parallel stream
* `Ch10Mistakes` – a merge function that returns `null`, and counting first as the fix; swallowed duplicates

The code requires Java 25 or newer.


<!-- happycoders-resources:start -->
<!-- Generated from happycoders-website-astro/data/github-readme by scripts/content/sync-github-readmes.mjs. Edit there, not here. -->

## <br>Additional Resources

### <br>Java Versions PDF Cheat Sheet

**Stay up-to-date** with the latest Java features with this **free** [PDF Cheat Sheet](https://www.happycoders.eu/java-versions/)!

[<img src="/img/java-versions-cheat-sheet-mockup.png" alt="Java Versions PDF Cheat Sheet Mockup" width="468">](https://www.happycoders.eu/java-versions/)

* Avoid lengthy research with this **concise overview of all Java versions from Java 10 to Java 27**.
* **Discover the innovative features** of each new Java version, summarized on a single page.
* **Impress your team** with your up-to-date knowledge of the latest Java version.

👉 [Download the free Java Versions PDF](https://www.happycoders.eu/java-versions/)<br>

_(Hier geht's zur deutschen Version &rarr; [Java-Versionen PDF](https://www.happycoders.eu/de/java-versionen/))_


### <br>The Big O Cheat Sheet

With this **free** [1-page PDF cheat sheet](https://www.happycoders.eu/big-o-cheat-sheet/), you'll always have the **7 most important complexity classes** at a glance.

[<img src="/img/big-o-cheat-sheet-mockup.png" alt="Big O PDF Cheat Sheet Mockup" width="304">](https://www.happycoders.eu/big-o-cheat-sheet/)

* **Always choose the most efficient data structures** and thus increase the performance of your applications.
* **Be prepared for technical interviews** and confidently present your algorithm knowledge.
* **Become a sought-after problem solver** and be known for systematically tackling complex problems.

👉 [Download the free Big O Cheat Sheet](https://www.happycoders.eu/big-o-cheat-sheet/)<br>

_(Hier geht's zur deutschen Version &rarr; [O-Notation Cheat Sheet](https://www.happycoders.eu/de/o-notation-cheat-sheet/))_


### <br>HappyCoders Newsletter
👉 Want to stay on top of modern Java?
Sign up for the [HappyCoders newsletter](https://www.happycoders.eu/newsletter/) – Modern Java: new versions & features, performance, and JVM insights – once a month.

_(Hier geht's zur deutschen Version &rarr; [HappyCoders-Newsletter deutsch](https://www.happycoders.eu/de/newsletter/))_


### <br>🇩🇪 An alle Java-Entwickler:innen, die durch fundierte Kenntnisse über Datenstrukturen besseren Code schreiben wollen

Trage dich jetzt unverbindlich auf die [Warteliste](https://www.happycoders.eu/de/mastering-data-structures-warteliste/) von „Mastering Data Structures in Java“ ein, und erhalte das beste Angebot!

[<img src="/img/mastering-data-structures-product-mockup.png" alt="Mastering Data Structures Mockup" width="640">](https://www.happycoders.eu/de/mastering-data-structures-warteliste/)

👉 [Zur Warteliste](https://www.happycoders.eu/de/mastering-data-structures-warteliste/)
<!-- happycoders-resources:end -->
