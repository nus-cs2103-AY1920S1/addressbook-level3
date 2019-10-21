package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.competition.Competition;

/**
 * Deletes a competition identified using it's displayed index from the address book.
 */
public class DeleteCompCommand extends Command {

    public static final String COMMAND_WORD = "deleteComp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the competition identified by the index number used in the displayed competition list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_COMPETITION_SUCCESS = "Deleted Competition: %1$s";

    private final Index targetIndex;

    public DeleteCompCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Competition> lastShownList = model.getFilteredCompetitionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPETITION_DISPLAYED_INDEX);
        }

        Competition compToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCompetition(compToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_COMPETITION_SUCCESS, compToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCompCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCompCommand) other).targetIndex)); // state check
    }


}
