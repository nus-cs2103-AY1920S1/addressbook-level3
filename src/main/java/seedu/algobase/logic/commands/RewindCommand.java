package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.commandhistory.CommandHistory;

/**
 * Rewinds the content of CommandBox to a previous successfully executed command.
 */
public class RewindCommand extends Command {

    public static final String COMMAND_WORD = "rewind";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Rewinds the command input to a previous successfully executed command input.\n"
        + "Parameters: INDEX (must be a positive integer, represents how many commands ago you want to rewind to)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Rewind successfully!";
    public static final String REWIND_TO_LAST_COMMAND_TEXT = COMMAND_WORD + " 1";

    private final Index targetIndex;

    public RewindCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<CommandHistory> commandHistories = model.getCommandHistoryList();

        if (targetIndex.getOneBased() > commandHistories.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REWIND_NUMBER);
        }

        CommandHistory commandHistoryToShow = commandHistories.get(
            commandHistories.size() - targetIndex.getOneBased());
        return new CommandResult(commandHistoryToShow.getCommandText(), false, false, true);
    }
}
