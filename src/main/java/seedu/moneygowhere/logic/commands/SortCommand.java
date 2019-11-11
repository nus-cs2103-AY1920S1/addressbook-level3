package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.logic.sorting.SortField;
import seedu.moneygowhere.logic.sorting.SpendingComparator;
import seedu.moneygowhere.model.Model;

//@@author Nanosync
/**
 * Sorts the current displayed spending entries in the list.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all spending entries";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all spending by fields indicated (order-sensitive).\n"
            + "Parameters: [PREFIX/ASC or DESC]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "ASC "
            + PREFIX_COST + "DESC ";

    public static final String MESSAGE_SORT_DUPLICATE_FIELD = "Repeated sorting field detected.\n"
            + "You can only sort a field once.";

    private final LinkedHashSet<SortField> fields;

    private final Logger logger = LogsCenter.getLogger(SortCommand.class);

    public SortCommand(LinkedHashSet<SortField> fields) {
        this.fields = fields;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        SpendingComparator spendingComparator = new SpendingComparator(fields);
        model.updateSortedSpendingList(spendingComparator);
        logger.log(Level.INFO, spendingComparator.toString());

        return new CommandResult(MESSAGE_SUCCESS + "\n" + spendingComparator);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && (fields.equals(((SortCommand) other).fields)));
    }
}
