//@@author SakuraBlossom
package seedu.address.logic.commands.patients;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.OmniPanelTab.PATIENTS_TAB;
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
public class ListPatientCommand extends NonActionableCommand {

    public static final String COMMAND_WORD = "patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " alice";

    private final Predicate<Person> predicate;

    public ListPatientCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    public ListPatientCommand(String keyword) {
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
        model.setTabListing(PATIENTS_TAB);
        model.updateFilteredPatientList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        model.getFilteredPatientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListPatientCommand // instanceof handles nulls
                && predicate.equals(((ListPatientCommand) other).predicate)); // state check
    }
}
