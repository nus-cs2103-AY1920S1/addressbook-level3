package seedu.address.model.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a command with its command word and the action which it executes.
 */
public class CommandObject {

    private CommandWord commandWord;
    private CommandAction commandAction;

    public CommandObject(CommandWord commandWord, CommandAction commandAction) {
        requireAllNonNull(commandWord, commandAction);
        this.commandWord = commandWord;
        this.commandAction = commandAction;
    }

    public CommandWord getCommandWord() {
        return commandWord.copy();
    }

    public void setCommandWord(CommandWord commandWord) {
        this.commandWord = commandWord;
    }

    public CommandAction getCommandAction() {
        return commandAction.copy();
    }

    public void setCommandAction(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    /**
     * Returns true if both commands have the same commandAction and commandWord.
     * This defines a weaker notion of equality between two CommandObjects.
     */
    public boolean isSameCommand(CommandObject otherCommand) {
        if (otherCommand == this) {
            return true;
        }

        return otherCommand != null
                && otherCommand.getCommandAction().equals(getCommandAction())
                && otherCommand.getCommandWord().equals(getCommandWord());
    }

    /**
     * Returns true if both commands have the same commandAction and commandWord.
     * This defines a stronger notion of equality between two CommandObjects.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CommandObject)) {
            return false;
        }

        CommandObject otherCommand = (CommandObject) other;
        return otherCommand.getCommandAction().equals(getCommandAction())
                && otherCommand.getCommandWord().equals(getCommandWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandAction, commandWord);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Word: ")
                .append(getCommandWord())
                .append(" Action: ")
                .append(getCommandAction());

        return builder.toString();
    }
}
