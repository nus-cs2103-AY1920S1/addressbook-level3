package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Stack;

/**
 * A history of models throughout execution, to facilitate model manipulation through history.
 */
public class ModelHistory implements ReadOnlyModelHistory {

    private String description;
    private Stack<Model> pastModels;
    private Stack<Model> futureModels;

    public ModelHistory(String description, Stack<Model> pastModels, Stack<Model> futureModels) {
        setDescription(description);
        setPastModels(pastModels);
        setFutureModels(futureModels);
    }

    public ModelHistory() {
        this("", new Stack<>(), new Stack<>());
    }

    /**
     * Copy constructor for ModelHistory.
     */
    public ModelHistory(ReadOnlyModelHistory history) {
        this();
        resetData(history);
    }

    /**
     * Replaces model history data with the given history.
     */
    public void resetData(ReadOnlyModelHistory history) {
        requireNonNull(history);
        setDescription(history.getDescription());
        setPastModels(history.getPastModels());
        setFutureModels(history.getFutureModels());
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Stack<Model> getPastModels() {
        return pastModels;
    }

    @Override
    public Stack<Model> getFutureModels() {
        return futureModels;
    }

    public void setDescription(String description) {
        this.description = description;
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
     * Adds the model state to the past models history.
     */
    public void addToPastModels(Model model) {
        requireNonNull(model);
        pastModels.push(model);
    }

    /**
     * Adds the model state to the future models history.
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
     * Returns the previous model in history and removes it from the history, if exists.
     */
    public Model getPrevModel() {
        if (isPastModelsEmpty()) {
            return null;
        }

        return pastModels.pop();
    }

    /**
     * Returns and removes the next model in history, if exists.
     */
    public Model getNextModel() {
        if (isFutureModelsEmpty()) {
            return null;
        }

        return futureModels.pop();
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

    // Debug purposes

    @Override
    public String toString() {
        return String.format("%d past and %d future models", pastModels.size(), futureModels.size());
    }
}
