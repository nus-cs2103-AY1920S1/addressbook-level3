package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Comparator;

import seedu.address.model.entity.Team;

/**
 * Displays the top specified number of teams on the UI.
 */
public abstract class TopTeamsCommand extends Command {

    public static final String COMMAND_WORD = "getTop";
    public static final String INVALID_VALUE_WARNING = "The value of K inputted is not a valid positive integer.";

    protected int numberOfTeams;
    protected ArrayList<Comparator<Team>> comparators;

    public TopTeamsCommand(int k, ArrayList<Comparator<Team>> comparators) {
        this.numberOfTeams = k;
        this.comparators = comparators;
    }

}
