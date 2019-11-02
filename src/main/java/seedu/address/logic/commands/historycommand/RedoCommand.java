package seedu.address.logic.commands.historycommand;

import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;

/**
 * Command that redoes the effects of the previous command, returning the model to the state after re-doing the command.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Un-did %1$s command(s)";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes the previous command.\n"
                                                + "Format: " + COMMAND_WORD + " [NUMBER_OF_COMMANDS_TO_REDO]\n"
                                                + "Note that NUMBER_OF_COMMANDS_TO_REDO must be a number from 1 to 49\n"
                                                + "and must be less than or equal to the number of commands that can\n"
                                                + "be redone (see the output of the `history` command for this";
    private int numToRedo;

    /**
     * Constructor for RedoCommand, indicating number of commands to redo.
     * @param numToRedo the number of commands to redo in ModelHistory.
     */
    public RedoCommand(int numToRedo) {
        this.numToRedo = numToRedo;
    }

    /**
     * Executes the command and returns a CommandResult with a message.
     * @param model contains the state of the data in memory.
     * @return feedback message of the operation result for display.
     * @throws CommandException
     */
    public CommandResult execute(Model model) throws CommandException {
        try {
            model.redo(this.numToRedo);
            model.updateHistory(this);
            model.recordCommandExecution(this.getCommandInputString());
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.numToRedo), CommandType.H);
        } catch (AlfredModelHistoryException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RedoCommand)) {
            return false;
        }

        RedoCommand otherCommand = (RedoCommand) other;
        return otherCommand.numToRedo == this.numToRedo;
    }
}
