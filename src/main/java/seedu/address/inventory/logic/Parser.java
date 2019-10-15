package seedu.address.inventory.logic;

import seedu.address.inventory.commands.Command;

/**
 * Punlic interface for parsing Commands.
 * @param <T> Type of Command.
 */
public interface Parser<T extends Command> {
    T parse(String userInput);
}
