package seedu.algobase.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;

/**
 * Contains details about a specific model.
 */
public class DetailsTabPane extends UiPart<Region> {

    private static final String FXML = "DetailsTabPane.fxml";

    @FXML
    private TabPane tabsPlaceholder;

    public DetailsTabPane() {
        super(FXML);
        addTabsToTabPane(new DetailsTab("Sequences"));
        addTabsToTabPane(new DetailsTab("Two Sum"));
        addTabsToTabPane(new DetailsTab("Second Highest Salary"));
    }

    /**
     * Adds a list of details tabs to the tab pane.
     *
     * @param detailsTabs List of tabs to be displayed.
     */
    private void addTabsToTabPane(DetailsTab... detailsTabs) {
        for (DetailsTab detailsTab: detailsTabs) {
            this.tabsPlaceholder.getTabs().add(detailsTab.getTab());
        }
    }
}
