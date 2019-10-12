package com.dukeacademy.model.question;

import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static com.dukeacademy.testutil.Assert.assertThrows;
import static com.dukeacademy.testutil.TypicalQuestions.ALICE;
import static com.dukeacademy.testutil.TypicalQuestions.BOB;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dukeacademy.testutil.QuestionBuilder;

public class QuestionTest {

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

        // different phone and email -> returns false
        Question
            editedAlice = new QuestionBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameQuestion(editedAlice));

        // different title -> returns false
        editedAlice = new QuestionBuilder(ALICE).withTitle(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameQuestion(editedAlice));

        // same title, same phone, different attributes -> returns true
        editedAlice = new QuestionBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                                                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameQuestion(editedAlice));

        // same title, same email, different attributes -> returns true
        editedAlice = new QuestionBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                                                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameQuestion(editedAlice));

        // same title, same phone, same email, different attributes -> returns true
        editedAlice = new QuestionBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
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

        // different title -> returns false
        Question
            editedAlice = new QuestionBuilder(ALICE).withTitle(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new QuestionBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new QuestionBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new QuestionBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new QuestionBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
