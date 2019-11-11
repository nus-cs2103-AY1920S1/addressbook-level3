/*
@@author shihaoyap
 */

package seedu.address.logic.commands.employee;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.TagContainsKeywordsPredicate;
import seedu.address.ui.MainWindow;

/**
 * Finds and lists all persons in address book whose tag contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindByTagCommand extends Command {

    public static final String COMMAND_WORD = "find_em_tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tag contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " fun hardworking";


    private final TagContainsKeywordsPredicate predicate;

    public FindByTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isScheduleTab() || MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_MISSING_EMPLOYEE_LIST);
        }

        model.updateFilteredEmployeeList(predicate);

        int employeeListSize = model.getFilteredEmployeeList().size();

        String resultMessage = employeeListSize == 1
                ? Messages.MESSAGE_EMPLOYEE_LISTED_OVERVIEW
                : Messages.MESSAGE_EMPLOYEES_LISTED_OVERVIEW;

        return new CommandResult(
                String.format(resultMessage, employeeListSize), "Employee");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindByTagCommand // instanceof handles nulls
                && predicate.equals(((FindByTagCommand) other).predicate)); // state check
    }
}
