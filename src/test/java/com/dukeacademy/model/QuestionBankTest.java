package com.dukeacademy.model;

import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static com.dukeacademy.testutil.Assert.assertThrows;
import static com.dukeacademy.testutil.TypicalQuestions.ALICE;
import static com.dukeacademy.testutil.TypicalQuestions.getTypicalQuestionBank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.exceptions.DuplicateQuestionException;
import com.dukeacademy.testutil.QuestionBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QuestionBankTest {

    private final QuestionBank addressBook = new QuestionBank();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getQuestionList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        QuestionBank newData = getTypicalQuestionBank();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateQuestions_throwsDuplicateQuestionException() {
        // Two questions with the same identity fields
        Question editedAlice = new QuestionBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                                                         .build();
        List<Question> newQuestions = Arrays.asList(ALICE, editedAlice);
        QuestionBankStub newData = new QuestionBankStub(newQuestions);

        assertThrows(DuplicateQuestionException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasQuestion_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasQuestion(null));
    }

    @Test
    public void hasQuestion_questionNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasQuestion(ALICE));
    }

    @Test
    public void hasQuestion_questionInAddressBook_returnsTrue() {
        addressBook.addQuestion(ALICE);
        assertTrue(addressBook.hasQuestion(ALICE));
    }

    @Test
    public void hasQuestion_questionWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addQuestion(ALICE);
        Question editedAlice = new QuestionBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                                                         .build();
        assertTrue(addressBook.hasQuestion(editedAlice));
    }

    @Test
    public void getQuestionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getQuestionList().remove(0));
    }

    /**
     * A stub ReadOnlyQuestionBank whose questions list can violate interface constraints.
     */
    private static class QuestionBankStub implements ReadOnlyQuestionBank {
        private final ObservableList<Question> questions = FXCollections.observableArrayList();

        QuestionBankStub(Collection<Question> questions) {
            this.questions.setAll(questions);
        }

        @Override
        public ObservableList<Question> getQuestionList() {
            return questions;
        }
    }

}
