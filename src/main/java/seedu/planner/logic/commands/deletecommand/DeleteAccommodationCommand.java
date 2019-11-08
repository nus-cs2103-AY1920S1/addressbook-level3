package seedu.planner.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.logic.events.Event;
import seedu.planner.logic.events.EventFactory;
import seedu.planner.model.Model;
import seedu.planner.model.accommodation.Accommodation;

/**
 * Removes an accommodation from the accommodation list.
 */
public class DeleteAccommodationCommand extends DeleteCommand {
    public static final String SECOND_COMMAND_WORD = "accommodation";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Deletes the accommodation identified by the index "
                    + "number used in the displayed accommodation list.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " INDEX(must be a positive integer)",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 1"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "<INDEX>"
    );


    public static final String MESSAGE_DELETE_ACCOMMODATION_SUCCESS = "Deleted Accommodation: %1$s";

    private final Index targetIndex;
    private final Accommodation toDelete;

    public DeleteAccommodationCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        toDelete = null;
    }

    //Constructor used to undo AddAccommodationEvent and create DeleteAccommodationEvent
    public DeleteAccommodationCommand(Index targetIndex, Accommodation accommodation) {
        requireNonNull(accommodation);
        toDelete = accommodation;
        this.targetIndex = targetIndex;
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    public Accommodation getToDelete() {
        return toDelete;
    }

    @Override
    public String getSecondCommandWord() {
        return SECOND_COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Accommodation> lastShownList = model.getFilteredAccommodationList();
        Accommodation accommodationToDelete;
        if (toDelete != null) {
            accommodationToDelete = toDelete;
        } else if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACCOMMODATION_DISPLAYED_INDEX);
        } else {
            accommodationToDelete = lastShownList.get(targetIndex.getZeroBased());
        }
        Index indexOfAccommodation = findIndexOfAccommodation(model, accommodationToDelete);

        if (toDelete == null) {
            //Not due to undo method
            DeleteAccommodationCommand newCommand = new DeleteAccommodationCommand(indexOfAccommodation,
                    accommodationToDelete);
            Event deleteAccommodationEvent = EventFactory.parse(newCommand, model);
            CommandHistory.addToUndoStack(deleteAccommodationEvent);
            CommandHistory.clearRedoStack();
        }

        model.deleteAccommodation(accommodationToDelete);
        return new CommandResult(
            String.format(MESSAGE_DELETE_ACCOMMODATION_SUCCESS, accommodationToDelete),
            new ResultInformation[]{
                new ResultInformation(
                        accommodationToDelete,
                        indexOfAccommodation,
                        String.format(MESSAGE_DELETE_ACCOMMODATION_SUCCESS, "")
                )
            },
            new UiFocus[] { UiFocus.ACCOMMODATION, UiFocus.INFO }
        );
    }

    /**
     * Returns the index of accommodation in the model.
     * Precondition: the {@code accommodation} must have not been deleted before this.
     */
    private Index findIndexOfAccommodation(Model model, Accommodation accommodation) {
        Optional<Index> indexOfAccommodation = model.getAccommodationIndex(accommodation);
        if (indexOfAccommodation.isEmpty()) {
            throw new AssertionError("Accommodation should not have been deleted before this.");
        }
        return indexOfAccommodation.get();
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAccommodationCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteAccommodationCommand) other).targetIndex)); // state check
    }
}
