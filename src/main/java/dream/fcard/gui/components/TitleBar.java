//@@author nattanyz
package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * UI component representing title bar in the application window.
 */
public class TitleBar extends TextBar {
    /**
     * Creates a new instance of TitleBar.
     */
    public TitleBar() {
        super();
    }

    /**
     * Sets the text to be displayed in the TitleBar.
     * @param textToBeDisplayed The text to be displayed.
     */
    @Override
    public void setText(String textToBeDisplayed) {
        // create label with appropriate text
        this.text = new Text(textToBeDisplayed);

        // style label
        this.text.setFont(GuiSettings.getTitleTextStyle());
        this.text.setFill(Color.web(GuiSettings.getPrimaryTextColour()));

        // todo: abstract into UIComponent.replace(Node newNode) method
        // remove any existing title in titleBar
        this.getChildren().clear();

        // add label to titleBar
        this.getChildren().add(text);
    }
}
