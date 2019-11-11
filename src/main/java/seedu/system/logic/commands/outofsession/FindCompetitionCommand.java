package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;

import seedu.system.commons.core.Messages;
import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.model.Model;
import seedu.system.model.competition.CompetitionContainsKeywordsPredicate;

/**
 * Finds and lists all competitions in the system whose name or dates contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCompetitionCommand extends Command {

    public static final String COMMAND_WORD = "findCompetition";
    public static final CommandType COMMAND_TYPE = CommandType.PARTICIPATION;

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Finds all competitions whose names contain any of the specified keywords (case-insensitive)\n"
        + "in its competition or person name and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " NUS 2019 OPEN";

    private final CompetitionContainsKeywordsPredicate predicate;

    public FindCompetitionCommand(CompetitionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws InSessionCommandException {
        requireNonNull(model);

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

        model.updateFilteredCompetitionList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_COMPETITIONS_LISTED_OVERVIEW, model.getFilteredCompetitionList().size()),
            COMMAND_TYPE
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindCompetitionCommand // instanceof handles nulls
            && predicate.equals(((FindCompetitionCommand) other).predicate)); // state check
    }
}
