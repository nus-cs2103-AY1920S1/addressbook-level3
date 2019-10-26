package seedu.address.model;

import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.ui.EntityCard;

/**
 * Represents a Previous, Current or Future Command in History.
 */
public class CommandRecord {
    /**
     * Represents the CommandType of a CommandRecord.
     * As the user uses the undo or redo command,
     * the commandtype will represent whether it
     * is a future command for redo, past command for undo,
     * end command so that user can no longer undo,
     * or current command user is at.
     */
    public enum CommandType { PAST, CURR, FUTURE, END };

    private Integer index;
    private String commandString;
    private CommandType commandType;

    public CommandRecord(Integer index, String commandString, CommandType commandType) {
        this.index = index;
        this.commandString = commandString;
        this.commandType = commandType;
    }

    public CommandRecord(CommandType commandType) throws AlfredModelHistoryException {
        if (commandType.equals(CommandType.END)) {
            this.commandString = "*: Initialised State. Cannot undo.";
            this.index = null;
            this.commandType = CommandType.END;
        } else {
            throw new AlfredModelHistoryException("Previous commands cannot be properly initialised");
        }
    }


    public String getCommandString() {
        return this.commandString;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public Integer getIndex() {
        return this.index;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EntityCard)) {
            return false;
        }

        // state check(if two EntityCard are equal)
        CommandRecord commandRecord = (CommandRecord) other;
        return index.equals(commandRecord.getIndex())
                && commandString.equals(commandRecord.getCommandString())
                && commandType.equals(commandRecord.getCommandType());
    }


}
