//@@author nattanyz
package dream.fcard.logic.stats;

/**
 * A class to store and retrieve the instances of UserStats and DeckStats allowed in the program.
 */
public class StatsHolder {
    private static UserStats userStats;

    private static DeckStats deckStats;

    public static UserStats getUserStats() {
        if (userStats == null) {
            userStats = new UserStats();
        }
        return userStats;
    }

    public static DeckStats getDeckStats() {
        if (deckStats == null) {
            deckStats = new DeckStats();
        }
        return deckStats;
    }
}
