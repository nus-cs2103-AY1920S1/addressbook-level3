//@@author nattanyz
package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * UI component representing an individual FlashCard to be displayed in the application.
 */
public class FlashCardDisplay extends HBox implements UiComponent<HBox> {

    private static double maxWidth = GuiSettings.getMinWidth() - 2 * GuiSettings.getPadding();
    private static double textMaxWidth = GuiSettings.getMinWidth() - 4 * GuiSettings.getPadding();

    private Text flashCardText;

    /**
     * Creates a new instance of FlashCardDisplay with the given text.
     * @param text Text to be displayed in the FlashCardDisplay.
     */
    public FlashCardDisplay(String text) {
        super();

        // set up dimensions
        this.setPrefHeight(180);
        this.setMaxWidth(maxWidth);
        this.setPadding(new Insets(GuiSettings.getPadding()));

        // set up background colour and radius
        this.setStyle("-fx-background-color:" + GuiSettings.getPrimaryUiColour() + ";");
        //this.setStyle("-fx-background-radius:" + GuiSettings.getRadius() +";");

        flashCardText = new Text(text);

        // style flashCardText
        flashCardText.setFont(GuiSettings.getFlashCardTextStyle());
        flashCardText.setFill(Color.web(GuiSettings.getPrimaryTextColour()));
        flashCardText.setWrappingWidth(this.getWidth());

        TextFlow textFlow = new TextFlow(flashCardText);
        textFlow.setMaxWidth(textMaxWidth);

        // add flashCardText to card
        this.getChildren().add(textFlow);
    }

    // todo: add constructor with two arguments (front and back)
}
