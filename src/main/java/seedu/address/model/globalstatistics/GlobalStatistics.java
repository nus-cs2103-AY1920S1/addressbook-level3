package seedu.address.model.globalstatistics;

public class GlobalStatistics {

    private int numPlayed;
    private WeeklyPlayed weeklyPlayed;

    public GlobalStatistics(int numPlayed, WeeklyPlayed weeklyPlayed) {
        this.numPlayed = numPlayed;
        this.weeklyPlayed = weeklyPlayed;
    }

    public GlobalStatistics() {
        this.numPlayed = 0;
        this.weeklyPlayed = new WeeklyPlayed();
    }

    public void addPlay() {
        ++numPlayed;
        weeklyPlayed.incrementPlay();
    }

    public int getNumPlayed() {
        return numPlayed;
    }

    public WeeklyPlayed getWeeklyPlayed() {
        return weeklyPlayed;
    }

    @Override
    public String toString() {
        return numPlayed + " times played";
    }
}