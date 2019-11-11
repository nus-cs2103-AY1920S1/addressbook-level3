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
 * Finds and lists all expenses in guiltTrip with fields matching any of the predicates.
 * Keyword matching is case insensitive.
 */
public class FindExpenseCommand extends Command {

    public static final String COMMAND_WORD = "findExpense";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Finds all expense entries ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC + "which contains "
            + " the keywords that the user requests to be filtered by contain any of and displays them as a list with "
            + "index numbers. There must at least be one property that you are searching by. \n"
            + "[" + PREFIX_CATEGORY + "CATEGORY NAME] "
            + "[" + PREFIX_DESC + "KEYWORDS] "
            + "[" + PREFIX_DATE + "TIME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_AMOUNT + "5.60";

    public static final String EMPTY_PROPETIES = "Propeties cannot be empty.";

    private final List<Predicate<Entry>> predicate;

    public FindExpenseCommand(List<Predicate<Entry>> predicate) {
        this.predicate = predicate;
    }

    /**
     * Filters the list of Expenses by the conjuction of the existing predicates.
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return CommandResult the CommandResult for guiltTrip to display to User.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Predicate<Entry> newPredicate = this.predicate.stream().reduce(t -> true, Predicate::and);
        model.updateFilteredExpenses(newPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ENTRIES_LISTED_OVERVIEW, model.getFilteredExpenses().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindExpenseCommand // instanceof handles nulls
                && predicate.equals(((FindExpenseCommand) other).predicate)); // state check
    }
}
