package dream.fcard.logic.stats;

public class Stats {

    private SessionList loginSessions;

    public static Stats parseStats(String fileText) {
        return new Stats();
    }

    public int numberOfLoginSessions() {
        return this.loginSessions.numberOfSessions();
    }

    // todo: calculate number of sessions in past week, past month etc. should this generate a list?
    // todo: possibly compare past week to previous week etc.
}
