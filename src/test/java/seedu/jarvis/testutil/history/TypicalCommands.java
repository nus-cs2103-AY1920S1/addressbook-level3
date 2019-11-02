package seedu.jarvis.testutil.history;

import java.util.Arrays;
import java.util.List;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.model.history.HistoryManager;

/**
 * A utility class containing {@code Command} objects to be used in tests.
 */
public class TypicalCommands {
    // executed commands to add typical persons to the application.

    // inversely executed command to clear all typical persons to the application.

    private TypicalCommands() {} // prevents instantiation

    /**
     * Gets a typical {@code HistoryManager} which remembers the executed commands that populates the application
     * with typical persons and remembers a inversely executed command that clears all typical persons from the
     * application.
     *
     * @return Typical {@code HistoryManager}.
     */
    public static HistoryManager getTypicalHistoryManager() {
        HistoryManager historyManager = new HistoryManager();
        getTypicalExecutedCommands().forEach(historyManager::rememberExecutedCommand);
        getTypicalInverselyExecutedCommands().forEach(historyManager::rememberInverselyExecutedCommand);
        return historyManager;
    }

    /**
     * Gets a {@code List} of {@code Command} objects that will add the typical persons to the application.
     *
     * @return {@code List} of {@code Command} objects.
     */
    public static List<Command> getTypicalExecutedCommands() {
        return Arrays.asList();
    }

    /**
     * Gets a {@code List} of {@code Command} objects that will clear all typical persons from the application.
     *
     * @return {@code List} of {@code Command} objects.
     */
    public static List<Command> getTypicalInverselyExecutedCommands() {
        return Arrays.asList();
    }

}
