package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * UI component representing title bar in the application window.
 */
public class TitleBar extends HBox implements UiComponent<HBox> {
    private Text title;

    /**
     * Creates a new instance of TitleBar. Called when MainWindow initialises its components.
     */
    public TitleBar() {
        super();

        // set up height
        this.setPrefHeight(Region.USE_COMPUTED_SIZE);

        // set up padding
        this.setPadding(new Insets(GuiSettings.getPadding()));

        // set background colour
        this.setStyle("-fx-background-color:" + GuiSettings.getSecondaryUiColour() + ";");
    }

    /**
     * Sets the title text to be displayed in the TitleBar.
     * @param titleText The title text to be displayed.
     */
    public void setTitle(String titleText) {
        // create label with appropriate text
        title = new Text(titleText);

        // style label
        title.setFont(GuiSettings.getTitleTextStyle());
        title.setFill(Color.web(GuiSettings.getPrimaryTextColour()));

        // todo: abstract into UIComponent.replace(Node newNode) method
        // remove any existing title in titleBar
        this.getChildren().clear();

        // add label to titleBar
        this.getChildren().add(title);
    }
}
