package dream.fcard.logic.stats;

import java.util.ArrayList;

public class Stats {

    private SessionList loginSessions;

    public static Stats parseStats(String fileText) {
        return new Stats();
    }

    public int numberOfLoginSessions() {
        return this.loginSessions.numberOfSessions();
    }
}
