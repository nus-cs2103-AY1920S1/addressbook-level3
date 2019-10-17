package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DESCRIPTION;

import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.logentry.LogEntry;


/**
 * Adds an entry of expenditure to the finance log.
 */
public class SpendCommand extends Command {

    public static final String COMMAND_WORD = "spend";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entry of expenditure to the finance log. "
            + "Parameters: "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DAY + "TRANSACTION_DATE "
            + PREFIX_DESCRIPTION + "DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "2.80 "
            + PREFIX_DAY + "15-10-2019 "
            + PREFIX_DESCRIPTION + "Yong Tau Foo";

    public static final String MESSAGE_SUCCESS = "New entry added: %1$s";

    private final LogEntry toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public SpendCommand(LogEntry logEntry) {
        requireNonNull(logEntry);
        toAdd = logEntry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addLogEntry(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SpendCommand // instanceof handles nulls
                && toAdd.equals(((SpendCommand) other).toAdd));
    }
}
