package thrift.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A class to ease converting an {@code Iterator} object into a {@code Stream} object. This will be useful when
 * converting a specified {@code Collections.List} object into a pure {@code Stream} object.
 * Source: https://stackoverflow.com/a/28118885
 */
public class StreamUtils {

    /**
     * Takes the specified {@code Iterator<T>} object and converts it into {@code Stream<T>} to return.
     */
    public static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
        requireNonNull(sourceIterator);
        return asStream(sourceIterator, false);
    }

    /**
     * Takes the specified {@code Iterator<T>} object and converts it into {@code Stream<T>} to return.
     * This method allows for specifying if the {@code Stream} will support parallelism.
     */
    public static <T> Stream<T> asStream(Iterator<T> sourceIterator, boolean parallel) {
        requireNonNull(sourceIterator);
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }
}
