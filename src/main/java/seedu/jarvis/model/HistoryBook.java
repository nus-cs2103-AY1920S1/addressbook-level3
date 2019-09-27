package seedu.jarvis.model;

import seedu.jarvis.logic.commands.CommandDeque;

/**
 * Wraps all data at the history-book level, storing done and undone commands.
 */
public class HistoryBook {
    private int historyLimit;
    private CommandDeque doneCommands;
    private CommandDeque undoneCommands;

    public HistoryBook() {
        historyLimit = CommandDeque.DEFAULT_INITIAL_SIZE_LIMIT;
        doneCommands = new CommandDeque();
        undoneCommands = new CommandDeque();
    }
}
