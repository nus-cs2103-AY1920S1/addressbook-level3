package seedu.address.model.quiz.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_CATEGORY_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TAG_LECTURE;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TYPE_BOB;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.QuestionBuilder;

public class QuestionTest {
    public static final Question ALICE = new QuestionBuilder().withName("What is alice favourite fruit?")
            .withAnswer("Watermelon").withCategory("Sec4").withType("normal").withTags("friends").build();
    public static final Question BOB = new QuestionBuilder().withName("What is bob favourite fruit?")
            .withAnswer("Banana").withCategory("PrimarySch").withType("high").withTags("owesMoney", "friends").build();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Question question = new QuestionBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> question.getTags().remove(0));
    }

    @Test
    public void isSameQuestion() {
        // same object -> returns true
        assertTrue(ALICE.isSameQuestion(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameQuestion(null));

        // different answer and category -> returns false
        Question editedAlice = new QuestionBuilder(ALICE).withAnswer(VALID_ANSWER_BOB)
                .withCategory(VALID_CATEGORY_BOB).build();
        assertFalse(ALICE.isSameQuestion(editedAlice));

        // different name -> returns false
        editedAlice = new QuestionBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameQuestion(editedAlice));

        // same name, same answer, different attributes -> returns false
        editedAlice = new QuestionBuilder(ALICE).withCategory(VALID_CATEGORY_BOB).withType(VALID_TYPE_BOB)
                .withTags(VALID_TAG_LECTURE).build();
        assertFalse(ALICE.isSameQuestion(editedAlice));

        // same name, same category, different attributes -> returns false
        editedAlice = new QuestionBuilder(ALICE).withAnswer(VALID_ANSWER_BOB).withType(VALID_TYPE_BOB)
                .withTags(VALID_TAG_LECTURE).build();
        assertFalse(ALICE.isSameQuestion(editedAlice));

        // same name, same answer, same category, different attributes -> returns true
        editedAlice = new QuestionBuilder(ALICE).withType(VALID_TYPE_BOB).withTags(VALID_TAG_LECTURE).build();
        assertTrue(ALICE.isSameQuestion(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Question aliceCopy = new QuestionBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different question -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Question editedAlice = new QuestionBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different answer -> returns false
        editedAlice = new QuestionBuilder(ALICE).withAnswer(VALID_ANSWER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different category -> returns false
        editedAlice = new QuestionBuilder(ALICE).withCategory(VALID_CATEGORY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different type -> returns false
        editedAlice = new QuestionBuilder(ALICE).withType(VALID_TYPE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new QuestionBuilder(ALICE).withTags(VALID_TAG_LECTURE).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
