package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BUG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORD_BUTTERFREE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.getTypicalWordBank;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.testutil.CardBuilder;

public class WordBankTest {

    private final WordBank wordBank = new WordBank();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), wordBank.getCardList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> wordBank.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyWordBank_replacesData() {
        WordBank newData = getTypicalWordBank();
        wordBank.resetData(newData);
        assertEquals(newData, wordBank);
    }

    @Test
    public void resetData_withDuplicateCards_throwsDuplicateCardException() {
        // Two Cards with the same identity fields
        Card editedAbra = new CardBuilder(ABRA).withWord(VALID_WORD_BUTTERFREE).withTags(VALID_TAG_BUG)
                .build();
        List<Card> newCards = Arrays.asList(ABRA, editedAbra);
        WordBankStub newData = new WordBankStub(newCards);

        assertThrows(DuplicateCardException.class, () -> wordBank.resetData(newData));
    }

    @Test
    public void hasCard_nullCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> wordBank.hasCard(null));
    }

    @Test
    public void hasCard_cardNotInWordBank_returnsFalse() {
        assertFalse(wordBank.hasCard(ABRA));
    }

    @Test
    public void hasCard_cardInWordBank_returnsTrue() {
        wordBank.addCard(ABRA);
        assertTrue(wordBank.hasCard(ABRA));
    }

    @Test
    public void hasCard_cardWithSameIdentityFieldsInWordBank_returnsTrue() {
        wordBank.addCard(ABRA);
        Card editedAbra = new CardBuilder(ABRA).withWord(VALID_WORD_BUTTERFREE).withTags(VALID_TAG_BUG)
                .build();
        assertTrue(wordBank.hasCard(editedAbra));
    }

    @Test
    public void getCardList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> wordBank.getCardList().remove(0));
    }

    /**
     * A stub ReadOnlyWordBankList whose cards list can violate interface constraints.
     */
    private static class WordBankStub implements ReadOnlyWordBank {
        private final ObservableList<Card> cards = FXCollections.observableArrayList();

        WordBankStub(Collection<Card> cards) {
            this.cards.setAll(cards);
        }

        @Override
        public ObservableList<Card> getCardList() {
            return cards;
        }

        @Override
        public Card getCard(Index index) {
            return cards.get(index.getZeroBased());
        }

        @Override
        public int size() {
            return cards.size();
        }
    }

}
