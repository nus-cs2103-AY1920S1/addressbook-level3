package seedu.flashcard.testutil;

import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_ANSWER_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_ANSWER_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_CHOICE_BLUE;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_CHOICE_GREEN;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_CHOICE_RED;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_CHOICE_YELLOW;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_DEFINITION_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_DEFINITION_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_QUESTION_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_QUESTION_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_LONG;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_ROUND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.McqFlashcard;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;

/**
 * A utility class containing a list of {@code Flashcard} objects to be used in tests
 */
public class TypicalFlashcard {

    public static final McqFlashcard MOUNT_BLANC = new FlashcardBuilder()
            .withQuestion("How long is Mount Blanc Tunnel?")
        .withAnswer("11611m").withDefinition("An amazing tunnel between Italy and France")
        .withTag("Geography", "Civil Engineering").withChoice("11611m", "12345m", "54321m").buildMcqFlashcard();
    public static final McqFlashcard DAXING_AIRPORT = new FlashcardBuilder()
        .withQuestion("Which is the name of the new airport in Beijing?").withAnswer("Daxing Airport")
        .withDefinition("The largest airport in China").withTag("Geography", "Civil Engineering")
        .withChoice("Xiongan Airport", "Daxing Airport", "Lixian Airport", "Zhuque Airport").withScore(2, 2)
            .buildMcqFlashcard();
    public static final ShortAnswerFlashcard CHANGI_AIRPORT = new FlashcardBuilder()
        .withQuestion("How many runways does Changi Airport have?").withDefinition("The airport in Singapore")
        .withTag("Civil Engineering").withAnswer("4").buildShortAnswerFlashcard();
    public static final ShortAnswerFlashcard TOKYO_AIRPORT = new FlashcardBuilder()
        .withQuestion("How many airports are there in Tokyo?").withDefinition("More than 1").withScore(0, 0)
        .withAnswer("2").buildShortAnswerFlashcard();

    public static final McqFlashcard APPLE = new FlashcardBuilder().withQuestion(VALID_QUESTION_APPLE)
            .withAnswer(VALID_ANSWER_APPLE).withDefinition(VALID_DEFINITION_APPLE).withTag(VALID_TAG_ROUND)
            .withChoice(VALID_CHOICE_RED, VALID_CHOICE_BLUE, VALID_CHOICE_GREEN, VALID_CHOICE_YELLOW)
            .buildMcqFlashcard();

    public static final ShortAnswerFlashcard BANANA = new FlashcardBuilder().withQuestion(VALID_QUESTION_BANANA)
            .withAnswer(VALID_ANSWER_BANANA).withDefinition(VALID_DEFINITION_BANANA).withTag(VALID_TAG_LONG)
            .buildShortAnswerFlashcard();
    public static final ShortAnswerFlashcard NO_TAG_BANANA = new FlashcardBuilder().withQuestion(VALID_QUESTION_BANANA)
            .withAnswer(VALID_ANSWER_BANANA).withDefinition(VALID_DEFINITION_BANANA)
            .buildShortAnswerFlashcard();

    /**
     * Returns an {@code FlashcardList} with all the typical flashcards.
     */
    public static FlashcardList getTypicalFlashcardList() {
        FlashcardList fl = new FlashcardList();
        for (Flashcard flashcard : getTypicalFlashcards()) {
            fl.addFlashcard(flashcard);
        }
        return fl;
    }

    public static List<Flashcard> getTypicalFlashcards() {
        return new ArrayList<>(Arrays.asList(MOUNT_BLANC, DAXING_AIRPORT, CHANGI_AIRPORT, TOKYO_AIRPORT));
    }

    /**
     *To avoid flip command bug where it increments score
     */
    public static List<Flashcard> getTypicalStatsFlashcards() {
        return new ArrayList<>(Arrays.asList(DAXING_AIRPORT, TOKYO_AIRPORT));
    }
}
