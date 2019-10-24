package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TitleBar extends HBox {
    private Text title;

    public TitleBar() {
        super();

        // set up height
        this.setPrefHeight(Region.USE_COMPUTED_SIZE);

        // set up padding
        this.setPadding(new Insets(GuiSettings.getPadding()));

        // set background colour
        this.setStyle("-fx-background-color:" + GuiSettings.getSecondaryUiColour() + ";");
    }

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
