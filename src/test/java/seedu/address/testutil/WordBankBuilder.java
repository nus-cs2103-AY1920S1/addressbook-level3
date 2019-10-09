package seedu.address.testutil;

import seedu.address.model.card.Card;
import seedu.address.model.wordbank.WordBank;

/**
 * A utility class to help with building WordBank objects.
 * Example usage: <br>
 *     {@code WordBank wb = new WordBankBuilder().withCard(CARD).build();}
 */
public class WordBankBuilder {

    private WordBank wordBank;

    public WordBankBuilder() {
        wordBank = new WordBank();
    }

    public WordBankBuilder(WordBank wordBank) {
        this.wordBank = wordBank;
    }

    /**
     * Adds a new {@code Card} to the {@code WordBank} that we are building.
     */
    public WordBankBuilder withCard(Card person) {
        wordBank.addCard(person);
        return this;
    }

    public WordBank build() {
        return wordBank;
    }
}
