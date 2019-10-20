package seedu.address.model.commands;

import seedu.address.model.commands.CommandAction;
import seedu.address.model.commands.CommandWord;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class CommandObject {

    private CommandWord commandWord;
    private CommandAction commandAction;

    public CommandObject(CommandWord commandWord, CommandAction commandAction) {
        requireAllNonNull(commandWord, commandAction);
        this.commandWord = commandWord;
        this.commandAction = commandAction;
    }

    public CommandWord getCommandWord() {
        return commandWord;
    }

    public void setCommandWord(CommandWord commandWord) {
        this.commandWord = commandWord;
    }

    public CommandAction getCommandAction() {
        return commandAction;
    }

    public void setCommandAction(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    /**
     * Returns true if both earnings of the same date and module have an identity field that is the same.
     * This defines a weaker notion of equality between two earnings.
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
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
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
        // use this method for custom fields hashing instead of implementing your own
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
