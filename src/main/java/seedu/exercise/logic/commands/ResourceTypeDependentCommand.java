package seedu.exercise.logic.commands;

/**
 * Interface for commands which share the same command word but operate on different resource types
 * such as "exercise" or "regime".
 */
public interface ResourceTypeDependentCommand {

    /**
     * Returns the type of the resource being added to the model.
     *
     * @return the name of the resource being added, "exercise" or "regime"
     */
    String getResourceType();
}
