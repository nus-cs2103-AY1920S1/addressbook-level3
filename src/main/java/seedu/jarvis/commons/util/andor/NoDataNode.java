package seedu.jarvis.commons.util.andor;

import java.util.Optional;

/**
 * Represents an AndOrNode with no data.
 * @param <T> generic type
 */
public abstract class NoDataNode<T> extends AndOrNode<T> {
    protected NoDataNode() {
        super();
    }

    /**
     * Returns an {@code Optional.empty}.
     * @return
     */
    @Override
    protected Optional<T> getData() {
        return Optional.empty();
    }
}
