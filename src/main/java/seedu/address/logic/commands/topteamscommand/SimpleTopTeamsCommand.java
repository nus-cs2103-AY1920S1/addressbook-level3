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
 * Shows the Top K teams currently in the leader board, where K is
 * an integer the user inputs.
 */
public class SimpleTopTeamsCommand extends TopTeamsCommand {

    public static final String MESSAGE_SUCCESS = "Showing Current Top %1$s";
    private final Logger logger = LogsCenter.getLogger(getClass());

    public SimpleTopTeamsCommand(int k, ArrayList<Comparator<Team>> comparators, SubjectName subject) {
        super(k, comparators, subject);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert comparators != null : "The comparators list should not be null";
        checkNoTeams(model);
        model.setTopK(this.numberOfTeams, comparators, subject);
        logger.info("Showing Top " + this.numberOfTeams + " Teams.");
        return new CommandResult(String.format(MESSAGE_SUCCESS, numberOfTeams), CommandType.L);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SimpleTopTeamsCommand // instanceof handles nulls
                && numberOfTeams == ((SimpleTopTeamsCommand) other).numberOfTeams)
                && comparators.equals(((SimpleTopTeamsCommand) other).comparators);
    }
}
