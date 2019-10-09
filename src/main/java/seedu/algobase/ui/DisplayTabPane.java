package seedu.algobase.ui;

import javafx.beans.value.ObservableIntegerValue;
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

    public DisplayTabPane(ObservableIntegerValue displayTabPaneIndex, DisplayTab... displayTabs) {
        super(FXML);
        displayTabPaneIndex.addListener((observable, oldValue, newValue) -> {
            selectTab((int) newValue);
        });
        addTabsToTabPane(displayTabs);
    }

    private void addTabsToTabPane(DisplayTab... displayTabs) {
        for (DisplayTab displayTab: displayTabs) {
            tabsPlaceholder.getTabs().add(displayTab.getTab());
        }
    }

    /**
     * Selects the tab to be displayed.
     *
     * @param index the index of the tab in the tab pane to be selected.
     */
    public void selectTab(int index) {
        tabsPlaceholder.getSelectionModel().select(index);
    }
}
