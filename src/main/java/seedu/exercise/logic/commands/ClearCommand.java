package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.commands.events.ClearEvent.KEY_EXERCISE_BOOK_CLEARED;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;

import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.EventPayload;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.ui.ListResourceType;

/**
 * Clears the exercise book.
 */
public class ClearCommand extends Command implements UndoableCommand, PayloadCarrierCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Exercise book has been cleared!";
    public static final String MESSAGE_EMPTY_EXERCISE_LIST = "Exercise book is already empty.";

    private EventPayload<ReadOnlyResourceBook<Exercise>> eventPayload;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ReadOnlyResourceBook<Exercise> exerciseBookCleared =
                new ReadOnlyResourceBook<>(model.getExerciseBookData(), DEFAULT_EXERCISE_COMPARATOR);
        if (checkIsExerciseBookEmpty(exerciseBookCleared)) {
            throw new CommandException(MESSAGE_EMPTY_EXERCISE_LIST);
        }
        eventPayload = new EventPayload<>();
        eventPayload.put(KEY_EXERCISE_BOOK_CLEARED, exerciseBookCleared);
        EventHistory.getInstance().addCommandToUndoStack(this);

        model.setExerciseBook(new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR));
        model.updateStatistic();
        return new CommandResult(MESSAGE_SUCCESS, ListResourceType.EXERCISE);
    }

    @Override
    public String getUndoableCommandWord() {
        return COMMAND_WORD;
    }

    private boolean checkIsExerciseBookEmpty(ReadOnlyResourceBook<Exercise> exerciseBook) {
        return exerciseBook.getSortedResourceList().isEmpty();
    }

    @Override
    public EventPayload<ReadOnlyResourceBook<Exercise>> getPayload() {
        return eventPayload;
    }

}
