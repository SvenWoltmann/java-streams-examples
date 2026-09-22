package eu.happycoders.lambdas;

/**
 * Inside a lambda, {@code this} is the enclosing instance; inside an anonymous class, it is not.
 */
public class Scope {

  private final String name = "enclosing instance";

  void run() {
    Runnable lambda = () -> System.out.println(this.name);
    Runnable anonymous =
        new Runnable() {
          @Override
          public void run() {
            System.out.println(this.getClass().getName());
          }
        };
    lambda.run();
    anonymous.run();
  }
}
