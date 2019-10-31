package seedu.system.logic.commands.comparators;

import java.util.Comparator;

import seedu.system.model.participation.Participation;

/**
 * Compares two participations for sorting
 *
 */
public class OverallScoreComparator implements Comparator<Participation> {
    @Override
    public int compare(Participation p1, Participation p2) {
        return p2.getTotalScore() - p1.getTotalScore();
    }
}
