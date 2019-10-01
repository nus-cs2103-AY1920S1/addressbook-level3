package seedu.address.transaction.logic;

import seedu.address.transaction.commands.Command;

public interface Parser<T extends Command> {
    T parse(String userInput);
}
