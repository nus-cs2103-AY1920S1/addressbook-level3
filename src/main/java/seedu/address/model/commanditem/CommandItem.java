package seedu.address.model.commanditem;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a command object. All FinSec commands (including default commands) will be initialised with commanditem.
 */
public class CommandItem {

    private CommandWord commandWord;
    private CommandTask commandTask;

    public CommandItem(CommandWord commandWord, CommandTask commandTask) {
        requireAllNonNull(commandWord, commandTask);
        this.commandWord = commandWord;
        this.commandTask = commandTask;
    }

    public CommandWord getCommandWord() {
        return commandWord;
    }

    public void setCommandWord(CommandWord commandWord) {
        this.commandWord = commandWord;
    }

    public CommandTask getCommandTask() {
        return commandTask;
    }

    public void setCommandTask(CommandTask commandTask) {
        this.commandTask = commandTask;
    }

    /**
     * Returns true if both commands have the same commandAction and commandWord.
     * This defines a weaker notion of equality between two CommandItems.
     */
    public boolean isSameCommand(CommandItem otherCommand) {
        if (otherCommand == this) {
            return true;
        }

        return otherCommand != null
                && otherCommand.getCommandTask().equals(getCommandTask())
                && otherCommand.getCommandWord().equals(getCommandWord());
    }


    /**
     * Returns true if both commands have the same commandTask and commandWord.
     * This defines a stronger notion of equality between two CommandItems.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CommandItem)) {
            return false;
        }

        CommandItem otherCommand = (CommandItem) other;
        return otherCommand.getCommandTask().equals(getCommandTask())
                && otherCommand.getCommandWord().equals(getCommandWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandTask, commandWord);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Word: ")
                .append(getCommandWord())
                .append(" Task: ")
                .append(getCommandTask());

        return builder.toString();
    }
}
