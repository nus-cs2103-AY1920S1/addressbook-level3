package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.commands.builder.EditExerciseBuilder.createEditedExercise;
import static seedu.exercise.logic.commands.events.EditExerciseEvent.KEY_EDITED_EXERCISE;
import static seedu.exercise.logic.commands.events.EditExerciseEvent.KEY_ORIGINAL_EXERCISE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_UNIT;

import java.util.List;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.commons.core.index.IndexUtil;
import seedu.exercise.logic.commands.builder.EditExerciseBuilder;
import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.EventPayload;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.ui.ListResourceType;

/**
 * Edits the details of an existing exercise in the exercise book.
 */
public class EditCommand extends Command implements UndoableCommand, PayloadCarrierCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the exercise identified "
        + "by the index number used in the displayed exercise list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: "
        + PREFIX_INDEX + "INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_DATE + "DATE] "
        + "[" + PREFIX_CALORIES + "CALORIES] "
        + "[" + PREFIX_QUANTITY + "QUANTITY] "
        + "[" + PREFIX_UNIT + "UNIT] "
        + "[" + PREFIX_MUSCLE + "MUSCLE]..."
        + "[CUSTOM_PROPERTY_PREFIX_NAME/VALUE]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_INDEX + "1 "
        + PREFIX_DATE + "03/10/2019 "
        + PREFIX_CALORIES + "800";

    public static final String MESSAGE_EDIT_EXERCISE_SUCCESS = "Edited Exercise: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EXERCISE = "This exercise already exists in the exercise book.";

    private final Index index;
    private final EditExerciseBuilder editExerciseBuilder;
    private EventPayload<Exercise> eventPayload;

    /**
     * @param index                  of the exercise in the filtered exercise list to edit
     * @param editExerciseBuilder details to edit the person with
     */
    public EditCommand(Index index, EditExerciseBuilder editExerciseBuilder) {
        requireNonNull(index);
        requireNonNull(editExerciseBuilder);

        this.index = index;
        this.eventPayload = new EventPayload<>();
        this.editExerciseBuilder = new EditExerciseBuilder(editExerciseBuilder);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getSortedExerciseList();

        if (IndexUtil.isIndexOutOfBounds(index, lastShownList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToEdit = lastShownList.get(index.getZeroBased());
        Exercise editedExercise = createEditedExercise(exerciseToEdit, editExerciseBuilder);

        if (!exerciseToEdit.isSameResource(editedExercise) && model.hasExercise(editedExercise)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXERCISE);
        }

        addToEventPayload(exerciseToEdit, editedExercise);
        model.setExercise(exerciseToEdit, editedExercise);
        EventHistory.getInstance().addCommandToUndoStack(this);
        model.updateStatistic();
        return new CommandResult(String.format(MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise),
            ListResourceType.EXERCISE);
    }

    @Override
    public String getUndoableCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public EventPayload<Exercise> getPayload() {
        return eventPayload;
    }

    /**
     * Stores the various states of the exercise to the payload.
     *
     * @param exerciseToEdit the exercise before it is edited
     * @param editedExercise the exercise after it is edited
     */
    private void addToEventPayload(Exercise exerciseToEdit, Exercise editedExercise) {
        eventPayload.put(KEY_ORIGINAL_EXERCISE, exerciseToEdit);
        eventPayload.put(KEY_EDITED_EXERCISE, editedExercise);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
            && editExerciseBuilder.equals(e.editExerciseBuilder);
    }
}
