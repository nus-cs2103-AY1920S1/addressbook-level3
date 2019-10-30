package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.LinkedHashSet;

import seedu.moneygowhere.logic.sorting.SortField;
import seedu.moneygowhere.logic.sorting.SpendingComparator;
import seedu.moneygowhere.model.Model;

/**
 * Sorts the current Spending entries.
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

    private final LinkedHashSet<SortField> fields;

    public SortCommand(LinkedHashSet<SortField> fields) {
        this.fields = fields;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        SpendingComparator spendingComparator = new SpendingComparator(fields);
        model.updateSortedSpendingList(spendingComparator);
        return new CommandResult(MESSAGE_SUCCESS + "\n" + spendingComparator);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && (fields.equals(((SortCommand) other).fields)));
    }
}
