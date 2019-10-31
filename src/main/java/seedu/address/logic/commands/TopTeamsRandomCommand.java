package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Team;

/**
 * Displays the top specified number of teams on the UI, breaking the tie
 * based on random selection.
 */
public class TopTeamsRandomCommand extends TopTeamsCommand {
    public static final String MESSAGE_SUCCESS = "Showing Current Top %1$s with Random Winners";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": shows the top K teams with the teams with equal"
            + " scores separated on a random basis, and where K is an integer value you type in. \n"
            + "Format: " + COMMAND_WORD + " K \n"
            + "For example: " + COMMAND_WORD + " 5";
    private final Logger logger = LogsCenter.getLogger(getClass());

    public TopTeamsRandomCommand(int k, ArrayList<Comparator<Team>> comparators) {
        super(k, comparators);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setTopKRandom(this.numberOfTeams, comparators);
        logger.info("Showing Top " + this.numberOfTeams + " Teams.");
        return new CommandResult(String.format(MESSAGE_SUCCESS, numberOfTeams), CommandType.K);
    }

}
