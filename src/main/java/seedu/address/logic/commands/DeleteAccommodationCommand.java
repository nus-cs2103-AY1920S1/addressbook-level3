package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.result.ResultInformation;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.logic.commands.util.HelpExplanation;
import seedu.address.model.Model;
import seedu.address.model.accommodation.Accommodation;

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

    public static final String MESSAGE_DELETE_ACCOMMODATION_SUCCESS = "Deleted Accommodation: %1$s";

    private final Index targetIndex;
    private final Accommodation toDelete;

    public DeleteAccommodationCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        toDelete = null;
    }

    public DeleteAccommodationCommand(Accommodation accommodation) {
        toDelete = accommodation;
        targetIndex = null;
    }

    public Index getTargetIndex() {
        return targetIndex;
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
