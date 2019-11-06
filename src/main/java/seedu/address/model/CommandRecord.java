package seedu.address.model;

import seedu.address.ui.EntityCard;

/**
 * Represents a record of a previously executed command in ModelHistory.
 */
public class CommandRecord {
    /**
     * Represents the CommandType of a CommandRecord. Indicates whether the
     * CommandRecord represents a Command that can be undone (UNDO), redone (REDO),
     * is the current command (CURR), or is a sentinel for the endpoints (END). The
     * user will not be able to undo/redo beyond the endpoints.
     */
    public enum CommandType {
        UNDO, CURR, REDO, END
    };

    private Integer index;
    private String commandString;
    private CommandType commandType;

    public CommandRecord(Integer index, String commandString, CommandType commandType) {
        this.index = index;
        this.commandString = commandString;
        this.commandType = commandType;
    }

    public static CommandRecord getUndoEndPoint() {
        return new CommandRecord(null, "UNDO DELIMITER: Cannot Undo Beyond This Point", CommandType.END);
    }

    public static CommandRecord getRedoEndPoint() {
        return new CommandRecord(null, "REDO DELIMITER: Cannot Redo Beyond This Point", CommandType.END);
    }

    public static CommandRecord getCurrentStatePoint() {
        return new CommandRecord(null, "CURRENT STATE: You are here!", CommandType.CURR);
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
        return index.equals(commandRecord.getIndex()) && commandString.equals(commandRecord.getCommandString())
                && commandType.equals(commandRecord.getCommandType());
    }
}
