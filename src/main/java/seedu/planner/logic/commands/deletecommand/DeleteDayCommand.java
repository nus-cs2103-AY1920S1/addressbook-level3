package seedu.planner.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.day.Day;

/**
 * Removes a Day from the itinerary.
 */
public class DeleteDayCommand extends DeleteCommand {
    public static final String SECOND_COMMAND_WORD = "day";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Deletes the day identified by the index "
                    + "number used in the displayed day list.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " INDEX(must be a positive integer)",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 4"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "<INDEX>"
    );

    public static final String MESSAGE_DELETE_DAY_SUCCESS = "Deleted day: %1$d";

    private final Index targetIndex;
    private final Day toDelete;
    private final boolean isUndoRedo;

    public DeleteDayCommand(Index targetIndex, boolean isUndoRedo) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        toDelete = null;
        this.isUndoRedo = isUndoRedo;
    }

    // Constructor used to create DeleteDayEvent
    public DeleteDayCommand(Index targetIndex, Day day) {
        requireNonNull(day);
        toDelete = day;
        this.targetIndex = targetIndex;
        this.isUndoRedo = true;
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    public Day getToDelete() {
        return toDelete;
    }

    @Override
    public String getSecondCommandWord() {
        return SECOND_COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Day> lastShownList = model.getFilteredItinerary();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DAY_DISPLAYED_INDEX);
        }
        Day dayToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (!isUndoRedo) {
            // Not due to redo method of DeleteDayEvent
            DeleteDayCommand newCommand = new DeleteDayCommand(targetIndex, dayToDelete);
            updateEventStack(newCommand, model);
        }
        model.shiftDatesInItineraryByDayBetweenRange(-1, Index.fromOneBased(targetIndex.getOneBased() + 1),
                Index.fromOneBased(model.getNumberOfDays() + 1));
        model.deleteDay(dayToDelete);

        return new CommandResult(
            String.format(MESSAGE_DELETE_DAY_SUCCESS, targetIndex.getOneBased()),
            new UiFocus[] {UiFocus.AGENDA}
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDayCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteDayCommand) other).targetIndex)); // state check
    }
}
