package seedu.address.inventory.logic;

import seedu.address.inventory.commands.Command;

public interface Parser<T extends Command> {
    T parse(String userInput);
}
