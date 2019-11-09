package dream.fcard.logic.stats;

import dream.fcard.util.stats.SessionListUtil;

/**
 * A list of the user's test sessions involving a particular deck.
 */
public class TestSessionList extends SessionList {

    /** Returns the average score of all TestSessions contained in this TestSessionList, as a String. */
    public String getAverageScore() {
        return SessionListUtil.getAverageScore(this);
    }
}
