package seedu.address.cashier.logic;

import seedu.address.cashier.commands.Command;

public interface Parser<T extends Command> {
    T parse(String userInput);
}
