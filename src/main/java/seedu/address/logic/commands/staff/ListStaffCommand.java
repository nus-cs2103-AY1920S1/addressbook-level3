//@@author SakuraBlossom
package seedu.address.logic.commands.staff;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.OmniPanelTab.DOCTORS_TAB;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.NonActionableCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.ContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ListStaffCommand extends NonActionableCommand {

    public static final String COMMAND_WORD = "doctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all staff doctors whose names, ref ids or phone number contains "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " Joe";

    private final Predicate<Person> predicate;

    public ListStaffCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    public ListStaffCommand(String keyword) {
        String trimmedArgs = keyword.trim();
        if (trimmedArgs.isEmpty()) {
            this.predicate = PREDICATE_SHOW_ALL_PERSONS;
        } else {
            this.predicate = new ContainsKeywordsPredicate(trimmedArgs.toUpperCase());
        }
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTabListing(DOCTORS_TAB);
        model.updateFilteredStaffList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        model.getFilteredStaffList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListStaffCommand // instanceof handles nulls
                && predicate.equals(((ListStaffCommand) other).predicate)); // state check
    }
}
