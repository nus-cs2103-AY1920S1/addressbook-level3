package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Filters the transactions in the bank account.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = FilterCommand.COMMAND_WORD + ": Filters the transaction "
            + "in the bank account. "
            + "Parameter: "
            + "TAG\n"
            + "Example: " + FilterCommand.COMMAND_WORD + " "
            + "food";

    public static final String MESSAGE_SUCCESS = "Bank Account has been filtered!";

    public final Tag toFilter;

    public FilterCommand(Tag tag) {
        requireNonNull(tag);
        this.toFilter = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toFilter));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof FilterCommand) {
            FilterCommand filterCommand = (FilterCommand) obj;
            return toFilter.equals(filterCommand.toFilter);
        } else {
            return false;
        }
    }
}
