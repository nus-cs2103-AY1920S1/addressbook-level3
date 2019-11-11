package seedu.tarence.logic.commands;

import static seedu.tarence.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.storage.Storage;

/**
 * Executes one of the stored suggested commands in the application.
 */
public class SelectSuggestionCommand extends Command {

    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "Invalid input- please enter a number corresponding to "
            + "one of the listed suggested commands.";

    private final Index index;

    public SelectSuggestionCommand(Index index) {
        this.index = index;
    }

    /**
     * Used to create a "dummy" instance of the command, to act as a flag in the pending command stack to prompt the
     * user for input of the desired index.
     */
    public SelectSuggestionCommand() {
        this.index = null;
    }

    /**
     * Returns whether or not the input command is an integer.
     */
    public static boolean isMatchingCommandWord(String userCommand) {
        try {
            Integer.parseInt(userCommand);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (index == null) {
            // dummy command- return empty command result
            return new CommandResult("");
        }

        if (model.getSuggestedCommands().size() == 0) {
            throw new CommandException(MESSAGE_UNKNOWN_COMMAND);
        }

        if (index.getOneBased() > model.getSuggestedCommands().size()) {
            throw new CommandException(MESSAGE_INDEX_OUT_OF_BOUNDS + "\n" + model.getSuggestedCorrections());
        }
        Command commandToExecute = model.getSuggestedCommands().get(index.getZeroBased());
        model.deleteSuggestedCommands();
        return commandToExecute.execute(model);
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    @Override
    public boolean needsInput() {
        return index == null;
    }
}
