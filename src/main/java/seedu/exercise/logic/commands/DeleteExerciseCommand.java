package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.commands.events.DeleteExerciseEvent.KEY_EXERCISE_TO_DELETE;

import java.util.List;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.EventPayload;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;

/**
 * Deletes an exercise identified using it's displayed index from the exercise book.
 */
public class DeleteExerciseCommand extends DeleteCommand implements PayloadCarrierCommand {

    public static final String MESSAGE_DELETE_EXERCISE_SUCCESS = "Deleted Exercise: %1$s";
    public static final String RESOURCE_TYPE = "exercise";

    private final Index targetIndex;
    private EventPayload<Exercise> eventPayload;

    public DeleteExerciseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.eventPayload = new EventPayload<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToDelete = lastShownList.get(targetIndex.getZeroBased());
        eventPayload.put(KEY_EXERCISE_TO_DELETE, exerciseToDelete);
        model.deleteExercise(exerciseToDelete);
        EventHistory.getInstance().addCommandToUndoStack(this);
        return new CommandResult(String.format(MESSAGE_DELETE_EXERCISE_SUCCESS, exerciseToDelete));
    }

    @Override
    public EventPayload<Exercise> getPayload() {
        return eventPayload;
    }

    @Override
    public String getResourceType() {
        return RESOURCE_TYPE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteExerciseCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteExerciseCommand) other).targetIndex)); // state check
    }
}
