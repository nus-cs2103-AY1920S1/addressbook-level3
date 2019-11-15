package dream.fcard.logic.exam;

/**
 * Result object that helps to keep track of score.
 */
public class Result {

    private final int maxScore;
    private int finalScore;

    public Result(int maxScore) {
        this.maxScore = maxScore;
        this.finalScore = 0;
    }

    public String getScore() {
        return finalScore + "/" + maxScore;
    }

    /**
     * Simulates marking a question and updates score if correct.
     * @param correct boolean on whether answer is correct or not.
     */
    public void mark(boolean correct) {
        if (correct) {
            this.finalScore++;
        }
    }
}
