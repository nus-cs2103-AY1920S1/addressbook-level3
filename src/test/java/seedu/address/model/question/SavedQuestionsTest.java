package seedu.address.model.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.question.TypicalQuestions.getTypicalSavedQuestions;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.question.QuestionBuilder;

public class SavedQuestionsTest {
    private final SavedQuestions savedQuestions = new SavedQuestions();
    private final Question question = new QuestionBuilder().withQuestion("What is 1+1?").withAnswer("2").build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), savedQuestions.getSavedQuestions());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savedQuestions.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyQuestions_replacesData() {
        SavedQuestions newData = getTypicalSavedQuestions();
        savedQuestions.resetData(newData);
        assertEquals(newData, savedQuestions);
    }

    @Test
    public void hasQuestion_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savedQuestions.hasQuestion(null));
    }

    @Test
    public void hasQuestion_questionNotInSavedQuestions_returnsFalse() {
        assertFalse(savedQuestions.hasQuestion(question));
    }

    @Test
    public void hasQuestions_noteInSavedQuestions_returnsTrue() {
        savedQuestions.addQuestion(question);
        assertTrue(savedQuestions.hasQuestion(question));
    }

    @Test
    public void hasQuestion_questionWithSameIdentityFieldsInSavedQuestions_returnsTrue() {
        savedQuestions.addQuestion(question);
        Question newQuestion = new QuestionBuilder().withQuestion("What is 1+1?").withAnswer("2").build();
        assertTrue(savedQuestions.hasQuestion(newQuestion));
    }

    @Test
    public void getQuestionsList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> savedQuestions.getSavedQuestions().remove(0));
    }
}
