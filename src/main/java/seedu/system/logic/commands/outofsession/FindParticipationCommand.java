package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;

import seedu.system.commons.core.Messages;
import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.model.Model;
import seedu.system.model.participation.PersonOrCompetitionNameContainsKeywordsPredicate;

/**
 * Finds and lists all participationss in the system whose competition name or person name contains any of the
 * argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindParticipationCommand extends Command {

    public static final String COMMAND_WORD = "findParticipation";
    public static final CommandType COMMAND_TYPE = CommandType.PARTICIPATION;

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Finds all participations whose names contain any of the specified keywords (case-insensitive)\n"
        + "in its competition or person name and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " NUS 2019 OPEN";

    private final PersonOrCompetitionNameContainsKeywordsPredicate predicate;

    public FindParticipationCommand(PersonOrCompetitionNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws InSessionCommandException {
        requireNonNull(model);

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

        model.updateFilteredParticipationList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_PARTICIPATIONS_LISTED_OVERVIEW, model.getFilteredParticipationList().size()),
            COMMAND_TYPE
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindParticipationCommand // instanceof handles nulls
            && predicate.equals(((FindParticipationCommand) other).predicate)); // state check
    }
}

