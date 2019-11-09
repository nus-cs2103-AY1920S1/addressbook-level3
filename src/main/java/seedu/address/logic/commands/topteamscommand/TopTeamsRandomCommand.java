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

    public TopTeamsRandomCommand(int k, ArrayList<Comparator<Team>> comparators, SubjectName subject) {
        super(k, comparators, subject);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert comparators != null : "The comparators list should not be null";
        checkNoTeams(model);
        model.setTopKRandom(this.numberOfTeams, comparators, subject);
        logger.info("Showing Top " + this.numberOfTeams + " Teams.");
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
