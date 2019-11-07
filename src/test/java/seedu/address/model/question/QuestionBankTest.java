package seedu.address.model.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.question.exceptions.DuplicateQuestionException;
import seedu.address.testutil.question.QuestionBuilder;

public class QuestionBankTest {

    private final QuestionBank questions = new QuestionBank();
    private final Question question = new QuestionBuilder().withQuestion("What is 1+1?")
        .withAnswer("2").build();
    private final Question differentQuestion = new QuestionBuilder().withQuestion("What is 1+2?")
        .withAnswer("3").build();

    @Test
    public void contains_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> questions.contains(null));
    }

    @Test
    public void contains_questionNotInList_returnsFalse() {
        Assertions.assertFalse(questions.contains(question));
    }

    @Test
    public void contains_questionInList_returnsTrue() {
        questions.addQuestion(question);
        Assertions.assertTrue(questions.contains(question));
    }

    @Test
    public void contains_questionWithSameIdentityFieldsInList_returnsTrue() {
        questions.addQuestion(question);
        Question editedQuestion = new QuestionBuilder().withQuestion("What is 1+1?").withAnswer("2")
            .build();
        Assertions.assertTrue(questions.contains(editedQuestion));
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
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            questions.asUnmodifiableObservableList().remove(0));
    }
}
