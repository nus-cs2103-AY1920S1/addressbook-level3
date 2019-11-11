package seedu.moolah.logic.commands.expense;

import static java.util.Objects.requireNonNull;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.model.Model;
import seedu.moolah.model.general.DescriptionContainsKeywordsPredicate;
import seedu.moolah.ui.expense.ExpenseListPanel;

/**
 * Finds and lists all expenses in MooLah whose description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindExpenseCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "find" + CommandGroup.EXPENSE;
    public static final String COMMAND_DESCRIPTION = "Find expenses with keywords %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all expenses whose descriptions contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final DescriptionContainsKeywordsPredicate predicate;

    public FindExpenseCommand(DescriptionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, String.join(" ", predicate.getKeywords()));
    }

    @Override
    protected void validate(Model model) {
        // No validation necessary.
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExpenseList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EXPENSES_LISTED_OVERVIEW, model.getFilteredExpenseList().size()),
                ExpenseListPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindExpenseCommand // instanceof handles nulls
                && predicate.equals(((FindExpenseCommand) other).predicate)); // state check
    }
}
