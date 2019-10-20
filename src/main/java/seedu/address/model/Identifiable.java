package seedu.address.model;

/**
 * Classes that implement this interface declare that their instances are able to be uniquely identified.
 * @param <T> Any class whose instances are capable of being identified uniquely.
 */
public interface Identifiable<T> {
    boolean isSameAs(T other);
}
