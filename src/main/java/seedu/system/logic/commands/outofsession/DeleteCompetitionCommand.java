package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.system.commons.core.Messages;
import seedu.system.commons.core.index.Index;
import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.CommandException;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.model.Model;
import seedu.system.model.competition.Competition;

/**
 * Deletes a competition identified using it's displayed index from the system.
 */
public class DeleteCompetitionCommand extends Command {

    public static final String COMMAND_WORD = "deleteCompetition";
    public static final CommandType COMMAND_TYPE = CommandType.COMPETITION;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the competition identified by the index number used in the displayed competition list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_COMPETITION_SUCCESS = "Deleted competition: %1$s";

    private final Index targetIndex;

    public DeleteCompetitionCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Competition> lastShownList = model.getFilteredCompetitionList();

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPETITION_DISPLAYED_INDEX);
        }

        Competition compToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCompetition(compToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_COMPETITION_SUCCESS, compToDelete), COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCompetitionCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCompetitionCommand) other).targetIndex)); // state check
    }


}
