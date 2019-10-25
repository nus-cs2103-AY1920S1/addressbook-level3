//@@author nattanyz
package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * UI component containing all content in the application window, excluding the title bar and
 * command text field.
 */
public class ScrollablePane extends ScrollPane implements UiComponent<ScrollPane> {
    private VBox paneContents;

    /**
     * Creates a new instance of ScrollablePane. Called when MainWindow initialises its components.
     */
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

    /**
     * Adds a given Node to the scrollable pane.
     * Expected behaviour: remove and replace anything currently in the scrollable pane.
     * @param node Node to be added to the scrollable pane
     */
    public void add(Node node) {
        // remove anything currently in the viewport. todo: rethink this behaviour
        this.paneContents.getChildren().clear();
        // add the node to the scrollable pane
        this.paneContents.getChildren().add(node);
    }
}
