//@@author nattanyz
package dream.fcard.gui.components;

import dream.fcard.gui.GuiHelper;
import dream.fcard.gui.GuiSettings;
import dream.fcard.model.Deck;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

/**
 * UI component representing a list of flashcards in a deck.
 */
public class DeckListView extends VBox {
    // todo: attempt to implement using ListView
    private ObservableList<FlashCardCell> listOfCardsInDeck;

    /**
     * Creates a new instance of DeckListView representing the given deck.
     * @param deck The Deck of FlashCards to be displayed in the GUI.
     */
    public DeckListView(Deck deck) {
        super(GuiSettings.getSpacing());

        listOfCardsInDeck = GuiHelper.toFlashCardCellList(deck);

        this.getChildren().setAll(listOfCardsInDeck);
        // todo: need to setCellFactory? (but cells are already created...)
    }
}
