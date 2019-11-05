package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_OPERATOR;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_SELF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.incident.Incident;
import seedu.address.model.incident.NameKeywordsPredicate;
import seedu.address.model.person.Name;


/**
 * Finds and lists all incidents in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindIncidentsCommand extends Command {

    public static final String COMMAND_WORD = "find-i";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all incidents for which ID matches exactly, "
            + "or description contains any of the specified description keywords, "
            + "or operator name matches any of the specified operator keywords or logged-in operator's name \n"
            + "Parameters: \n"
            + SEARCH_PREFIX_ID + "<ID> \n"
            + SEARCH_PREFIX_OPERATOR + "<OPERATOR KEYWORD [MORE_KEYWORDS]> \n"
            + PREFIX_DESCRIPTION + "<DESCRIPTION KEYWORD [MORE_KEYWORDS]...> \n"
            + SEARCH_PREFIX_SELF + "\n"
            + "Example: " + COMMAND_WORD + " "
            + SEARCH_PREFIX_OPERATOR + "alex yeoh "
            + PREFIX_DESCRIPTION + "6 "
            + SEARCH_PREFIX_SELF;

    private Predicate<Incident> predicate;
    private boolean isSelfSearch = false;
    private List<Predicate<Incident>> predicateArr = new ArrayList<>();

    public FindIncidentsCommand(List<Predicate<Incident>> predicateArr) {
        this.predicateArr = predicateArr;
        combinePredicates();
    }

    public FindIncidentsCommand(List<Predicate<Incident>> predicateArr, Prefix prefix) {
        this.predicateArr = predicateArr;
        combinePredicates();
        if (prefix == SEARCH_PREFIX_SELF) {
            this.isSelfSearch = true;
        }
    }

    /**
     * Combines predicates if there are multiple predicates
     */
    private void combinePredicates() {
        if (predicateArr.size() != 1) {
            for (int i = 0; i < predicateArr.size() - 1; i++) {
                this.predicate = predicateArr.get(i).and(predicateArr.get(i + 1));
            }
        } else {
            this.predicate = predicateArr.get(0);
        }
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (isSelfSearch) {
            // TODO: quick fix to allow searching using first name. when full name is allowed in search, change this
            Name operatorName = model.getLoggedInPerson().getName();
            String[] opNameKeywords = operatorName.fullName.split("\\s+");
            if (predicate != null) {
                predicate = predicate.and(new NameKeywordsPredicate(Arrays.asList(opNameKeywords), true));
            } else {
                predicate = new NameKeywordsPredicate(Arrays.asList(opNameKeywords), true);
            }
        }

        model.updateFilteredIncidentList(predicate);

        // prints grammatically correct messages to user
        if (model.getFilteredIncidentList().size() == 0) {
            return new CommandResult(Messages.MESSAGE_NO_INCIDENTS_FOUND);
        } else if (model.getFilteredIncidentList().size() == 1) {
            return new CommandResult(Messages.MESSAGE_SINGLE_INCIDENT_LISTED);
        } else {
            return new CommandResult(
                    String.format(Messages.MESSAGE_INCIDENTS_LISTED_OVERVIEW, model.getFilteredIncidentList().size()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindIncidentsCommand // instanceof handles nulls
                && predicate.equals(((FindIncidentsCommand) other).predicate)); // state check
    }
}
