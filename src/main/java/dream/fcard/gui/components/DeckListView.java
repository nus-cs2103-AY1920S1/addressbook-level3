//@@author nattanyz
package dream.fcard.gui.components;

import dream.fcard.gui.GuiHelper;
import dream.fcard.model.Deck;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * UI component representing a list of flashcards in a deck.
 */
public class DeckListView extends ListView<FlashCardCell> {
    ObservableList<FlashCardCell> listOfCardsInDeck;

    /**
     * Creates a new instance of DeckListView representing the given deck.
     * @param deck The Deck of FlashCards to be displayed in the GUI.
     */
    public DeckListView(Deck deck) {
        super();

        listOfCardsInDeck = GuiHelper.toFlashCardCellList(deck);

        this.setItems(listOfCardsInDeck);
        // todo: need to setCellFactory? (but cells are already created...)
    }
}
