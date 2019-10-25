package seedu.address.logic.commands;

/**
 * Represents the result of a command execution.
 */
public interface CommandResult {

    /**
     *
     * @return
     */
    String getFeedbackToUser();

    boolean isGlobalCommandResult();
    boolean isFlashcardCommandResult();
    boolean isCheatSheetCommandResult();
    boolean isNoteCommandResult();

    /**
     *
     * @param other
     * @return
     */
    @Override
    boolean equals(Object other);

    /**
     *
     * @return
     */
    @Override
    int hashCode();

}
