package seedu.jarvis.model;

import seedu.jarvis.logic.commands.Command;

/**
 * The API of the {@code HistoryModel} component.
 */
public interface HistoryModel {
    /**
     * Gets the {@code HistoryManager}.
     *
     * @return {@code HistoryManager} object.
     */
    HistoryManager getHistoryManager();

    /**
     * Replaces {@code HistoryManager} data with the data in {@code HistoryManager} given as argument.
     *
     * @param historyManager {@code HistoryManager} data to be used.
     */
    void setHistoryManager(HistoryManager historyManager);

    /**
     * Gets the number of available executed commands that can be undone.
     *
     * @return The number of available executed commands that can be undone.
     */
    int getAvailableNumberOfExecutedCommands();

    /**
     * Gets the number of available inversely executed commands that can be redone.
     *
     * @return The number of available inversely executed commands that can be redone.
     */
    int getAvailableNumberOfInverselyExecutedCommands();

    /**
     * Checks whether it is possible to roll back any commands.
     *
     * @return Whether it is possible to roll back any commands.
     */
    boolean canRollback();

    /**
     * Checks whether it is possible to commit any commands.
     *
     * @return Whether it is possible to commit any commands.
     */
    boolean canCommit();

    /**
     * Adds the latest executed command. {@code Command} to be added must be invertible.
     *
     * @param command {@code Command} to be added.
     */
    void rememberExecutedCommand(Command command);

    /**
     * Rolls back the changes made by the latest executed command by inversely executing the command.
     *
     * @return Whether the inverse execution of the latest executed command was successful.
     */
    boolean rollback();

    /**
     * Commits the changes made by the latest inversely executed command by executing the command.
     *
     * @return Whether the execution of the latest executed command was successful.
     */
    boolean commit();
}
