package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.commands.events.DeleteRegimeEvent.KEY_REGIME_TO_DELETE;
import static seedu.exercise.logic.commands.events.EditRegimeEvent.KEY_EDITED_REGIME;
import static seedu.exercise.logic.commands.events.EditRegimeEvent.KEY_IS_REGIME_EDITED;
import static seedu.exercise.logic.commands.events.EditRegimeEvent.KEY_ORIGINAL_REGIME;

import java.util.HashSet;
import java.util.List;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.EventPayload;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.UniqueResourceList;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.ui.ListResourceType;

/**
 * Deletes a regime identified using it's name or deletes exercises in regime.
 */
public class DeleteRegimeCommand extends DeleteCommand implements PayloadCarrierCommand {

    public static final String MESSAGE_DELETE_REGIME_SUCCESS = "Deleted Regime: %1$s\n%2$s";
    public static final String MESSAGE_REGIME_DOES_NOT_EXIST = "No such regime in regime book.";
    public static final String MESSAGE_DELETE_EXERCISE_IN_REGIME_SUCCESS = "Deleted exercises in regime.";
    public static final String MESSAGE_DUPLICATE_INDEX = "There is duplicate index.";
    public static final String RESOURCE_TYPE = "regime";

    private final List<Index> indexes;
    private final Name name;
    private final EventPayload<Object> eventPayload;

    public DeleteRegimeCommand(Name name, List<Index> indexes) {
        this.name = name;
        this.indexes = indexes;
        this.eventPayload = new EventPayload<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Regime> lastShownList = model.getFilteredRegimeList();
        Regime regime = new Regime(name, new UniqueResourceList<>());
        checkValidRegime(regime, model);

        int indexOfRegime = model.getRegimeIndex(regime);
        Regime regimeToDelete = lastShownList.get(indexOfRegime);

        CommandResult commandResult;
        if (indexes == null) {
            commandResult = deleteRegimeFromModel(regimeToDelete, model);
        } else {
            commandResult = deleteExercisesFromRegime(regimeToDelete, model);
        }
        EventHistory.getInstance().addCommandToUndoStack(this);
        return commandResult;
    }

    /**
     * Deletes the specified regime completely from the model's regime list.
     *
     * @param regimeToDelete the regime to delete
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    private CommandResult deleteRegimeFromModel(Regime regimeToDelete, Model model) {
        model.deleteRegime(regimeToDelete);
        addToEventPayloadForDeleteRegime(regimeToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_REGIME_SUCCESS, name, regimeToDelete),
                ListResourceType.REGIME);
    }

    /**
     * Deletes exercises from the specified regime based on the list of {@code Index} passed into the command.
     *
     * @param originalRegime the specified regime to delete exercises from
     * @param model {@code Model} which the command should operate on
     * @return feedback message of the operation result for display
     */
    private CommandResult deleteExercisesFromRegime(Regime originalRegime, Model model) throws CommandException {
        Regime editedRegime = originalRegime.deepCopy();
        List<Exercise> currentExerciseList = originalRegime.getRegimeExercises().asUnmodifiableObservableList();
        checkValidIndexes(indexes, currentExerciseList);
        checkDuplicateIndexes(indexes);

        for (Index targetIndex : indexes) {
            Exercise exerciseToDelete = currentExerciseList.get(targetIndex.getZeroBased());
            editedRegime.deleteExercise(exerciseToDelete);
        }

        addToEventPayloadForEditRegime(originalRegime, editedRegime);
        model.setRegime(originalRegime, editedRegime);
        model.updateFilteredRegimeList(Model.PREDICATE_SHOW_ALL_REGIMES);
        return new CommandResult(String.format(MESSAGE_DELETE_EXERCISE_IN_REGIME_SUCCESS, editedRegime),
                ListResourceType.REGIME);
    }

    /**
     * Checks whether the specified regime exists in the model's regime list.
     *
     * @param regime the regime to check validity for
     * @param model {@code Model} which the command should operate on
     * @throws CommandException If the specified regime does not exist in the model's regime list
     */
    private void checkValidRegime(Regime regime, Model model) throws CommandException {
        if (!model.hasRegime(regime)) {
            throw new CommandException(MESSAGE_REGIME_DOES_NOT_EXIST);
        }
    }

    /**
     * Checks whether the list of indexes provided is valid,
     *
     * @param indexes the list of {@code Index} passed into the command
     * @param exerciseList the current exercise list of the regime
     * @throws CommandException If any one of the indexes is greater than the size of the regime's exercise list
     */
    private void checkValidIndexes(List<Index> indexes, List<Exercise> exerciseList) throws CommandException {
        for (Index targetIndex : indexes) {
            if (targetIndex.getZeroBased() >= exerciseList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
            }
        }
    }

    /**
     * Checks whether the given indexes contain duplicates.
     *
     * @throws CommandException If a duplicate index is found
     */
    private void checkDuplicateIndexes(List<Index> indexes) throws CommandException {
        HashSet<Index> set = new HashSet<>(indexes);
        if (set.size() < indexes.size()) {
            throw new CommandException(MESSAGE_DUPLICATE_INDEX);
        }
    }

    /**
     * Stores the regime to be deleted in this command.
     *
     * @param regimeToDelete the regime to be deleted
     */
    private void addToEventPayloadForDeleteRegime(Regime regimeToDelete) {
        eventPayload.put(KEY_IS_REGIME_EDITED, false);
        eventPayload.put(KEY_REGIME_TO_DELETE, regimeToDelete);
    }

    /**
     * Stores the various states of the exercise to the payload.
     *
     * @param originalRegime the regime before it is edited
     * @param editedRegime the regime after it is edited
     */
    private void addToEventPayloadForEditRegime(Regime originalRegime, Regime editedRegime) {
        eventPayload.put(KEY_IS_REGIME_EDITED, true);
        eventPayload.put(KEY_ORIGINAL_REGIME, originalRegime);
        eventPayload.put(KEY_EDITED_REGIME, editedRegime);
    }

    @Override
    public EventPayload<Object> getPayload() {
        return eventPayload;
    }

    @Override
    public String getResourceType() {
        return RESOURCE_TYPE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteRegimeCommand // instanceof handles nulls
            && indexes.equals(((DeleteRegimeCommand) other).indexes)); // state check
    }
}
