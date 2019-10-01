package seedu.address.transaction.logic;

import seedu.address.transaction.commands.CommandResult;

public interface Logic {

    CommandResult execute(String commandText) throws Exception;
}
