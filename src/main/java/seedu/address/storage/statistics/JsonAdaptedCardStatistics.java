package seedu.address.storage.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.statistics.CardStatistics;

public class JsonAdaptedCardStatistics {
    private final String cardId;
    private final int numShown;
    private final int numCorrect;

    public JsonAdaptedCardStatistics(@JsonProperty("id") String cardId,
                                     @JsonProperty("numShown") int numShown,
                                     @JsonProperty("numCorrect") int numCorrect) {
        this.cardId = cardId;
        this.numShown = numShown;
        this.numCorrect = numCorrect;
    }

    /**
     * Converts a given {@code CardStatistics} into this class for Jackson use.
     */
    public JsonAdaptedCardStatistics(CardStatistics source) {
        this.numShown = source.getNumShown();
        this.numCorrect = source.getNumCorrect();
        this.cardId = source.getCardId();
    }

    public CardStatistics toModelType() {
        return new CardStatistics(cardId, numShown, numCorrect);
    }
}