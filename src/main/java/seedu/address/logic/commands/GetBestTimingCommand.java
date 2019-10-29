package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.task.Task;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.Iterator;

/**
 * Adds a task to the address book.
 */
public class GetBestTimingCommand extends Command {

    public static final String COMMAND_WORD = "add-calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": GET BEST TIMING";
    public static final String MESSAGE_SUCCESS = "Getting best timing";

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public GetBestTimingCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.getBestTiming();

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this;
    }
}
