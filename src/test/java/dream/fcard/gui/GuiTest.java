//@@ author nattanyz
package dream.fcard.gui;

import java.util.ArrayList;

import dream.fcard.model.Deck;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.FrontBackCard;

/** Tests for Gui. */
public class GuiTest {
    /** Verify that flashcards with long text scroll in the expected manner. */
    public static void renderFront_longText_showScrollBar() {
        String longStringForTesting = "Lorem ipsum dolor sit amet, "
            + "consectetur adipscing elit. Aliquam lacinia, nunc quis dictum consectetur, "
            + "erat nulla molestie turpis, quis finibus justo ipsum a justo. Cras quis ullamcorper "
            + "nulla. Duis nec volutpat nibh. Praesent ut justo vestibulum, lacinia tortor mattis, "
            + "euismod eros. Maecenas viverra erat ac eros consequat feugiat. Praesent vehicula non "
            + "turpis tempor elementum. Suspendisse ac lacus congue, blandit nisl eget, suscipit "
            + "eros. Donec aliquet, tellus eu consectetur ornare, odio ipsum lacinia ipsum, id "
            + "ullamcorper magna nibh quis nulla. Nulla sagittis, quam vel condimentum commodo, "
            + "arcu ligula lobortis erat, non consequat tortor mi non libero. Etiam orci purus, "
            + "maximus sed suscipit ut, consectetur sed arcu.";
        FrontBackCard flashCardForTesting = new FrontBackCard(longStringForTesting,
            "Praesent ut est justo. Cras urna risus, ultricies posuere pharetra in, "
                + "fringilla ac dolor. Donec a porttitor tellus, vitae ullamcorper risus.");
        //Gui.renderFront(flashCardForTesting);
    }

    /** Verify that a node can be appended to the scrollable pane. */
    public static void displayInScrollablePane() {

    }

    /** Verify that a node can replace currently existing nodes in the scrollable pane. */
    public static void replaceInScrollablePane() {

    }

    /** Verify that the GUI can display an error message. */
    //public static void showError() {
    //    Gui.showError("Command not recognised :(");
    //}

    /** Verify that the GUI can display a deck of cards. */
    public static void displayDeck_validDeck_showDeckInGui() {
        ArrayList<FlashCard> cards = new ArrayList<>();
        String frontString = "Lorem ipsum dolor sit amet, "
            + "consectetur adipscing elit. Aliquam lacinia, nunc quis dictum consectetur, "
            + "erat nulla molestie turpis, quis finibus justo ipsum a justo.";
        String backString = "Duis nec volutpat nibh. Praesent ut justo vestibulum, lacinia tortor "
            + "mattis, euismod eros. Maecenas viverra erat ac eros consequat feugiat.";
        cards.add(new FrontBackCard(frontString, backString));
        cards.add(new FrontBackCard(frontString, backString));
        cards.add(new FrontBackCard(frontString, backString));
        cards.add(new FrontBackCard(frontString, backString));
        cards.add(new FrontBackCard(frontString, backString));
        cards.add(new FrontBackCard(frontString, backString));
        cards.add(new FrontBackCard(frontString, backString));
        cards.add(new FrontBackCard(frontString, backString));
        Deck deck = new Deck(cards, "Test");
        //Gui.renderDeck(deck);
    }
}
