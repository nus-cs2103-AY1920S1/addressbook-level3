package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.exercise.logic.commands.events.AddRegimeEvent.KEY_REGIME_TO_ADD;
import static seedu.exercise.logic.commands.events.EditRegimeEvent.KEY_EDITED_REGIME;
import static seedu.exercise.logic.commands.events.EditRegimeEvent.KEY_IS_REGIME_EDITED;
import static seedu.exercise.logic.commands.events.EditRegimeEvent.KEY_ORIGINAL_REGIME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;

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
 * Adds a regime to the regime book.
 */
public class AddRegimeCommand extends AddCommand implements PayloadCarrierCommand {

    public static final String MESSAGE_USAGE_REGIME = "Parameters: "
        + PREFIX_CATEGORY + "CATEGORY "
        + PREFIX_NAME + "REGIME NAME "
        + PREFIX_INDEX + "INDEX\n"
        + "\t\tExample: " + COMMAND_WORD + " "
        + PREFIX_CATEGORY + "regime "
        + PREFIX_NAME + "power set "
        + PREFIX_INDEX + "1 "
        + PREFIX_INDEX + "2";

    public static final String MESSAGE_SUCCESS_NEW_REGIME = "Added new regime to regime list.";
    public static final String MESSAGE_SUCCESS_ADD_EXERCISE_TO_REGIME = "Added exercises to regime.";
    public static final String MESSAGE_DUPLICATE_EXERCISE_IN_REGIME = "Duplicate exercise found in regime.";
    public static final String MESSAGE_NO_EXERCISES_ADDED = "No index provided, nothing changes.";
    public static final String MESSAGE_DUPLICATE_INDEX = "There is duplicate index.";
    public static final String RESOURCE_TYPE = "regime";

    private List<Index> toAddIndexes;
    private Name name;
    private EventPayload<Object> eventPayload;

    public AddRegimeCommand(List<Index> indexes, Name name) {
        requireAllNonNull(indexes, name);
        this.name = name;
        this.toAddIndexes = indexes;
        this.eventPayload = new EventPayload<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Exercise> lastShownList = model.getFilteredExerciseList();
        checkDuplicateIndexes(toAddIndexes);
        checkValidIndexes(toAddIndexes, lastShownList);

        CommandResult commandResult;
        if (!isRegimeInModel(model)) {
            commandResult = addNewRegimeToModel(model);
        } else {
            commandResult = addExercisesToExistingRegime(model);
        }
        EventHistory.getInstance().addCommandToUndoStack(this);
        return commandResult;
    }

    /**
     * Adds a new regime with exercises added based on the list of {@code Index} passed into the command.
     *
     * @param model {@code Model} which the command should operate on
     * @return feedback message of the operation result for display
     */
    private CommandResult addNewRegimeToModel(Model model) throws CommandException {
        Regime regime = new Regime(name, new UniqueResourceList<>());
        addExercisesToRegime(regime, model);
        model.addRegime(regime);
        addToEventPayloadForAddRegime(regime);
        return new CommandResult(MESSAGE_SUCCESS_NEW_REGIME, ListResourceType.REGIME);
    }

    /**
     * Adds exercises to the specified regime based on the list of {@code Index} passed into the command.
     *
     * @param model {@code Model} which the command should operate on
     * @return feedback message of the operation result for display
     */
    private CommandResult addExercisesToExistingRegime(Model model) throws CommandException {
        checkIndexesNotEmpty();
        Regime originalRegime = getRegimeFromModel(model);
        Regime editedRegime = originalRegime.deepCopy();
        addExercisesToRegime(editedRegime, model);
        addToEventPayloadForEditRegime(originalRegime, editedRegime);

        model.setRegime(originalRegime, editedRegime);
        return new CommandResult(MESSAGE_SUCCESS_ADD_EXERCISE_TO_REGIME, ListResourceType.REGIME);
    }

    /**
     * Returns the actual regime object with all the existing exercises.
     *
     * @param model {@code Model} which the command should operate on
     * @return the existing regime from model
     */
    private Regime getRegimeFromModel(Model model) {
        List<Regime> regimes = model.getFilteredRegimeList();
        int regimeIndex = model.getRegimeIndex(new Regime(name, new UniqueResourceList<>()));
        return regimes.get(regimeIndex);
    }

    /**
     * Adds all exercises into the specified regime based on the given indexes.
     *
     * @param regime the regime to add exercises to
     * @param model {@code Model} which the command should operate on
     * @throws CommandException If duplicate exercises are found
     */
    private void addExercisesToRegime(Regime regime, Model model) throws CommandException {
        List<Exercise> lastShownList = model.getFilteredExerciseList();
        for (Index index : toAddIndexes) {
            Exercise exercise = lastShownList.get(index.getZeroBased());
            checkDuplicateExerciseInRegime(exercise, regime);
            regime.addExercise(exercise);
        }
    }

    /**
     * Checks whether the {@code Model} contains a regime with the same name.
     *
     * @param model {@code Model} which the command should operate on
     * @return true if a regime of the same name exists, false otherwise
     */
    private boolean isRegimeInModel(Model model) {
        Regime regime = new Regime(name, new UniqueResourceList<>());
        return model.hasRegime(regime);
    }

    /**
     * Checks whether an exercise is already found in the regime.
     *
     * @param exercise exercise to be checked against the regime's list
     * @param regime the regime to be checked with
     * @throws CommandException If a duplicate exercise is found in the regime
     */
    private void checkDuplicateExerciseInRegime(Exercise exercise, Regime regime) throws CommandException {
        if (regime.getRegimeExercises().contains(exercise)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXERCISE_IN_REGIME);
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
     * Checks if the given indexes is empty.
     *
     * @throws CommandException If no indexes are provided at all
     */
    private void checkIndexesNotEmpty() throws CommandException {
        if (toAddIndexes.size() == 0) {
            throw new CommandException(MESSAGE_NO_EXERCISES_ADDED);
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
     * Stores the regime to be added in this command.
     *
     * @param regimeToAdd the regime to be added
     */
    private void addToEventPayloadForAddRegime(Regime regimeToAdd) {
        eventPayload.put(KEY_IS_REGIME_EDITED, false);
        eventPayload.put(KEY_REGIME_TO_ADD, regimeToAdd);
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
    public String getCommandTypeIdentifier() {
        return RESOURCE_TYPE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddRegimeCommand // instanceof handles nulls
            && toAddIndexes.equals(((AddRegimeCommand) other).toAddIndexes));
    }


}
