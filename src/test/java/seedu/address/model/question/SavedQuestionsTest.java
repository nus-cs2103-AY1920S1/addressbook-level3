package seedu.address.model.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.question.TypicalQuestions.getTypicalSavedQuestions;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.question.exceptions.DuplicateQuestionException;
import seedu.address.testutil.question.QuestionBuilder;

public class SavedQuestionsTest {

    private final SavedQuestions savedQuestions = new SavedQuestions();
    private final Question question = new QuestionBuilder().withQuestion("What is 1+1?")
        .withAnswer("2").build();
    private final Question differentQuestion = new QuestionBuilder().withQuestion("What is 1+2?")
        .withAnswer("3").build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), savedQuestions.getSavedQuestions());
    }

    @Test
    public void initialise_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SavedQuestions(null));
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
    public void resetData_withDuplicateQuestions_throwsDuplicateQuestionException() {
        Question editedQuestion = new QuestionBuilder().withQuestion(question.getQuestion())
            .withAnswer(question.getAnswer()).build();
        List<Question> newQuestions = Arrays.asList(question, editedQuestion);
        SavedQuestionsTest.SavedQuestionsStub newData = new SavedQuestionsTest.SavedQuestionsStub(
            newQuestions);
        assertThrows(DuplicateQuestionException.class, () -> savedQuestions.resetData(newData));
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
    public void hasQuestions_questionInSavedQuestions_returnsTrue() {
        savedQuestions.addQuestion(question);
        assertTrue(savedQuestions.hasQuestion(question));
    }

    @Test
    public void hasQuestion_questionWithSameIdentityFieldsInSavedQuestions_returnsTrue() {
        savedQuestions.addQuestion(question);
        Question newQuestion = new QuestionBuilder().withQuestion("What is 1+1?").withAnswer("2")
            .build();
        assertTrue(savedQuestions.hasQuestion(newQuestion));
    }

    @Test
    public void getQuestionsList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            savedQuestions.getSavedQuestions().remove(0));
    }

    @Test
    public void getAllQuestions_questionInList_returnAllQuestions() {
        savedQuestions.addQuestion(question);

        ObservableList<Question> expectedList = FXCollections.observableArrayList();
        expectedList.add(question);

        assertEquals(savedQuestions.getAllQuestions(), expectedList);
    }

    @Test
    public void getSlideshowQuestions_questionInList_returnSlideshowQuestions() {
        savedQuestions.addQuestion(question);
        ObservableList<Index> indexList = FXCollections.observableArrayList();
        indexList.add(Index.fromOneBased(1));
        savedQuestions.setSlideshowQuestions(indexList);

        ObservableList<Question> expectedList = FXCollections.observableArrayList();
        expectedList.add(question);

        assertEquals(savedQuestions.getSlideshowQuestionList(), expectedList);
    }

    @Test
    public void setSlideshowQuestions_withValidQuestion_success() {
        savedQuestions.addQuestion(question);
        ObservableList<Index> indexList = FXCollections.observableArrayList();
        indexList.add(Index.fromOneBased(1));
        savedQuestions.setSlideshowQuestions(indexList);

        assertEquals(savedQuestions.getSlideshowQuestionList().size(), 1);
    }

    @Test
    public void setQuestion_withValidQuestion_success() {
        savedQuestions.addQuestion(question);
        savedQuestions.setQuestion(Index.fromOneBased(1), differentQuestion);

        ObservableList<Question> expectedList = FXCollections.observableArrayList();
        expectedList.add(differentQuestion);

        assertEquals(savedQuestions.getAllQuestions(), expectedList);
    }

    @Test
    public void searchQuestions_withValidSearchTerm_success() {
        String searchTerm = "What is 1+1?";
        savedQuestions.addQuestion(question);

        assertEquals(savedQuestions.searchQuestions(searchTerm),
            String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchTerm, 1));
    }

    /**
     * A stub ReadOnlyQuestions whose questions list can violate interface constraints.
     */
    private static class SavedQuestionsStub implements ReadOnlyQuestions {

        private final ObservableList<Question> questions = FXCollections.observableArrayList();

        SavedQuestionsStub(Collection<Question> questions) {
            this.questions.setAll(questions);
        }

        @Override
        public ObservableList<Question> getSavedQuestions() {
            return questions;
        }
    }
}
