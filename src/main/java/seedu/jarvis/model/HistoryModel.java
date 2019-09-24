package seedu.jarvis.model;

import java.util.List;

/**
 * The API of the HistoryModel component.
 */
public interface HistoryModel {

    /**
     * Gets a list of messages from past commands that have been executed.
     * @return A list of messages from past commands that have been executed.
     */
    List<String> getPastDoneMessages();

    /**
     * Gets a list of messages from commands that have been executed inversely.
     * @return A list of messages from commands that have been inversely executed.
     */
    List<String> getPastUndoneMessages();

    /**
     * Inversely execute the latest command that was done.
     */
    void undo();

    /**
     * Execute the command that was most recently undone.
     */
    void redo();
}
