package seedu.system.logic.commands.comparators;

import java.util.Comparator;

import seedu.system.logic.commands.RankMethod;
import seedu.system.model.participation.Participation;

/**
 * Compares two participations for ordering, where the participation with the higher maximum score for a particular
 * lift or overall score goes earlier in the ordering. The method of calculating the score depends on the rank method
 * inputted.
 */
public class ParticipationRankMethodComparator implements Comparator<Participation> {

    private final RankMethod rankMethod;

    public ParticipationRankMethodComparator(RankMethod rankMethod) {
        this.rankMethod = rankMethod;
    }

    @Override
    public int compare(Participation p1, Participation p2) {
        return p2.getScore(rankMethod) - p1.getScore(rankMethod);
    }
}

