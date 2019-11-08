package seedu.address.model.quiz.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TAG_LECTURE;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TYPE_BOB;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.quiz.person.exceptions.DuplicateQuestionException;
import seedu.address.model.quiz.person.exceptions.QuestionNotFoundException;
import seedu.address.testutil.QuestionBuilder;


public class UniqueQuestionListTest {
    public static final Question ALICE = new QuestionBuilder().withName("What is alice favourite fruit?")
            .withAnswer("Watermelon").withCategory("Sec4").withType("normal").withTags("friends").build();
    public static final Question BOB = new QuestionBuilder().withName("What is bob favourite fruit?")
            .withAnswer("Banana").withCategory("PrimarySch").withType("high").withTags("owesMoney", "friends").build();
    private final UniqueQuestionList uniqueQuestionList = new UniqueQuestionList();

    @Test
    public void contains_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.contains(null));
    }

    @Test
    public void contains_questionNotInList_returnsFalse() {
        assertFalse(uniqueQuestionList.contains(ALICE));
    }

    @Test
    public void contains_questionInList_returnsTrue() {
        uniqueQuestionList.add(ALICE);
        assertTrue(uniqueQuestionList.contains(ALICE));
    }

    @Test
    public void contains_questionWithSameIdentityFieldsInList_returnsTrue() {
        uniqueQuestionList.add(ALICE);
        Question editedAlice = new QuestionBuilder(ALICE).withType(VALID_TYPE_BOB).withTags(VALID_TAG_LECTURE)
                .build();
        assertTrue(uniqueQuestionList.contains(editedAlice));
    }

    @Test
    public void add_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.add(null));
    }

    @Test
    public void add_duplicateQuestion_throwsDuplicateQuestionException() {
        uniqueQuestionList.add(ALICE);
        assertThrows(DuplicateQuestionException.class, () -> uniqueQuestionList.add(ALICE));
    }

    @Test
    public void setQuestion_nullTargetQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setQuestion(null, ALICE));
    }

    @Test
    public void setQuestion_nullEditedQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setQuestion(ALICE, null));
    }

    @Test
    public void setQuestion_targetQuestionNotInList_throwsQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> uniqueQuestionList.setQuestion(ALICE, ALICE));
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
        Question editedAlice = new QuestionBuilder(ALICE).withType(VALID_TYPE_BOB).withTags(VALID_TAG_LECTURE)
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
        assertThrows(DuplicateQuestionException.class, () -> uniqueQuestionList.setQuestion(ALICE, BOB));
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
        assertThrows(NullPointerException.class, () -> uniqueQuestionList.setQuestions((UniqueQuestionList) null));
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
        assertThrows(DuplicateQuestionException.class, () ->
                uniqueQuestionList.setQuestions(listWithDuplicateQuestions));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueQuestionList.asUnmodifiableObservableList().remove(0));
    }
}
