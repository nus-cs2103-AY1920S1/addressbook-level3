package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ScrollablePane extends ScrollPane implements UiComponent<ScrollPane> {
    private VBox paneContents;

    public ScrollablePane() {
        super();
        VBox.setVgrow(this, Priority.ALWAYS);

        // set scrollable pane background colour, just in case
        this.setStyle("-fx-background-color:" + GuiSettings.getBackgroundColour() + ";");

        // create container for all contents in the scrollable window
        this.paneContents = new VBox();
        this.paneContents.setPadding(new Insets(GuiSettings.getPadding()));
        this.paneContents.setStyle("-fx-background-color:" + GuiSettings.getBackgroundColour() + ";");

        // add content container to self
        this.setContent(this.paneContents);

        // set fit to height and width
        this.setFitToHeight(true);
        this.setFitToWidth(true);
    }

    public void add(Node node) {
        // remove anything currently in the viewport
        this.paneContents.getChildren().clear();
        // add the node to the scrollable pane
        this.paneContents.getChildren().add(node);
    }
}
