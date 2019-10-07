package seedu.algobase.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Pane containing the different tabs.
 */
public class DisplayTabPane extends UiPart<Region> {

    private static final String FXML = "DisplayTabPane.fxml";

    @FXML
    private TabPane tabsPlaceholder;

    @FXML
    private StackPane problemListPanelPlaceholder;

    public DisplayTabPane(DisplayTab... displayTabs) {
        super(FXML);
        for (DisplayTab displayTab: displayTabs) {
            tabsPlaceholder.getTabs().add(displayTab.getTab());
        }
    }
}
