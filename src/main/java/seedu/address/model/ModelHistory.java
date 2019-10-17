package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.Stack;

/**
 * A history of models throughout execution, to facilitate easy rollback and migration.
 */
public class ModelHistory implements ReadOnlyModelHistory {
    private Stack<Model> pastModels;
    private Stack<Model> futureModels;

    public ModelHistory(Stack<Model> pastModels, Stack<Model> futureModels) {
        setPastModels(pastModels);
        setFutureModels(futureModels);
    }

    public ModelHistory() {
        this(new Stack<>(), new Stack<>());
    }

    /**
     * Copy constructor for ModelHistory.
     */
    public ModelHistory(ReadOnlyModelHistory history) {
        this(history.getPastModels(), history.getFutureModels());
    }

    /**
     * Replaces model history data with the given history.
     */
    public void resetData(ReadOnlyModelHistory history) {
        requireNonNull(history);
        setPastModels(history.getPastModels());
        setFutureModels(history.getFutureModels());
    }

    @Override
    public Stack<Model> getPastModels() {
        return pastModels;
    }

    @Override
    public Stack<Model> getFutureModels() {
        return futureModels;
    }

    public void setPastModels(Stack<Model> pastModels) {
        requireNonNull(pastModels);
        this.pastModels = (Stack<Model>) pastModels.clone();
    }

    public void setFutureModels(Stack<Model> futureModels) {
        requireNonNull(futureModels);
        this.futureModels = (Stack<Model>) futureModels.clone();
    }

    /**
     * Adds the copy of a model to the past models history.
     */
    public void addToPastModels(Model model) {
        requireNonNull(model);
        pastModels.push(model);
    }

    /**
     * Adds the copy of a model to the future models history.
     */
    public void addToFutureModels(Model model) {
        requireNonNull(model);
        futureModels.push(model);
    }

    /**
     * Clears future models history.
     */
    public void clearFutureModels() {
        futureModels.clear();
    }

    /**
     * Checks whether there are no past models.
     */
    public boolean isPastModelsEmpty() {
        return pastModels.isEmpty();
    }

    /**
     * Checks whether there are no future models.
     */
    public boolean isFutureModelsEmpty() {
        return futureModels.isEmpty();
    }

    /**
     * Returns the previous model in history, if exists.
     */
    public Optional<Model> getPrevModel() {
        if (isPastModelsEmpty()) {
            return Optional.empty();
        }

        Model prevModel = pastModels.pop();
        return Optional.of(prevModel);
    }

    /**
     * Returns the next model in history, if exists.
     */
    public Optional<Model> getNextModel() {
        if (isFutureModelsEmpty()) {
            return Optional.empty();
        }

        Model nextModel = futureModels.pop();
        return Optional.of(nextModel);
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
        return pastModels.equals(other.pastModels)
                && futureModels.equals(other.futureModels);
    }

    @Override
    public String toString() {
        return String.format("%d past and %d future models", pastModels.size(), futureModels.size());
    }
}
