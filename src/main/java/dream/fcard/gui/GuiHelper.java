//@@author nattanyz
package dream.fcard.gui;

import dream.fcard.gui.components.FlashCardDisplay;
import dream.fcard.model.cards.FlashCard;

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
        String cardText = flashCard.getFront();

        // generate FlashCardDisplay containing text to be rendered
        return new FlashCardDisplay(cardText);
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
}
