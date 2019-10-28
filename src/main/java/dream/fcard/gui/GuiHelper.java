//@@author nattanyz
package dream.fcard.gui;

import java.util.ArrayList;

import dream.fcard.gui.components.DeckListView;
import dream.fcard.gui.components.FlashCardCell;
import dream.fcard.gui.components.FlashCardDisplay;
import dream.fcard.model.Deck;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.MultipleChoiceCard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Handles logic for displaying objects in the GUI.
 */
public class GuiHelper {

    /**
     * Generates a FlashCardDisplay object corresponding to the front of the flashCard to be
     * displayed in the GUI.
     * @param flashCard The FlashCard to be rendered.
     * @return The FlashCardDisplay object to be displayed in the GUI.
     */
    public static FlashCardDisplay renderFront(FlashCard flashCard) {
        // get text to be rendered
        StringBuilder cardText = new StringBuilder(flashCard.getFront());

        // if flashCard is a MultipleChoiceCard, show its choices too
        if (flashCard.hasChoices()) {
            MultipleChoiceCard multipleChoiceCard = (MultipleChoiceCard) flashCard;
            ArrayList<String> listOfChoices = multipleChoiceCard.getListOfChoices();
            int i = 1;
            for (String choice : listOfChoices) {
                cardText.append(i).append(" ").append(choice);
            }
        }

        // generate FlashCardDisplay containing text to be rendered
        return new FlashCardDisplay(cardText.toString());
    }

    /**
     * Generates a FlashCardDisplay object corresponding to the back of the flashCard to be
     * displayed in the GUI.
     * @param flashCard The FlashCard to be rendered.
     * @return The FlashCardDisplay object to be displayed in the GUI.
     */
    public static FlashCardDisplay renderBack(FlashCard flashCard) {
        // get text to be rendered
        String cardText = flashCard.getBack();

        // generate FlashCardDisplay containing text to be rendered
        return new FlashCardDisplay(cardText);
    }

    /**
     * Generates a DeckListView object containing FlashCardCells which correspond to each
     * FlashCard in the deck, to be displayed in the GUI.
     * @param deck The Deck to be rendered.
     * @return The DeckListView object to be displayed in the GUI.
     */
    public static DeckListView renderDeck(Deck deck) {
        return new DeckListView(deck);
    }

    /**
     * Generates an ObservableList of FlashCardCells, given a Deck of FlashCards.
     * @param deck The Deck to generate an ObservableList from.
     * @return An ObservableList of FlashCardCells representing each flashcard in the deck.
     */
    public static ObservableList<FlashCardCell> toFlashCardCellList(Deck deck) {
        ObservableList<FlashCardCell> list = FXCollections.observableArrayList();
        int i = 1; // index of the card in the list
        for (FlashCard card : deck.getCards()) {
            FlashCardCell cell = toFlashCardCell(card, i);
            list.add(cell);
            i++;
        }
        return list;
    }

    /**
     * Generates a FlashCardCell object representing the given FlashCard, to be displayed
     * in a list in the GUI.
     * @param flashCard The FlashCard to be displayed.
     * @return The FlashCardCell object to be displayed in the GUI.
     */
    private static FlashCardCell toFlashCardCell(FlashCard flashCard, int index) {
        return new FlashCardCell(flashCard, index);
    }
}
