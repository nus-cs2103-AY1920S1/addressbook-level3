package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import dream.fcard.model.cards.FlashCard;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * UI component representing the row taken up by an individual flashcard when displayed in a list.
 */
public class FlashCardCell extends HBox {

    private Text cardIndex;
    private FlashCardDisplay flashCardDisplay;

    /**
     * Creates a new instance of FlashCardCell to display the given cell.
     * @param flashCard
     */
    public FlashCardCell(FlashCard flashCard, int index) {
        // create a HBox with the desired spacing
        super(GuiSettings.getSpacing());

        this.setPadding(new Insets(GuiSettings.getPadding()));

        // todo: show the index to the left of the card, rather than above it
        cardIndex = new Text(String.valueOf(index));

        // temporarily, show only the front of the flashcard
        // todo: show both front and back of flashcard
        flashCardDisplay = new FlashCardDisplay(flashCard.getFront());

        // let flashCardDisplay take up the maximum horizontal space in the row
        //HBox.setHgrow(flashCardDisplay, Priority.ALWAYS);

        // add both elements to the FlashCardCell
        this.getChildren().addAll(cardIndex, flashCardDisplay);
    }

}
