package seedu.address.model.question;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.question.exceptions.DuplicateQuestionException;
import seedu.address.testutil.question.QuestionBankStub;
import seedu.address.testutil.question.QuestionBuilder;

public class QuestionBankTest {

    private final QuestionBank questions = new QuestionBank();
    private final Question question = new QuestionBuilder().withQuestion("What is 1+1?")
        .withAnswer("2").build();
    private final Question differentQuestion = new QuestionBuilder().withQuestion("What is 1+2?")
        .withAnswer("3").build();
    private final Question mcqQuestion = new QuestionBuilder().withQuestion("What is 1+2?")
        .withAnswer("3").withType("mcq").withOptionA("1").withOptionB("2").withOptionC("3")
        .withOptionD("4").build();

    @Test
    public void contains_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> questions.contains(null));
    }

    @Test
    public void contains_questionNotInList_returnsFalse() {
        assertFalse(questions.contains(question));
    }

    @Test
    public void contains_questionInList_returnsTrue() {
        questions.addQuestion(question);
        assertTrue(questions.contains(question));
    }

    @Test
    public void contains_questionWithSameIdentityFieldsInList_returnsTrue() {
        questions.addQuestion(question);
        Question editedQuestion = new QuestionBuilder().withQuestion("What is 1+1?").withAnswer("2")
            .build();
        assertTrue(questions.contains(editedQuestion));
    }

    @Test
    public void addQuestion_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> questions.addQuestion(null));
    }

    @Test
    public void addQuestion_duplicateQuestion_throwsDuplicateQuestionException() {
        questions.addQuestion(question);
        assertThrows(DuplicateQuestionException.class, () -> questions.addQuestion(question));
    }

    @Test
    public void setQuestion_nullTargetQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            questions.setQuestion((Index) null, question));
    }

    @Test
    public void setQuestion_nullEditedQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> questions.setQuestion(question, null));
    }

    @Test
    public void setQuestion_editedQuestionIsSameQuestion_success() {
        questions.addQuestion(question);
        questions.setQuestion(question, question);

        QuestionBank expectedQuestionBank = new QuestionBank();
        expectedQuestionBank.addQuestion(question);
        assertEquals(expectedQuestionBank, questions);
    }

    @Test
    public void setQuestion_editedPersonHasSameIdentity_success() {
        questions.addQuestion(question);
        Question editedQuestion = new QuestionBuilder().withQuestion("What is 1+3?").withAnswer("4")
            .build();
        questions.setQuestion(question, editedQuestion);

        QuestionBank expectedQuestionBank = new QuestionBank();
        expectedQuestionBank.addQuestion(editedQuestion);
        assertEquals(expectedQuestionBank, questions);
    }

    @Test
    public void setQuestion_editedQuestionHasDifferentIdentity_success() {
        questions.addQuestion(question);
        questions.setQuestion(question, differentQuestion);
        QuestionBank expectedQuestionBank = new QuestionBank();
        expectedQuestionBank.addQuestion(differentQuestion);
        assertEquals(expectedQuestionBank, questions);
    }

    @Test
    public void setQuestion_editedQuestionHasNonUniqueIdentity_throwsDuplicateQuestionException() {
        questions.addQuestion(question);
        questions.addQuestion(differentQuestion);
        assertThrows(
            DuplicateQuestionException.class, () ->
                questions.setQuestion(Index.fromOneBased(1), differentQuestion));
    }

    @Test
    public void setQuestion_withNewListAndNoRepeatedQuestions_success() {
        questions.addQuestion(question);

        QuestionBankStubAddSetQuestion stub = new QuestionBankStubAddSetQuestion();
        List<Question> questionList = new ArrayList<>();
        questionList.add(question);
        stub.setQuestions(questionList);

        assertEquals(questions.getAllQuestions().size(), stub.questions.size());
    }

    @Test
    public void setQuestion_withNewListAndRepeatedQuestions_success() {
        questions.addQuestion(question);

        QuestionBankStubAddSetQuestion stub = new QuestionBankStubAddSetQuestion();
        List<Question> questionList = new ArrayList<>();
        questionList.add(question);
        stub.setQuestions(questionList);

        // This list should not replace the previous
        questionList.add(question);
        stub.setQuestions(questionList);

        assertEquals(questions.getAllQuestions().size(), stub.questions.size());
    }

    @Test
    public void remove_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> questions.deleteQuestion((Index) null));
    }

    @Test
    public void remove_existingQuestion_removesQuestion() {
        questions.addQuestion(question);
        questions.deleteQuestion(question);
        QuestionBank expectedQuestionBank = new QuestionBank();
        assertEquals(expectedQuestionBank, questions);
    }

    @Test
    public void searchQuestion_nullSearch_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> questions.searchQuestions(null));
    }

    @Test
    public void searchQuestion_searchQuestionThatExists_success() {
        questions.addQuestion(question);
        String searchStr = "What is 1+1?";
        String result = questions.searchQuestions(searchStr);
        String expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 1);
        assertEquals(result, expectedResult);

        questions.addQuestion(differentQuestion);
        searchStr = "What is 1+1?";
        result = questions.searchQuestions(searchStr);
        expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 2);
        assertEquals(result, expectedResult);

        searchStr = "What is 1+2?";
        result = questions.searchQuestions(searchStr);
        expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 2);
        assertEquals(result, expectedResult);
    }

    @Test
    public void searchQuestion_searchQuestionThatDontExist_returnNoResult() {
        String searchStr = "What is 1+1?";
        String result = questions.searchQuestions(searchStr);
        String expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 0);
        assertEquals(result, expectedResult);

        searchStr = "What is 1+2?";
        result = questions.searchQuestions(searchStr);
        expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 0);
        assertEquals(result, expectedResult);

        questions.addQuestion(question);
        questions.addQuestion(differentQuestion);
        searchStr = "Definition of pi?";
        result = questions.searchQuestions(searchStr);
        expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 0);
        assertEquals(result, expectedResult);

        searchStr = "What is 999 + 999?";
        result = questions.searchQuestions(searchStr);
        expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 0);
        assertEquals(result, expectedResult);

        searchStr = "What is 999 + 999?";
        result = questions.searchQuestions(searchStr);
        expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 0);
        assertEquals(result, expectedResult);
    }

    @Test
    public void searchQuestion_searchQuestionWithWrongSpelling_success() {
        questions.addQuestion(question);
        questions.addQuestion(differentQuestion);
        String searchStr = "Whxt is 1+1?";
        String result = questions.searchQuestions(searchStr);
        String expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 2);
        assertEquals(result, expectedResult);

        searchStr = "Whxt xs 1+2?";
        result = questions.searchQuestions(searchStr);
        expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 2);
        assertEquals(result, expectedResult);

        searchStr = "Whaat is 1+2??";
        result = questions.searchQuestions(searchStr);
        expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 2);
        assertEquals(result, expectedResult);
    }

    @Test
    public void searchQuestion_searchQuestionWithIncompleteSentence_success() {
        questions.addQuestion(question);
        questions.addQuestion(differentQuestion);
        String searchStr = "What is ";
        String result = questions.searchQuestions(searchStr);
        String expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 2);
        assertEquals(result, expectedResult);

        searchStr = "What is 1+";
        result = questions.searchQuestions(searchStr);
        expectedResult = String.format(QuestionBank.SEARCH_RESULT_SUCCESS, searchStr, 2);
        assertEquals(result, expectedResult);
    }

    @Test
    public void setQuestions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            questions.setQuestions((List<Question>) null));
    }

    @Test
    public void setQuestions_list_replacesOwnListWithProvidedList() {
        questions.addQuestion(question);
        List<Question> questionsList = Collections.singletonList(differentQuestion);
        questions.setQuestions(questionsList);
        QuestionBank expectedQuestionBank = new QuestionBank();
        expectedQuestionBank.addQuestion(differentQuestion);
        assertEquals(expectedQuestionBank, questions);
    }

    @Test
    public void setQuestions_listWithDuplicateQuestions_throwsDuplicateQuestionException() {
        List<Question> listWithDuplicateQuestions = Arrays.asList(question, question);
        assertThrows(DuplicateQuestionException.class, () ->
            questions.setQuestions(listWithDuplicateQuestions));
    }

    @Test
    public void getAllQuestions_listIsNotEmpty_success() {
        questions.addQuestion(question);

        ObservableList<Question> expectedList = FXCollections.observableArrayList();
        expectedList.add(question);
        assertEquals(questions.getAllQuestions(), expectedList);
    }

    @Test
    public void getSearchQuestions_listIsEmpty_success() {
        questions.addQuestion(question);

        ObservableList<Question> expectedList = FXCollections.observableArrayList();
        assertEquals(questions.getSearchQuestions(), expectedList);
    }

    @Test
    public void getQuestionsSummary_questionsInList_success() {
        questions.addQuestion(question);

        String expectedResult = "Below is the list of questions.\n"
            + "There are currently 1 questions saved.\n";
        assertEquals(questions.getQuestionsSummary(), expectedResult);
    }

    @Test
    public void getMcqQuestion_questionInList_success() {
        questions.addQuestion(mcqQuestion);

        ObservableList<Question> expectedList = FXCollections.observableArrayList();
        expectedList.add(mcqQuestion);
        assertEquals(questions.getMcqQuestions(), expectedList);
    }

    @Test
    public void getOpenEndedQuestion_questionInList_success() {
        questions.addQuestion(question);

        ObservableList<Question> expectedList = FXCollections.observableArrayList();
        expectedList.add(question);
        assertEquals(questions.getOpenEndedQuestions(), expectedList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            questions.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals() {
        QuestionBank questionBank = new QuestionBank();

        // Same object
        assertTrue(questionBank.equals(questionBank));

        // Null
        assertFalse(questionBank.equals(null));
    }

    /**
     * A Model stub that always accept the question being added.
     */
    private class QuestionBankStubAddSetQuestion extends QuestionBankStub {

        private final ObservableList<Question> questions = FXCollections.observableArrayList();

        @Override
        public void addQuestion(Question question) {
            requireNonNull(question);
            this.questions.add(question);
        }

        @Override
        public void setQuestions(List<Question> questions) {
            if (!isRepeated(questions)) {
                this.questions.setAll(questions);
            }
        }

        /**
         * Returns true if a question has been repeated, else false.
         *
         * @param questions the list of questions to check.
         * @return True if the question has been repeated, else false.
         */
        private boolean isRepeated(List<Question> questions) {
            for (int i = 0; i < questions.size() - 1; i++) {
                for (int j = i + 1; j < questions.size(); j++) {
                    if (questions.get(i).equals(questions.get(j))) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
