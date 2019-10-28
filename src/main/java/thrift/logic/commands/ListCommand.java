package thrift.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;

import thrift.logic.parser.CliSyntax;
import thrift.model.Model;

/**
 * Lists all transactions in THRIFT to the user.
 */
public class ListCommand extends NonScrollingCommand {

    public static final String COMMAND_WORD = "list";

    public static final String HELP_MESSAGE = COMMAND_WORD
            + ": Lists all transactions or lists transactions filtered by month\n"
            + "Format: " + COMMAND_WORD + " [" + CliSyntax.PREFIX_MONTH + "MONTH] (must be of format MM/yyyy)\n"
            + "Possible usages of " + COMMAND_WORD + ": \n"
            + "To list all transactions: " + COMMAND_WORD + "\n"
            + "To list all transactions that were created in January 2019: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_MONTH + "01/2019";

    public static final String MESSAGE_SUCCESS = "Listed all transactions";

    public static final String MESSAGE_SUCCESS_MONTH_FILTER = "Listed transactions in specified month";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": lists all transactions or list transactions filtered by month.\n"
            + "Parameters: "
            + CliSyntax.PREFIX_MONTH + "MONTH (must be of format MM/yyyy)\n"
            //+ CliSyntax.PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_MONTH + "01/2019";

    private Calendar monthyear;

    public ListCommand(Calendar monthyear) {
        this.monthyear = monthyear;
    }

    public ListCommand() {
        this.monthyear = null;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (this.monthyear == null) {
            model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            model.setCurrentMonthYear(this.monthyear);
            model.updateFilteredTransactionListToCurrentMonth();
            return new CommandResult(MESSAGE_SUCCESS_MONTH_FILTER);
        }
    }
}
