package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.util.Date;
import seedu.address.model.Model;
import seedu.address.model.Projection;
import seedu.address.model.transaction.DateComparator;
import seedu.address.model.transaction.Transaction;

/**
 * Projects user's future balance based on income/outflow history
 */
public class ProjectCommand extends Command {
    public static final String COMMAND_WORD = "project";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Project future balance based on past income/outflow.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "12122103 09:00";

    public static final String MESSAGE_SUCCESS = "Projected balance: %1$s";
    public final Date date;

    public ProjectCommand(Date date) {
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Transaction> transactionHistory =
                model.getBankAccount().getTransactionHistory().sorted(new DateComparator());

        Projection projection = new Projection(transactionHistory, date, model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, projection.toString()));
    }
}
