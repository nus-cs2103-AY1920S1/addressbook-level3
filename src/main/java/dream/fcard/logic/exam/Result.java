package dream.fcard.logic.exam;

public class Result {

    private final int maxScore;
    private int finalScore;

    public Result(int maxScore) {
        this.maxScore = maxScore;
        this.finalScore = 0;
    }

    public String getScore() {
        return "Final Score:" + finalScore + "/" + maxScore;
    }

    public void mark(boolean correct) {
        if (correct) {
            this.finalScore++;
        }
    }
}
