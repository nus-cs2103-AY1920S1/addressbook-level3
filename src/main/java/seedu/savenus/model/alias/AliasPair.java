package seedu.savenus.model.alias;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

/**
 * Represents a Pair containing the command word and the alias word assigned .
 */
public class AliasPair {

    public static final String MESSAGE_CONSTRAINTS = "The command word you have entered is invalid! \n"
            + "Please refer to the list of commands for valid command words";

    private String commandWord;
    private String aliasWord;

    /**
     * Creates a new AliasPair containing the command and alias word.
     * @param commandWord the command to have the alias assigned to.
     * @param aliasWord the word representing the alias.
     */
    public AliasPair(String commandWord, String aliasWord) {
        requireNonNull(commandWord);
        requireNonNull(aliasWord);
        checkArgument(isValidCommandWord(commandWord), MESSAGE_CONSTRAINTS);
        this.commandWord = commandWord;
        this.aliasWord = aliasWord;
    }

    public String getCommandWord() {
        return this.commandWord;
    }

    public String getAliasWord() {
        return this.aliasWord;
    }

    public void setAliasWord(String aliasWord) {
        this.aliasWord = aliasWord;
    }

    /**
     * Checks if the command word is valid or not,
     * @param commandWord the command word.
     * @return true if the command word is not empty. False if otherwise.
     */
    public boolean isValidCommandWord(String commandWord) {
        if (commandWord.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof AliasPair) {
            AliasPair other = (AliasPair) object;
            return this.getCommandWord().equals(other.getCommandWord())
                    && this.getAliasWord().equals(other.getAliasWord());
        } else {
            return false;
        }
    }
}
