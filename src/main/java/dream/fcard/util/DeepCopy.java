package dream.fcard.util;

import java.util.ArrayList;

import dream.fcard.model.TestCase;
import dream.fcard.model.cards.FlashCard;

/**
 * Methods for cloning our objects.
 */
public class DeepCopy {
    /**
     * Used to duplicate a deck of cards.
     * @param toClone the array list of cards to be cloned
     * @return an array list of a copy of each card.
     */
    public static ArrayList<FlashCard> duplicateCards(ArrayList<FlashCard> toClone) {
        ArrayList<FlashCard> newList = new ArrayList<>();
        toClone.forEach(card -> {
            FlashCard newCard = card.duplicate();
            newList.add(newCard);
        });
        return newList;
    }

    /**
     * An array to duplicate the test cases.
     * @param toClone the array list of test cases.
     * @return an array of a replica of test cases.
     */
    public static ArrayList<TestCase> duplicateTestCases(ArrayList<TestCase> toClone) {
        ArrayList<TestCase> newList = new ArrayList<>();
        toClone.forEach(testCase -> newList.add(testCase.duplicate()));
        return newList;
    }
}
