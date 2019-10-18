package seedu.address.statistics;

/**
 * Represents the statistics of a card.
 */
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

    /**
     * Add a wrong guess data point to this card stats.
     */
    public void addWrong() {
        ++numShown;
    }

    /**
     * Add a correct guess data point to this card stats.
     */
    public void addCorrect() {
        ++numCorrect;
        ++numShown;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("id:")
                .append(cardId)
                .append("\nnumShown:")
                .append(numShown)
                .append("\nnumCorrect:")
                .append(numCorrect)
                .toString();

    }
}
