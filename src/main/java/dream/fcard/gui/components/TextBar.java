//@@author nattanyz
package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * Class which TitleBar and StatusBar inherit from.
 */
public class TextBar extends HBox {
    protected Text text;

    /**
     * Creates a new instance of TitleBar. Called when MainWindow initialises its components.
     */
    public TextBar() {
        super();

        // set up height
        this.setPrefHeight(Region.USE_COMPUTED_SIZE);

        // set up padding
        this.setPadding(new Insets(GuiSettings.getPadding()));

        // set background colour
        this.setStyle("-fx-background-color:" + GuiSettings.getSecondaryUiColour() + ";");
    }

    /**
     * Sets the text to be displayed in the TextBar.
     * @param textToBeDisplayed The text to be displayed.
     */
    public void setText(String textToBeDisplayed) {
        // create label with appropriate text
        this.text = new Text(textToBeDisplayed);

        // style label (dependent on subclass)

        // remove any existing title in titleBar
        this.getChildren().clear();

        // add label to titleBar
        this.getChildren().add(text);
    }
}
