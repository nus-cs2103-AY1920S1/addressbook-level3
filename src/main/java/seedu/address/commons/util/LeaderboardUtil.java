package seedu.address.commons.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.Comparators;
import seedu.address.model.entity.Team;

/**
 * Utility class for deriving the leaderboard from the sorted list of teams.
 */
public class LeaderboardUtil {

    public static final String RANDOM_KEYWORD = "random";
    public static final String RANDOM_USAGE_WARNING = "Random must be the last specified tie-breaking method.";
    public static final String INVALID_TIE_BREAK = "The tie-break method specified does not exist or is not "
            + "supported by Alfred: ";

    /**
     * Gets the top {@code k} teams in the hackathon based on the current standing
     * as per the input list {@code currentStanding}. This method does not discriminate between
     * teams with same score and places them in the same position.
     *
     * @return an ObservableList object containing the current top K teams.
     */
    public static ObservableList<Team> topKWithTie(ObservableList<Team> currentStanding, int k,
                                                   ArrayList<Comparator<Team>> comparators) {
        ObservableList<Team> teams = FXCollections.observableArrayList();
        Team currentTeam = currentStanding.get(0);
        int currentTeamIndex = 0;
        int distinctTeams = 1; // The first team is a distinct team always so the value starts at 1.

        while (distinctTeams <= k && currentTeamIndex < currentStanding.size()) {
            Team team = currentStanding.get(currentTeamIndex);
            if (allMatch(currentTeam, team, comparators)) {
                teams.add(team);
            } else {
                teams.add(team);
                currentTeam = team;
                distinctTeams++;
            }
            currentTeamIndex++;
        }
        return returnAppropriateSizedList(k, distinctTeams, teams);
    }

    /**
     * Returns a {@code ObservableList} object of Teams of the appropriate length
     * depending on the value {@code k} inputted by the user and the number of {@code distinct}
     * teams appearing in the list.
     *
     * @return an ObservableList object.
     */
    private static ObservableList<Team> returnAppropriateSizedList(int k, int distinctTeams,
                                                                   ObservableList<Team> teams) {
        if (distinctTeams > k) {
            teams.remove(teams.size() - 1); // The above algorithm adds one extra team at the end of the list.
            return teams;
        } else {
            return teams;
        }
    }

    /**
     * Gets the top {@code k} teams in the hackathon based on the current standing
     * as per the input list {@code currentStanding}. This method discriminates between teams
     * with the same score by placing teams with lower ID higher.
     *
     * @return an ObservableList object containing the current top K teams.
     */
    public static ObservableList<Team> topKAbsolute(ObservableList<Team> currentStanding, int k) {
        int listSize = currentStanding.size();
        currentStanding.remove(k, listSize);
        return currentStanding;
    }

    /**
     * Generates random {@code k} winners in order from the {@code currentStanding} to determine
     * the winners of the hackathon on a random basis.
     *
     * @return a new ObservableList object containing the randomly generated winners in order.
     */
    public static ObservableList<Team> randomWinnersGenerator(ObservableList<Team> currentStanding, int k,
                                                              ArrayList<Comparator<Team>> comparators) {
        ObservableList<Team> finalTeams = FXCollections.observableArrayList();
        ArrayList<Team> temp = new ArrayList<>();
        Team currentTeam = currentStanding.get(0);

        for (int i = 0; i < currentStanding.size(); i++) {
            Team team = currentStanding.get(i);
            if (allMatch(team, currentTeam, comparators)) {
                temp.add(team);
            } else {
                shuffleAndAdd(finalTeams, temp);
                currentTeam = team;
                temp.add(team);
            }
        }
        shuffleAndAdd(finalTeams, temp); // call one last time to ensure no teams leftover
        return topKAbsolute(finalTeams, k);
    }

    /**
     * Checks if {@code team1} and {@code team2} are equal based on all specified
     * {@code comparators}.
     *
     * @return a boolean value depending on whether the all conditions are met.
     */
    private static boolean allMatch(Team team1, Team team2, ArrayList<Comparator<Team>> comparators) {
        comparators.add(Comparators.rankByScore());
        return comparators.stream()
                .allMatch(comparator -> comparator.compare(team1, team2) == 0);
    }

    private static void shuffleAndAdd(ObservableList<Team> teamStandings, ArrayList<Team> teams) {
        Collections.shuffle(teams);
        teamStandings.addAll(teams);
        teams.clear();
    }
}
