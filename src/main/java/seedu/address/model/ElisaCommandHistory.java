package seedu.address.model;

import seedu.address.logic.commands.Command;

/**
 * interface for state history
 */

public interface ElisaCommandHistory {
    public void pushCommand(Command command);

    public Command popCommand();

    public Command peekCommand();

    public int size();
}
