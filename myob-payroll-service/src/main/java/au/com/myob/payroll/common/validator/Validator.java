package au.com.myob.payroll.common.validator;

import au.com.myob.payroll.common.exception.MYOBEmployeeDetailsValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Class representing Monad design pattern. Monad is a way of chaining operations on the
 * given object together step by step. In Validator each step results in either success or
 * failure indicator, giving a way of receiving each of them easily and finally getting
 * validated object or list of exceptions.
 *
 * @param <T> Placeholder for an object.
 */
public class Validator<T> {
  /**
   * Object that is validated
   */
  private final T t;

  /**
   * List of exception thrown during validation.
   */
  private final List<Throwable> exceptions = new ArrayList<>();

  /**
   * Creates a monadic value of given object.
   * @param t object to be validated
   */
  private Validator(T t) {
    this.t = t;
  }

  /**
   * Creates validator against given object
   *
   * @param t   object to be validated
   * @param <T> object's type
   * @return new instance of a validator
   */
  public static <T> Validator<T> of(T t) {
    return new Validator<>(Objects.requireNonNull(t));
  }

  /**
   * @param validation one argument boolean-valued function that
   *                   represents one step of validation. Adds exception to main validation exception
   *                   list when single step validation ends with failure.
   * @param message    error message when object is invalid
   * @return this
   */
  public Validator<T> validate(Predicate<T> validation, String message) {
    if (!validation.test(t)) {
      exceptions.add(new IllegalStateException(message));
    }
    return this;
  }

  /**
   * Extension for the {@link Validator#validate(Function, Predicate, String)} method,
   * dedicated for objects, that need to be projected before requested validation.
   *
   * @param projection function that gets an objects, and returns projection representing
   *                   element to be validated.
   * @param validation see {@link Validator#validate(Function, Predicate, String)}
   * @param message    see {@link Validator#validate(Function, Predicate, String)}
   * @param <U>        see {@link Validator#validate(Function, Predicate, String)}
   * @return this
   */
  public <U> Validator<T> validate(Function<T, U> projection, Predicate<U> validation,
                                   String message) {
    return validate(projection.andThen(validation::test)::apply, message);
  }

  /**
   * Receives validated object or throws exception when invalid.
   *
   * @return object that was validated
   * @throws MYOBEmployeeDetailsValidationException when any validation step results with failure
   */
  public T get() throws MYOBEmployeeDetailsValidationException {
    if (exceptions.isEmpty()) {
      return t;
    }
    IllegalStateException e = new IllegalStateException();
    exceptions.forEach(e::addSuppressed);
    throw e;
  }
}
