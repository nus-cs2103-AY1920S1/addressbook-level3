package seedu.address.statistics;

public class CardStatistics {

    private String cardId;
    private int numShown;
    private int numCorrect;

    public CardStatistics(String cardId, int numShown, int numCorrect) {
        this.cardId = cardId;
        this.numShown = numShown;
        this.numCorrect = numCorrect;
    }

    public String getCardId() {
        return cardId;
    }

    public int getNumShown() {
        return numShown;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public void addWrong() {
        ++numShown;
    }

    public void addCorrect() {
        ++numCorrect;
        ++numShown;
    }
}
