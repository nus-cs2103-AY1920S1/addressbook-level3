package seedu.address.model.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_CONCEPT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppData.ALGEBRA_QUESTION;
import static seedu.address.testutil.TypicalAppData.CONCEPT_QUESTION;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.question.exceptions.DuplicateQuestionException;
import seedu.address.model.question.exceptions.QuestionNotFoundException;
import seedu.address.testutil.QuestionBuilder;

class UniqueQuestionListTest {
    private final UniqueQuestionList uniqueQuestionList = new UniqueQuestionList();

    @Test
    public void contains_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.contains(null));
    }

    @Test
    public void contains_questionNotInList_returnsFalse() {
        assertFalse(uniqueQuestionList.contains(ALGEBRA_QUESTION));
    }

    @Test
    public void contains_questionInList_returnsTrue() {
        uniqueQuestionList.add(ALGEBRA_QUESTION);
        assertTrue(uniqueQuestionList.contains(ALGEBRA_QUESTION));
    }

    @Test
    public void contains_questionWithSameIdentityFieldsInList_returnsTrue() {
        uniqueQuestionList.add(ALGEBRA_QUESTION);
        Question editedAlgebra = new QuestionBuilder(ALGEBRA_QUESTION)
                .withAnswer(VALID_ANSWER_CONCEPT)
                .build();
        assertTrue(uniqueQuestionList.contains(editedAlgebra));
    }

    @Test
    public void add_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.add(null));
    }

    @Test
    public void add_duplicateQuestion_throwsDuplicateQuestionException() {
        uniqueQuestionList.add(ALGEBRA_QUESTION);
        assertThrows(DuplicateQuestionException.class, () -> uniqueQuestionList.add(ALGEBRA_QUESTION));
    }

    @Test
    public void setQuestion_nullTargetQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setQuestion(null, ALGEBRA_QUESTION));
    }

    @Test
    public void setQuestion_nullEditedQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList
                .setQuestion(ALGEBRA_QUESTION, null));
    }

    @Test
    public void setQuestion_targetQuestionNotInList_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> uniqueQuestionList
                .setQuestion(ALGEBRA_QUESTION, ALGEBRA_QUESTION));
    }

    @Test
    public void setQuestion_editedQuestionIsSameQuestion_success() {
        uniqueQuestionList.add(ALGEBRA_QUESTION);
        uniqueQuestionList.setQuestion(ALGEBRA_QUESTION, ALGEBRA_QUESTION);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(ALGEBRA_QUESTION);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestion_editedQuestionHasSameIdentity_success() {
        uniqueQuestionList.add(ALGEBRA_QUESTION);
        Question editedAlgebra = new QuestionBuilder(ALGEBRA_QUESTION)
                .withAnswer(VALID_ANSWER_CONCEPT)
                .build();
        uniqueQuestionList.setQuestion(ALGEBRA_QUESTION, editedAlgebra);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(editedAlgebra);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestion_editedQuestionHasDifferentIdentity_success() {
        uniqueQuestionList.add(ALGEBRA_QUESTION);
        uniqueQuestionList.setQuestion(ALGEBRA_QUESTION, CONCEPT_QUESTION);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(CONCEPT_QUESTION);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestion_editedQuestionHasNonUniqueIdentity_throwsDuplicateQuestionException() {
        uniqueQuestionList.add(ALGEBRA_QUESTION);
        uniqueQuestionList.add(CONCEPT_QUESTION);
        assertThrows(DuplicateQuestionException.class, () -> uniqueQuestionList
                .setQuestion(ALGEBRA_QUESTION, CONCEPT_QUESTION));
    }

    @Test
    public void remove_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.remove(null));
    }

    @Test
    public void remove_questionDoesNotExist_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> uniqueQuestionList.remove(ALGEBRA_QUESTION));
    }

    @Test
    public void remove_existingQuestion_removesQuestion() {
        uniqueQuestionList.add(ALGEBRA_QUESTION);
        uniqueQuestionList.remove(ALGEBRA_QUESTION);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestions_nullUniqueQuestionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setQuestions((UniqueQuestionList) null));
    }

    @Test
    public void setQuestions_uniqueQuestionList_replacesOwnListWithProvidedUniqueQuestionList() {
        uniqueQuestionList.add(ALGEBRA_QUESTION);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(CONCEPT_QUESTION);
        uniqueQuestionList.setQuestions(expectedUniqueQuestionList);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setQuestions((List<Question>) null));
    }

    @Test
    public void setQuestions_list_replacesOwnListWithProvidedList() {
        uniqueQuestionList.add(ALGEBRA_QUESTION);
        List<Question> questionList = Collections.singletonList(CONCEPT_QUESTION);
        uniqueQuestionList.setQuestions(questionList);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(CONCEPT_QUESTION);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestions_listWithDuplicateQuestions_throwsDuplicateQuestionException() {
        List<Question> listWithDuplicateQuestions = Arrays.asList(ALGEBRA_QUESTION, ALGEBRA_QUESTION);
        assertThrows(DuplicateQuestionException.class, () -> uniqueQuestionList
                .setQuestions(listWithDuplicateQuestions));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueQuestionList
                .asUnmodifiableObservableList().remove(0));
    }
}
