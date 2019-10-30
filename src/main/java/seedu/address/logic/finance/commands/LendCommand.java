package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TO;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TRANSACTION_METHOD;

import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * Adds an entry of lend (instance of lending) to the finance log.
 */
public class LendCommand extends Command {

    public static final String COMMAND_WORD = "lend";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entry of lending to the finance log. \n"
            + "Parameters: "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DAY + "DATE_LENT "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_TRANSACTION_METHOD + "TRANSACTION_METHOD "
            + PREFIX_TO + "PERSON_LENT_TO "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "1 "
            + PREFIX_DAY + "08-08-2019 "
            + PREFIX_DESCRIPTION + "HL Choco milk "
            + PREFIX_TRANSACTION_METHOD + "Cash "
            + PREFIX_TO + "Brother ";

    public static final String MESSAGE_SUCCESS = "New entry added: %1$s \n";

    private final LogEntry toAdd;

    /**
     * Creates an AddCommand to add the specified {@code LogEntry}
     */
    public LendCommand(LogEntry logEntry) {
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
                || (other instanceof LendCommand // instanceof handles nulls
                && toAdd.equals(((LendCommand) other).toAdd));
    }
}
