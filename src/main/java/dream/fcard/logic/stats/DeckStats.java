package dream.fcard.logic.stats;

public class DeckStats extends Stats {

    /** Ends the current session, and sets its score. */
    public void endCurrentSession(String score) {
        this.currentSession.setScore(score);
        endCurrentSession();
    }
}
