package seedu.address.logic.commands.topteamscommand;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

/**
 * Displays the top specified number of teams on the UI, breaking the tie
 * based on random selection.
 */
public class TopTeamsRandomCommand extends TopTeamsCommand {
    public static final String MESSAGE_SUCCESS = "Showing Current Top %1$s with Random Winners";
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates a new {@code TopTeamsRandomCommand} object.
     *
     * @param k the number of teams to be fetched.
     * @param comparators the comparators to be used for tiebreaks.
     * @param subject the subject name to be used for filtering the top teams by. Allows input to be null
     *                if the user hasn't specified a subject to filter by.
     */
    public TopTeamsRandomCommand(int k, ArrayList<Comparator<Team>> comparators, SubjectName subject) {
        super(k, comparators, subject);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert comparators != null : "The comparators list is null";
        checkNoTeams(model);
        model.setTopKRandom(this.numberOfTeams, comparators, subject);
        logger.info("Showing Top " + this.numberOfTeams + " Teams.");
        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(String.format(MESSAGE_SUCCESS, numberOfTeams), CommandType.L);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TopTeamsRandomCommand // instanceof handles nulls
                && numberOfTeams == ((TopTeamsRandomCommand) other).numberOfTeams)
                && comparators.equals(((TopTeamsRandomCommand) other).comparators);
    }
}
