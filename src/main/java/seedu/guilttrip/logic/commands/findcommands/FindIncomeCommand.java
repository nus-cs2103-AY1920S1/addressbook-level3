package seedu.guilttrip.logic.commands.findcommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.function.Predicate;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Entry;

/**
 * Finds and lists all incomes in guilttrip book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindIncomeCommand extends Command {

    public static final String COMMAND_WORD = "findIncome";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Finds all income entries";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC + " which contains "
            + " the keywords that the user requests to be filtered by contain any of and displays them as a list with "
            + "index numbers. There must at least be one property that you are searching by. \n"
            + "[" + PREFIX_CATEGORY + "KEYWORDS] "
            + "[" + PREFIX_DESC + "KEYWORDS] "
            + "[" + PREFIX_DATE + "TIME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_AMOUNT + "5.60";


    public static final String EMPTY_PROPETIES = "Propeties cannot be empty.";

    private final List<Predicate<Entry>> predicate;

    public FindIncomeCommand(List<Predicate<Entry>> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Predicate<Entry> newPredicate = this.predicate.stream().reduce(t -> true, (tbefore, tafter) ->
                tbefore.and(tafter));
        model.updateFilteredIncomes(newPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ENTRIES_LISTED_OVERVIEW, model.getFilteredIncomes().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindIncomeCommand // instanceof handles nulls
                && predicate.equals(((FindIncomeCommand) other).predicate)); // state check
    }
}
