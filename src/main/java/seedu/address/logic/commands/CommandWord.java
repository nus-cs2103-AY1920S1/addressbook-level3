package seedu.address.logic.commands;

public class CommandWord {

    private String commandWord;

    public CommandWord(String commandWord) {
        this.commandWord = commandWord;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CommandWord)) {
            return false;
        }

        CommandWord other = (CommandWord) obj;
        return other.commandWord.toLowerCase().equals(commandWord.toLowerCase());
    }
}
