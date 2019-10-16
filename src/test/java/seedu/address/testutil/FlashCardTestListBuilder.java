package seedu.address.testutil;

import java.util.LinkedList;
import java.util.List;

import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;
import seedu.address.model.util.SampleDataUtil;

//@@author keiteo
/**
 * Creates an ArrayList of FlashCards to facilitate FlashCardTestMode tests.
 */
public class FlashCardTestListBuilder {

    private List<FlashCard> testList = new LinkedList<>();
    private final String[] tagList = {"cs2100", "cs1101s", "cs2040"};

    /**
     * Returns an ArrayList containing 10000 simple flashcards.
     */
    public List<FlashCard> build() {
        final int constant = 10;
        for (int i = 0; i < 10000; i++) {
            String question = i + " + " + constant;
            String answer = Integer.toString(i + constant);
            String tag = tagList[i % 3];
            testList.add(new FlashCard(new Question(question), new Answer(answer), new Rating("good"),
                    SampleDataUtil.getCategorySet(tag)));
        }
        return testList;
    }
}
