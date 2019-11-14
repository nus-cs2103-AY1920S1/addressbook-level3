package seedu.address.commons;

import java.util.Comparator;

import seedu.address.model.entity.Team;

/**
 * Contains common comparators that will come in use within Alfred, for example to
 * rank teams by score.
 */
public class Comparators {

    // Reference names for available comparators
    public static final String MORE_PARTICIPANTS = "moreParticipants";
    public static final String LESS_PARTICIPANTS = "lessParticipants";
    public static final String HIGHER_ID = "higherId";
    public static final String LOWER_ID = "lowerId";

    /**
     * Creates a new comparator used to sort teams by their score
     * in descending order, mainly for the leader board.
     *
     * @return a new Comparator for Teams.
     */
    public static Comparator<Team> rankByScore() {
        return new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                int scoreTeam1 = o1.getScore().getScore();
                int scoreTeam2 = o2.getScore().getScore();
                return scoreTeam2 - scoreTeam1;
            }
        };
    }

    /**
     * Creates a new comparator used to sort teams by the number of participants
     * they have in ascending order, for use to break ties.
     *
     * @return a new Comparator for Teams.
     */
    public static Comparator<Team> rankByParticipantsDescending() {
        return new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                int participantsTeam1 = o1.getParticipants().size();
                int participantsTeam2 = o2.getParticipants().size();
                return participantsTeam2 - participantsTeam1;
            }
        };
    }

    /**
     * Creates a new comparator used to sort teams by their score
     * in ascending order, mainly for the loser board.
     *
     * @return a new Comparator for Teams.
     */
    public static Comparator<Team> rankByParticipantsAscending() {
        return rankByParticipantsDescending().reversed();
    }

    /**
     * Creates a new comparator used to sort teams by their id
     * in ascending order, for use to break ties.
     *
     * @return a new Comparator for Teams.
     */
    public static Comparator<Team> rankByIdDescending() {
        return new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                int idTeam1 = o1.getId().getNumber();
                int idTeam2 = o2.getId().getNumber();
                return idTeam2 - idTeam1;
            }
        };
    }

    /**
     * Creates a new comparator used to sort teams by their id
     * in ascending order, for use to break ties.
     *
     * @return a new Comparator for Teams.
     */
    public static Comparator<Team> rankByIdAscending() {
        return rankByIdDescending().reversed();
    }
}
