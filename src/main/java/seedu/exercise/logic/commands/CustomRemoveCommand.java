package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_REMOVE_CUSTOM;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.commons.core.index.IndexUtil;
import seedu.exercise.logic.commands.builder.EditExerciseBuilder;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.property.PropertyBook;
import seedu.exercise.model.resource.Exercise;

//@@author weihaw08
/**
 * Removes the custom property with the given full name.
 */
public class CustomRemoveCommand extends CustomCommand {

    public static final String MESSAGE_USAGE_CUSTOM_REMOVE = "Parameters: "
        + PREFIX_REMOVE_CUSTOM + "FULL NAME"
        + " [" + PREFIX_INDEX + "INDEX" + "]\t"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_REMOVE_CUSTOM + "Rating ";

    public static final String MESSAGE_SUCCESS_ALL_REMOVED = "Custom property removed: %1$s";
    public static final String MESSAGE_SUCCESS_SINGLE_REMOVED = "%1$s removed for exercise %2$s";
    public static final String MESSAGE_FULL_NAME_NOT_FOUND = "This full name is not used by an "
        + "existing custom property";

    private static final Logger logger = LogsCenter.getLogger(CustomRemoveCommand.class);
    private final String toRemove;
    private final Optional<Index> index;

    public CustomRemoveCommand(String toRemove, Optional<Index> index) {
        requireAllNonNull(toRemove, index);
        this.toRemove = toRemove;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        PropertyBook propertyBook = PropertyBook.getInstance();
        if (!propertyBook.isFullNameUsedByCustomProperty(toRemove)) {
            throw new CommandException(MESSAGE_FULL_NAME_NOT_FOUND);
        }

        if (index.isEmpty()) {
            logger.info("Removing " + toRemove + " from the app");
            propertyBook.removeCustomProperty(toRemove);
            updateCustomPropertiesOfAllExercises(model);
            return new CommandResult(String.format(MESSAGE_SUCCESS_ALL_REMOVED, toRemove));
        } else {
            logger.info("Removing " + toRemove + " from a single exercise");
            Index indexToRemove = index.get();
            updateCustomPropertiesOfSingleExercise(model, indexToRemove);
            return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE_REMOVED, toRemove,
                indexToRemove.getOneBased()));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof CustomRemoveCommand)
            && (toRemove.equals(((CustomRemoveCommand) other).toRemove))
            && (index.equals(((CustomRemoveCommand) other).index));
    }


    /**
     * Updates the old custom properties map of an exercise with the updated custom properties.
     *
     * @param oldPropertiesMap the old custom properties map of an exercise
     * @return a new map consisting of the updated custom properties
     */
    private Map<String, String> updateCustomPropertiesMap(Map<String, String> oldPropertiesMap) {
        Map<String, String> updatedMap = new TreeMap<>();
        Set<String> keySet = oldPropertiesMap.keySet();
        for (String property : keySet) {
            if (!property.equals(toRemove)) {
                updatedMap.put(property, oldPropertiesMap.get(property));
            }
        }
        return updatedMap;
    }

    /**
     * Updates the custom properties of the given {@code exercise}.
     *
     * @return a new {@code Exercise} object containing the updated custom properties. The other properties are
     *     kept the same.
     */
    private Exercise updateExerciseCustomProperty(Exercise exercise) {
        EditExerciseBuilder editExerciseBuilder = new EditExerciseBuilder(exercise);
        Map<String, String> oldCustomProperties = exercise.getCustomPropertiesMap();
        Map<String, String> newCustomProperties = updateCustomPropertiesMap(oldCustomProperties);
        editExerciseBuilder.setCustomProperties(newCustomProperties);
        return editExerciseBuilder.buildEditedExercise();
    }

    /**
     * Updates the custom properties of the exercise at the given index in the model.
     */
    private void updateCustomPropertiesOfSingleExercise(Model model, Index index) throws CommandException {
        ObservableList<Exercise> modelList = model.getSortedExerciseList();
        if (IndexUtil.isIndexOutOfBounds(index, modelList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }
        Exercise oldExercise = modelList.get(index.getZeroBased());
        Exercise updatedExercise = updateExerciseCustomProperty(oldExercise);
        model.setExercise(oldExercise, updatedExercise);
    }

    /**
     * Updates the custom properties of all the exercises in the given {@code model}.
     */
    private void updateCustomPropertiesOfAllExercises(Model model) {
        List<Exercise> exerciseList = model.getSortedExerciseList();
        for (Exercise oldExercise : exerciseList) {
            Exercise updatedExercise = updateExerciseCustomProperty(oldExercise);
            model.setExercise(oldExercise, updatedExercise);
        }
    }
}
