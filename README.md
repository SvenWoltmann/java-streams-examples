# Java Streams Examples

[![Build](https://github.com/SvenWoltmann/java-streams-examples/actions/workflows/build.yml/badge.svg)](https://github.com/SvenWoltmann/java-streams-examples/actions/workflows/build.yml)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-streams-examples&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-streams-examples)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-streams-examples&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-streams-examples)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_java-streams-examples&metric=security_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_java-streams-examples)

## Article

The code refers to the following articles:
* English: [Java Streams (with Examples)](https://www.happycoders.eu/java/java-streams/)
* German: [Java Streams (mit Beispielen)](https://www.happycoders.eu/de/java/java-streams/)

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
