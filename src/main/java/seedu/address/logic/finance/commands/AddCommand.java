package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_TRANSACTION_METHOD;

import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.logEntry.LogEntry;


/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_DAY + "NAME "
            + PREFIX_ITEM + "PHONE "
            + PREFIX_CATEGORY + "EMAIL "
            + PREFIX_PLACE + "ADDRESS "
            + "[" + PREFIX_TRANSACTION_METHOD + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DAY + "John Doe "
            + PREFIX_ITEM + "98765432 "
            + PREFIX_CATEGORY + "johnd@example.com "
            + PREFIX_PLACE + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TRANSACTION_METHOD + "friends "
            + PREFIX_TRANSACTION_METHOD + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final LogEntry toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(LogEntry logEntry) {
        requireNonNull(logEntry);
        toAdd = logEntry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasLogEntry(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addLogEntry(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
