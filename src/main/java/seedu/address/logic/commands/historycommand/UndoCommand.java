package seedu.address.logic.commands.historycommand;

import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;

/**
 * Command that undoes the effects of the previous command, returning the model to its previous state.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Un-did %1$s command(s)";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the previous command.\n"
                                                + "Format: " + COMMAND_WORD + " [NUMBER_OF_COMMANDS_TO_UNDO]\n"
                                                + "Note that NUMBER_OF_COMMANDS_TO_UNDO must be a number from 1 to 49\n"
                                                + "and must be less than or equal to the number of commands that can\n"
                                                + "be undone (see the output of the `history` command for this";
    private int numToUndo;

    /**
     * Constructor for UndoCommand, indicating number of commands to undo.
     * @param numToUndo the number of commands to undo in ModelHistory.
     */
    public UndoCommand(int numToUndo) {
        this.numToUndo = numToUndo;
    }

    /**
     * Executes the command and returns a CommandResult with a message.
     * @param model contains the state of the data in memory.
     * @return feedback message of the operation result for display.
     * @throws CommandException
     */
    public CommandResult execute(Model model) throws CommandException {
        try {
            model.undo(this.numToUndo);
            model.updateHistory(this);
            model.recordCommandExecution(this.getCommandInputString());
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.numToUndo), CommandType.H);
        } catch (AlfredModelHistoryException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UndoCommand)) {
            return false;
        }

        UndoCommand otherCommand = (UndoCommand) other;
        return otherCommand.numToUndo == this.numToUndo;
    }
}
