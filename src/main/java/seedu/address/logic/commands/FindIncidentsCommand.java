package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_OPERATOR;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.incident.DescriptionKeywordsPredicate;
import seedu.address.model.incident.IdKeywordsPredicate;
import seedu.address.model.incident.Incident;
import seedu.address.model.incident.NameKeywordsPredicate;


/**
 * Finds and lists all incidents in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindIncidentsCommand extends Command {

    public static final String COMMAND_WORD = "find-i";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all incidents for which IDs match exactly "
            + "or description contains any of the specified keywords (case-insensitive) and displays them as a list "
            + "with index numbers.\n"
            + "Parameters: "
            + SEARCH_PREFIX_ID + "<ID> OR "
            + SEARCH_PREFIX_OPERATOR + "<OPERATOR> OR "
            + SEARCH_PREFIX_DESCRIPTION + "<KEYWORD [MORE_KEYWORDS]...>\n"
            + "Example: " + COMMAND_WORD + " "
            + SEARCH_PREFIX_OPERATOR + "alex";

    private final Predicate<Incident> predicate;

    public FindIncidentsCommand(DescriptionKeywordsPredicate descriptionPredicate) {
        this.predicate = descriptionPredicate;
    }

    public FindIncidentsCommand(IdKeywordsPredicate idPredicate) {
        this.predicate = idPredicate;
    }

    public FindIncidentsCommand(NameKeywordsPredicate namePredicate) {
        this.predicate = namePredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredIncidentList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_INCIDENTS_LISTED_OVERVIEW, model.getFilteredIncidentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindIncidentsCommand // instanceof handles nulls
                && predicate.equals(((FindIncidentsCommand) other).predicate)); // state check
    }
}
