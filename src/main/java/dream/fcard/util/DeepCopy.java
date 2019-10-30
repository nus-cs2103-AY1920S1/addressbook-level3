package dream.fcard.util;

import java.util.ArrayList;

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
        toClone.forEach(card -> newList.add(card.duplicate()));
        return newList;
    }

}
