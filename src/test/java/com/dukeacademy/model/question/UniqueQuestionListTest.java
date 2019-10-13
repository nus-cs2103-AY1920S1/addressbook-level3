package com.dukeacademy.model.question;

import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static com.dukeacademy.testutil.Assert.assertThrows;
import static com.dukeacademy.testutil.TypicalQuestions.ALICE;
import static com.dukeacademy.testutil.TypicalQuestions.BOB;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.exceptions.DuplicateQuestionException;
import com.dukeacademy.model.question.exceptions.QuestionNotFoundException;
import com.dukeacademy.testutil.QuestionBuilder;

public class UniqueQuestionListTest {

    private final UniqueQuestionList
        uniqueQuestionList = new UniqueQuestionList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueQuestionList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueQuestionList.add(ALICE);
        assertTrue(uniqueQuestionList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueQuestionList.add(ALICE);
        Question editedAlice = new QuestionBuilder(ALICE).withDifficulty(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                                                         .build();
        assertTrue(uniqueQuestionList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueQuestionList.add(ALICE);
        assertThrows(DuplicateQuestionException.class, () -> uniqueQuestionList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList
            .setQuestion(null, ALICE));
    }

    @Test
    public void setQuestion_nullEditedQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList
            .setQuestion(ALICE, null));
    }

    @Test
    public void setQuestion_targetQuestionNotInList_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> uniqueQuestionList
            .setQuestion(ALICE, ALICE));
    }

    @Test
    public void setQuestion_editedQuestionIsSameQuestion_success() {
        uniqueQuestionList.add(ALICE);
        uniqueQuestionList.setQuestion(ALICE, ALICE);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(ALICE);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestion_editedQuestionHasSameIdentity_success() {
        uniqueQuestionList.add(ALICE);
        Question editedAlice = new QuestionBuilder(ALICE).withDifficulty(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                                                         .build();
        uniqueQuestionList.setQuestion(ALICE, editedAlice);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(editedAlice);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestion_editedQuestionHasDifferentIdentity_success() {
        uniqueQuestionList.add(ALICE);
        uniqueQuestionList.setQuestion(ALICE, BOB);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(BOB);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestion_editedQuestionHasNonUniqueIdentity_throwsDuplicateQuestionException() {
        uniqueQuestionList.add(ALICE);
        uniqueQuestionList.add(BOB);
        assertThrows(DuplicateQuestionException.class, () -> uniqueQuestionList
            .setQuestion(ALICE, BOB));
    }

    @Test
    public void remove_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.remove(null));
    }

    @Test
    public void remove_questionDoesNotExist_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> uniqueQuestionList.remove(ALICE));
    }

    @Test
    public void remove_existingQuestion_removesQuestion() {
        uniqueQuestionList.add(ALICE);
        uniqueQuestionList.remove(ALICE);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestions_nullUniqueQuestionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList
            .setQuestions((UniqueQuestionList) null));
    }

    @Test
    public void setQuestions_uniqueQuestionList_replacesOwnListWithProvidedUniqueQuestionList() {
        uniqueQuestionList.add(ALICE);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(BOB);
        uniqueQuestionList.setQuestions(expectedUniqueQuestionList);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setQuestions((List<Question>) null));
    }

    @Test
    public void setQuestions_list_replacesOwnListWithProvidedList() {
        uniqueQuestionList.add(ALICE);
        List<Question> questionList = Collections.singletonList(BOB);
        uniqueQuestionList.setQuestions(questionList);
        UniqueQuestionList expectedUniqueQuestionList = new UniqueQuestionList();
        expectedUniqueQuestionList.add(BOB);
        assertEquals(expectedUniqueQuestionList, uniqueQuestionList);
    }

    @Test
    public void setQuestions_listWithDuplicateQuestions_throwsDuplicateQuestionException() {
        List<Question> listWithDuplicateQuestions = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateQuestionException.class, () -> uniqueQuestionList.setQuestions(
            listWithDuplicateQuestions));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueQuestionList.asUnmodifiableObservableList().remove(0));
    }
}
