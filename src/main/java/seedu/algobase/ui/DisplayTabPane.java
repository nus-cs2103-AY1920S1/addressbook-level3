package seedu.algobase.ui;

import java.util.function.Consumer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.GuiState;

/**
 * Pane containing the different tabs.
 */
public class DisplayTabPane extends UiPart<Region> {

    private static final String FXML = "DisplayTabPane.fxml";

    @FXML
    private TabPane tabsPlaceholder;

    @FXML
    private StackPane problemListPanelPlaceholder;

    public DisplayTabPane(GuiState guiState, DisplayTab... displayTabs) {
        super(FXML);
        addTabsToTabPane(displayTabs);
        addListenerForIndex(guiState.getDisplayTabPaneIndex());
        addListenerToTabPane(guiState::setDisplayTabPaneIndex);
    }

    private void addTabsToTabPane(DisplayTab... displayTabs) {
        for (DisplayTab displayTab: displayTabs) {
            this.tabsPlaceholder.getTabs().add(displayTab.getTab());
        }
    }

    private void addListenerForIndex(ObservableIntegerValue displayTabPaneIndex) {
        displayTabPaneIndex.addListener((observable, oldValue, newValue) -> {
            selectTab((int) newValue);
        });
    }

    /**
     * Adds an index change listener to the tab pane.
     *
     * @param indexChangeHandler A callback function for when the index of the tabPane changes.
     */
    private void addListenerToTabPane(Consumer<Index> indexChangeHandler) {
        this.tabsPlaceholder.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                indexChangeHandler.accept(Index.fromZeroBased(newValue.intValue()));
            }
        });
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
