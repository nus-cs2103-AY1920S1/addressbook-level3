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

    public ModelHistory() {
        this.pastModels = new Stack<>();
        this.futureModels = new Stack<>();
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
        pastModels = (Stack<Model>) history.getPastModels().clone();
        futureModels = (Stack<Model>) history.getFutureModels().clone();
    }

    @Override
    public Stack<Model> getPastModels() {
        return pastModels;
    }

    @Override
    public Stack<Model> getFutureModels() {
        return futureModels;
    }

    /**
     * Adds a model to the past models history.
     */
    public void addToPastModels(Model model) {
        requireNonNull(model);
        pastModels.push(model);
    }

    /**
     * Adds a model to the future models history.
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
     * Returns the previous model in history, if exists.
     */
    public Optional<Model> getPrevModel() {
        if (pastModels.empty()) {
            return Optional.empty();
        }

        Model prevModel = pastModels.pop();
        return Optional.of(prevModel);
    }

    /**
     * Returns the next model in history, if exists.
     */
    public Optional<Model> getNextModel() {
        if (futureModels.empty()) {
            return Optional.empty();
        }

        Model nextModel = futureModels.pop();
        return Optional.of(nextModel);
    }
}
