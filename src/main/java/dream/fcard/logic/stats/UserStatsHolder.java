//@@author nattanyz
package dream.fcard.logic.stats;

/**
 * A class to store and retrieve the one and only instance of UserStats allowed in the program.
 */
public class UserStatsHolder {
    private static UserStats userStats;

    public static UserStats getUserStats() {
        if (userStats == null) {
            userStats = new UserStats();
        }
        return userStats;
    }
}
