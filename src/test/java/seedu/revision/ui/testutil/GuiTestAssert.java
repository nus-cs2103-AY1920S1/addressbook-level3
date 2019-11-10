package seedu.revision.ui.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.AnswerableCardHandle;
import guitests.guihandles.AnswerableListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.revision.model.answerable.Answerable;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(AnswerableCardHandle expectedCard, AnswerableCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getQuestionType(), actualCard.getQuestionType());
        assertEquals(expectedCard.getQuestion(), actualCard.getQuestion());
        assertEquals(expectedCard.getDifficulty(), actualCard.getDifficulty());
        assertEquals(expectedCard.getCombinedAnswerList(), actualCard.getCombinedAnswerList());
        assertEquals(expectedCard.getCategories(), actualCard.getCategories());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedAnswerable}.
     */
    public static void assertCardDisplaysAnswerable(Answerable expectedAnswerable, AnswerableCardHandle actualCard) {
        assertEquals(expectedAnswerable.getQuestion().question, actualCard.getQuestion());
        assertEquals(expectedAnswerable.getDifficulty().difficulty, actualCard.getDifficulty());
        assertEquals(expectedAnswerable.getCombinedAnswerList().stream().map(a -> a.answer).sorted()
                .collect(Collectors.toList()), actualCard.getCombinedAnswerList());
        assertEquals(expectedAnswerable.getCategories().stream().map(cat -> cat.category).sorted()
                .collect(Collectors.toList()), actualCard.getCategories());
    }

    /**
     * Asserts that the list in {@code answerableListPanelHandle} displays the details of {@code answerables}
     * correctly and in the correct order.
     */
    public static void assertListMatching(AnswerableListPanelHandle answerableListPanelHandle,
              Answerable... answerables) {
        for (int i = 0; i < answerables.length; i++) {
            answerableListPanelHandle.navigateToCard(i);
            assertCardDisplaysAnswerable(answerables[i], answerableListPanelHandle.getAnswerableCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code answerableListPanelHandle} displays the details of {@code answerables}
     * correctly and in the correct order.
     */
    public static void assertListMatching(AnswerableListPanelHandle answerableListPanelHandle,
              List<Answerable> answerables) {
        assertListMatching(answerableListPanelHandle, answerables.toArray(new Answerable[0]));
    }

    /**
     * Asserts the size of the list in {@code answerableListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(AnswerableListPanelHandle answerableListPanelHandle, int size) {
        int numberOfAnswerables = answerableListPanelHandle.getListSize();
        assertEquals(size, numberOfAnswerables);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
