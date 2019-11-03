//@@author nattanyz
package dream.fcard.logic.stats;

import java.io.Serializable;

/**
 * Represents the user's statistics.
 */
public class UserStats extends Stats implements Serializable {

    /** Constructs a new instance of UserStats with no stored data. */
    public UserStats() {
        super();
        System.out.println("New UserStats object created");
        this.sessionList = new SessionList();
        System.out.println("New SessionList for logins created");
    }

    ///** Returns the UserStats object pertaining to this user. */
    //public static UserStats getUserStats() {
    //    if (userStats == null) {
    //        System.out.println("Creating a new UserStats object...");
    //        userStats = new UserStats();
    //    }
    //    return userStats;
    //}

    ///** Sets userStats to the pre-defined UserStats object. */
    //public void setUserStats(UserStats stats) {
    //    userStats = stats;
    //}

    // todo: calculate number of sessions in past week, past month etc. should this generate a list?
    // todo: possibly compare past week to previous week etc.
}
