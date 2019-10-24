package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CommandTextFieldPlaceholder extends VBox implements UiComponent<VBox> {
    public CommandTextFieldPlaceholder() {
        super();

        // set up height
        this.setPrefHeight(Region.USE_COMPUTED_SIZE);

        // set up padding
        this.setPadding(new Insets(GuiSettings.getPadding()));

        // set background colour
        this.setStyle("-fx-background-color:" + GuiSettings.getTertiaryUiColour() + ";"); // temporary
        // todo: abstract into UI component setBackgroundColour(String colour) method
        //this.setStyle("-fx-background-color:#FFFFFF;");
    }

    public void add(Node node) {
        this.getChildren().add(node);
    }
}
