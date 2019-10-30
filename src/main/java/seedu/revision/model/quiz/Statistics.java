package seedu.revision.model.quiz;

public class Statistics {

    private double totalScore;

    public Statistics(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

}
