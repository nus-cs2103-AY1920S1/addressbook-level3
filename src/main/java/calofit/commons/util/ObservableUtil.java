package calofit.commons.util;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;

/**
 * Utility class for working with JavaFx Observables.
 */
public class ObservableUtil {

    /**
     * Given an observable value, compute an observable result using the function.
     * @param source Source value
     * @param op Mapper function
     * @param <S> Source type
     * @param <T> Target type
     * @return Observable result value
     */
    public static <S, T> ObjectBinding<T> map(ObservableValue<S> source, Function<S, T> op) {
        return Bindings.createObjectBinding(() -> op.apply(source.getValue()), source);
    }

    /**
     * Given an observable double value, compute an observable double result.
     * @param expression Source value
     * @param op Mapper function
     * @return Observable result
     */
    public static DoubleExpression mapDouble(ObservableDoubleValue expression, DoubleUnaryOperator op) {
        return Bindings.createDoubleBinding(() -> op.applyAsDouble(expression.get()), expression);
    }

    /**
     * Given an observable double value, compute an observable result.
     * @param expression Source value
     * @param op Mapper function
     * @param <T> Target type
     * @return Observable result
     */
    public static <T> ObjectBinding<T> mapToObject(ObservableDoubleValue expression, DoubleFunction<T> op) {
        return Bindings.createObjectBinding(() -> op.apply(expression.get()), expression);
    }

    /**
     * Represents a function that takes 3 doubles and returns a double.
     */
    @FunctionalInterface
    public interface DoubleTernaryOperator {
        double apply(double a, double b, double c);
    }

    /**
     * Computes an observable value, given the values of 3 other observable values.
     * @param a First value
     * @param b Second value
     * @param c Third value
     * @param op Function computing the result from the values of the observables.
     * @return Observable value representing the result
     */
    public static DoubleExpression liftA3(ObservableDoubleValue a, ObservableDoubleValue b, ObservableDoubleValue c,
                                          DoubleTernaryOperator op) {
        return Bindings.createDoubleBinding(() -> op.apply(a.get(), b.get(), c.get()), a, b, c);
    }
    public static <T> DoubleExpression mapToDouble(ObservableValue<T> expression, ToDoubleFunction<T> op) {
        return Bindings.createDoubleBinding(() -> op.applyAsDouble(expression.getValue()), expression);
    }
}
