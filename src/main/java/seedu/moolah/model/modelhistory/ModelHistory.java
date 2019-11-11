package seedu.moolah.model.modelhistory;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.Stack;

/**
 * A history of model changes throughout execution to facilitate model manipulation through history.
 */
public class ModelHistory implements ReadOnlyModelHistory {

    private Stack<ModelChanges> pastChanges;
    private Stack<ModelChanges> futureChanges;

    public ModelHistory(Stack<ModelChanges> pastChanges, Stack<ModelChanges> futureModels) {
        setPastChanges(pastChanges);
        setFutureChanges(futureModels);
    }

    public ModelHistory() {
        this(new Stack<>(), new Stack<>());
    }

    /**
     * Copy constructor for ModelHistory.
     */
    public ModelHistory(ReadOnlyModelHistory history) {
        this();
        resetData(history);
    }

    /**
     * Sets model history data with the copy of the given model history.
     */
    public void resetData(ReadOnlyModelHistory history) {
        requireNonNull(history);
        setPastChanges(history.getPastChanges());
        setFutureChanges(history.getFutureChanges());
    }

    @Override
    public ReadOnlyModelHistory copy() {
        return new ModelHistory(this);
    }

    @Override
    public Stack<ModelChanges> getPastChanges() {
        return pastChanges;
    }

    @Override
    public Stack<ModelChanges> getFutureChanges() {
        return futureChanges;
    }

    /**
     * Sets past changes data with the copy of the given past changes.
     */
    public void setPastChanges(Stack<ModelChanges> pastChanges) {
        requireNonNull(pastChanges);
        this.pastChanges = new Stack<>();
        for (ModelChanges change : pastChanges) {
            this.pastChanges.push(change);
        }
    }

    /**
     * Sets future changes data with the copy of the given future changes.
     */
    public void setFutureChanges(Stack<ModelChanges> futureChanges) {
        requireNonNull(futureChanges);
        this.futureChanges = new Stack<>();
        for (ModelChanges change : futureChanges) {
            this.futureChanges.push(change);
        }
    }

    /**
     * Adds the given model changes to the past changes history.
     */
    public void addToPastChanges(ModelChanges change) {
        requireNonNull(change);
        pastChanges.push(change);
    }

    /**
     * Adds the given model changes to the future changes history.
     */
    public void addToFutureChanges(ModelChanges change) {
        requireNonNull(change);
        futureChanges.push(change);
    }

    /**
     * Clears future changes history.
     */
    public void clearFutureChanges() {
        futureChanges.clear();
    }

    /**
     * Checks whether there are no past changes.
     */
    public boolean isPastChangesEmpty() {
        return pastChanges.isEmpty();
    }

    /**
     * Checks whether there are no future changes.
     */
    public boolean isFutureChangesEmpty() {
        return futureChanges.isEmpty();
    }

    /**
     * Returns and removes the previous changes in history if exists.
     */
    public Optional<ModelChanges> getPrevChanges() {
        if (isPastChangesEmpty()) {
            return Optional.empty();
        }

        return Optional.of(pastChanges.pop());
    }

    /**
     * Returns and removes the next changes in history if exists.
     */
    public Optional<ModelChanges> getNextChanges() {
        if (isFutureChangesEmpty()) {
            return Optional.empty();
        }

        return Optional.of(futureChanges.pop());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ModelHistory)) {
            return false;
        }

        ModelHistory other = (ModelHistory) obj;
        return pastChanges.equals(other.pastChanges)
                && futureChanges.equals(other.futureChanges);
    }

    // Debug purposes

    @Override
    public String toString() {
        return String.format("%d past and %d future changes", pastChanges.size(), futureChanges.size());
    }
}
