package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * UI component representing status bar in the application window.
 */
public class StatusBar extends TextBar {
    /**
     * Creates a new instance of StatusBar.
     */
    public StatusBar() {
        super();

        // reduce padding
        this.setPadding(new Insets(GuiSettings.getPadding() / 2));
    }

    /**
     * Sets the text to be displayed in the StatusBar.
     * @param textToBeDisplayed The text to be displayed.
     */
    @Override
    public void setText(String textToBeDisplayed) {
        // create label with appropriate text
        this.text = new Text(textToBeDisplayed);

        // style label
        this.text.setFont(GuiSettings.getStatusTextStyle());
        this.text.setFill(Color.web(GuiSettings.getPrimaryTextColour()));

        // remove any existing title in titleBar
        this.getChildren().clear();

        // add label to titleBar
        this.getChildren().add(text);
    }

    public void setErrorMessage(String errorMessage) {
        this.setText(errorMessage);
        this.text.setFill(Color.web(GuiSettings.getErrorTextColour()));
    }
}
