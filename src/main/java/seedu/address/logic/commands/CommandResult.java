package seedu.address.logic.commands;

/**
 * Represents the result of a command execution.
 */
public abstract class CommandResult {

    /**
     *
     * @return
     */
    public abstract String getFeedbackToUser();

    public boolean isGlobalCommandResult() {
        return false;
    }

    public boolean isFlashcardCommandResult() {
        return false;
    }

    public boolean isCheatSheetCommandResult() {
        return false;
    }

    public boolean isNoteCommandResult() {
        return false;
    }

    /**
     *
     * @param other
     * @return
     */
    @Override
    public abstract boolean equals(Object other);

    /**
     *
     * @return
     */
    @Override
    public abstract int hashCode();

}
