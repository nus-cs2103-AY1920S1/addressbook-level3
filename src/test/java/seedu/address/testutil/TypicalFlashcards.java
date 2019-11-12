package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INTELLIJ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SHORTCUTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TWO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.StudyBuddyPro;
import seedu.address.model.flashcard.Flashcard;

/**
 * A utility class containing a list of {@code Flashcards} objects to be used in tests.
 */
public class TypicalFlashcards {

    public static final Flashcard MATH_ONE = new FlashcardBuilder().withQuestion("What is 2 + 2?")
            .withAnswer("4")
            .withTitle("Math Question 1")
            .withTags("maths").build();
    public static final Flashcard CS_ONE = new FlashcardBuilder().withQuestion("What does the SLAP acronym mean?")
            .withAnswer("Single Layer of Abstraction Principle")
            .withTitle("CS Question 1")
            .withTags("CS2103T").build();

    // Manually added - Flashcard's details found in {@code CommandTestUtil}
    public static final Flashcard INTELLIJ_ONE =
            new FlashcardBuilder().withQuestion(VALID_QUESTION_ONE)
            .withAnswer(VALID_ANSWER_ONE).withTitle(VALID_TITLE_ONE).withTags(VALID_TAG_INTELLIJ)
                    .build();
    public static final Flashcard INTELLIJ_TWO =
            new FlashcardBuilder().withQuestion(VALID_QUESTION_TWO)
            .withAnswer(VALID_ANSWER_TWO).withTitle(VALID_TITLE_TWO).withTags(VALID_TAG_INTELLIJ, VALID_TAG_SHORTCUTS)
                    .build();


    private TypicalFlashcards() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical flashcards.
     */
    public static StudyBuddyPro getTypicalStudyBuddyPro() {
        StudyBuddyPro ab = new StudyBuddyPro();
        for (Flashcard flashcard : getTypicalFlashcards()) {
            ab.addFlashcard(flashcard);
        }
        return ab;
    }

    public static List<Flashcard> getTypicalFlashcards() {
        return new ArrayList<>(Arrays.asList(MATH_ONE, CS_ONE, INTELLIJ_ONE, INTELLIJ_TWO));
    }
}
