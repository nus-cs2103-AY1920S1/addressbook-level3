package seedu.address.model.wordbankstats;

import static java.util.Objects.requireNonNull;

/**
 * Represents the statistics of a card.
 */
public class CardStatistics {

    private String cardId;
    private int numShown;
    private int numCorrect;

    public CardStatistics(String cardId, int numShown, int numCorrect) {
        if (numShown < 0 || numCorrect < 0) {
            throw new IllegalArgumentException("Integer arguments should be positive.");
        }
        if (numCorrect > numShown) {
            throw new IllegalArgumentException("Number of correct guesses should be at most the number"
                + "the card has been shown.");
        }
        requireNonNull(cardId);
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
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof CardStatistics)) {
            return false;
        }

        CardStatistics other = (CardStatistics) obj;
        return getCardId().equals(other.getCardId())
                && getNumShown() == other.getNumShown()
                && getNumCorrect() == other.getNumCorrect();
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
