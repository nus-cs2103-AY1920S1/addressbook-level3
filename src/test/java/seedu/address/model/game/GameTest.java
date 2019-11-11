package seedu.address.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;
import static seedu.address.testutil.TypicalCards.CHARIZARD;
import static seedu.address.testutil.TypicalCards.DITTO;
import static seedu.address.testutil.TypicalCards.EEVEE;

import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.appsettings.DifficultyEnum;
import seedu.address.model.card.Card;
import seedu.address.model.wordbank.WordBank;
import seedu.address.testutil.WordBankBuilder;


public class GameTest {

    @Test
    public void checkGuess() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(ABRA);
        WordBank wb = wordBankBuilder.build();
        Game game = new Game(wb, x -> Collections.shuffle(x), DifficultyEnum.EASY);

        // Makes correct guess; guess Abra as Abra
        assertTrue(game.checkGuess(new Guess(ABRA.getWord().toString())));

        // Makes wrong guess; guess Abra as Butterfree
        assertFalse(game.checkGuess(new Guess(BUTTERFREE.getWord().toString())));

        // When a guess is made after game has already ended
        assertThrows(UnsupportedOperationException.class, () -> {
            game.moveToNextCard();
            game.checkGuess(new Guess(BUTTERFREE.getWord().toString()));
        });
    }

    @Test
    public void nullWordBankPassedIntoConstructor_throwsNullPointerException() {
        WordBank wb = null;
        assertThrows(NullPointerException.class, () -> new Game(wb, x -> Collections.shuffle(x), DifficultyEnum.EASY));
    }

    @Test
    public void nullDifficultyEnumPassedIntoConstructor_throwsNullPointerException() {
        WordBank wb = new WordBank("empty");
        DifficultyEnum difficultyEnum = null;
        assertThrows(NullPointerException.class, () -> {
            new Game(wb, x -> Collections.shuffle(x), difficultyEnum);
        });
    }

    @Test
    public void nullCardShufflerPassedIntoConstructor_throwsNullPointerException() {
        WordBank wb = new WordBank("empty");
        DifficultyEnum difficultyEnum = DifficultyEnum.EASY;
        assertThrows(NullPointerException.class, () -> {
            new Game(wb, null, difficultyEnum);
        });
    }

    @Test
    public void moveToNextCard() {
        // HashMap that maps each card to the number of times it has been seen by the user.
        HashMap<Card, Integer> cardVisitedCountMap = new HashMap<>();

        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(ABRA);
        wordBankBuilder.withCard(BUTTERFREE);
        wordBankBuilder.withCard(CHARIZARD);
        wordBankBuilder.withCard(DITTO);
        WordBank wb = wordBankBuilder.build();

        // Populate HashMap with all the cards in this test wordBank
        for (int i = 0; i < wb.size(); i++) {
            cardVisitedCountMap.put(wb.getCard(Index.fromZeroBased(i)), 0);
        }

        Game game = new Game(wb, x -> Collections.shuffle(x), DifficultyEnum.EASY);

        // Mark the first card as being seen once.
        Card firstCardOfBank = game.getCurrCard();
        cardVisitedCountMap.put(firstCardOfBank,
                cardVisitedCountMap.get(firstCardOfBank) + 1);

        // Mark the rest of the card as being seen once.
        for (int j = 0; j < wb.size() - 1; j++) {
            game.moveToNextCard();
            Card currentCard = game.getCurrCard();
            cardVisitedCountMap.put(currentCard,
                    cardVisitedCountMap.get(currentCard) + 1);
        }

        // Check that all cards were visited exactly once.
        for (Card key : cardVisitedCountMap.keySet()) {
            assertEquals(1, cardVisitedCountMap.get(key));
        }

        // Game should be over now.
        assertThrows(UnsupportedOperationException.class, () -> {
            game.moveToNextCard();
            game.getCurrQuestion();
        });
    }

    @Test
    public void isOver() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(ABRA);
        wordBankBuilder.withCard(BUTTERFREE);
        WordBank wb = wordBankBuilder.build();
        Game game = new Game(wb, x -> Collections.shuffle(x), DifficultyEnum.EASY);

        // Game has not ended, 2 cards left.
        assertFalse(game.isOver());

        game.moveToNextCard();

        // Game has not ended, 1 card left.
        assertFalse(game.isOver());

        game.moveToNextCard();

        // Game has ended, 0 card left.
        assertTrue(game.isOver());
    }

    @Test
    public void getCurrQuestion() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(ABRA);
        WordBank wb = wordBankBuilder.build();
        Game game = new Game(wb, x -> Collections.shuffle(x), DifficultyEnum.EASY);

        // ABRA card shows correctly.
        assertEquals(ABRA.getMeaning().toString(), game.getCurrQuestion());

        // Attempting to show current card's meaning when game already ended
        assertThrows(UnsupportedOperationException.class, () -> {
            game.moveToNextCard();
            game.getCurrQuestion();
        });
    }

    @Test
    public void forceStop() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(ABRA);
        wordBankBuilder.withCard(BUTTERFREE);
        WordBank wb = wordBankBuilder.build();
        Game game = new Game(wb, x -> Collections.shuffle(x), DifficultyEnum.EASY);

        // Game has not ended yet.
        assertFalse(game.isOver());

        // Game is forcibly stopped.
        game.forceStop();
        assertTrue(game.isOver());
    }

    @Test
    public void setShuffledDeckOfCards() {
        HashMap<Card, Integer> cardVisitedCountMap = new HashMap<>();
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(ABRA);
        wordBankBuilder.withCard(BUTTERFREE);
        wordBankBuilder.withCard(CHARIZARD);
        wordBankBuilder.withCard(DITTO);
        wordBankBuilder.withCard(EEVEE);
        WordBank wb = wordBankBuilder.build();
        // Populate HashMap with all the cards in this test wordBank
        for (int i = 0; i < wb.size(); i++) {
            cardVisitedCountMap.put(wb.getCard(Index.fromZeroBased(i)), 0);
        }

        Game game = new Game(wb, x -> Collections.shuffle(x), DifficultyEnum.EASY);


        // Shuffling method should not change the set of possible cards or introduce duplicates.
        for (int i = 0; i < wb.size(); i++) {
            Card currCard = game.getCurrCard();
            assertTrue(cardVisitedCountMap.containsKey(currCard));
            cardVisitedCountMap.put(currCard, cardVisitedCountMap.get(currCard) + 1);
            game.moveToNextCard();
        }

        // Check that all cards were visited exactly once.
        for (Card key : cardVisitedCountMap.keySet()) {
            assertEquals(1, cardVisitedCountMap.get(key));
        }

        // Shuffling method should not change number of cards.
        assertEquals(wb.size(), cardVisitedCountMap.size());

        assertTrue(game.isOver());
        assertThrows(UnsupportedOperationException.class, () -> {
            game.moveToNextCard();
            game.getCurrQuestion();
        });

    }

    @Test
    void getCurrentGameDifficulty() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(ABRA);
        wordBankBuilder.withCard(BUTTERFREE);
        wordBankBuilder.withCard(CHARIZARD);
        wordBankBuilder.withCard(DITTO);
        wordBankBuilder.withCard(EEVEE);
        WordBank wb = wordBankBuilder.build();
        Game testGame = new Game(wb, x -> Collections.shuffle(x),
                DifficultyEnum.EASY);
        assertEquals(DifficultyEnum.EASY, testGame.getCurrentGameDifficulty());
    }

    @Test
    void getTimeAllowedPerQuestion() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(ABRA);
        wordBankBuilder.withCard(BUTTERFREE);
        wordBankBuilder.withCard(CHARIZARD);
        wordBankBuilder.withCard(DITTO);
        wordBankBuilder.withCard(EEVEE);
        WordBank wb = wordBankBuilder.build();

        for (int i = 0; i < DifficultyEnum.values().length; i++) {
            Game testGame = new Game(wb, x -> Collections.shuffle(x),
                    DifficultyEnum.values()[i]);
            assertEquals(DifficultyEnum.values()[i].getTimeAllowedPerQuestion(), testGame.getTimeAllowedPerQuestion());
        }
    }

    @Test
    void getCurrCard() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(EEVEE);
        WordBank wb = wordBankBuilder.build();
        Game.CardShuffler dummyShuffler = cardsToShuffle -> {};
        Game testGame = new Game(wb, dummyShuffler,
                DifficultyEnum.EASY);
        assertEquals(EEVEE, testGame.getCurrCard());
    }

    @Test
    void getHintFormatForCurrCard() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(EEVEE);
        WordBank wb = wordBankBuilder.build();
        Game.CardShuffler dummyShuffler = cardsToShuffle -> {};
        Game testGame = new Game(wb, dummyShuffler,
                DifficultyEnum.EASY);

        int underscoreCount = 0;
        String formattedHintString = testGame.getCurrCardFormattedHint().toString();
        for (int i = 0; i < 5; i++) {
            if (formattedHintString.charAt(i) == '_') {
                underscoreCount++;
            }
        }
        assertEquals(4, underscoreCount);
    }

    @Test
    void getHintFormatSizeOfCurrCard() {
        WordBankBuilder wordBankBuilder = new WordBankBuilder();
        wordBankBuilder.withCard(EEVEE);
        wordBankBuilder.withCard(ABRA);
        WordBank wb = wordBankBuilder.build();
        Game.CardShuffler dummyShuffler = cardsToShuffle -> {};
        Game testGame = new Game(wb, dummyShuffler,
                DifficultyEnum.EASY);
        assertEquals(5, testGame.getHintFormatSizeOfCurrCard());
        testGame.moveToNextCard();
        assertEquals(4, testGame.getHintFormatSizeOfCurrCard());
    }

}
