package seedu.algobase.ui.display;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.gui.ReadOnlyTabManager;
import seedu.algobase.ui.UiPart;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiActionExecutor;
import seedu.algobase.ui.action.UiActionType;


/**
 * Pane containing the different tabs.
 */
public class DisplayTabPane extends UiPart<Region> {

    private static final String FXML = "DisplayTabPane.fxml";
    private static final Logger logger = LogsCenter.getLogger(DisplayTabPane.class);

    private final UiActionExecutor uiActionExecutor;

    @FXML
    private TabPane tabsPlaceholder;

    public DisplayTabPane(ReadOnlyTabManager tabManager, UiActionExecutor uiActionExecutor, DisplayTab... displayTabs) {
        super(FXML);
        requireAllNonNull(tabManager, uiActionExecutor);

        this.uiActionExecutor = uiActionExecutor;

        addTabsToTabPane(displayTabs);
        addListenerToTabPaneIndexChange();
        selectTab(tabManager.getDisplayTabPaneIndex().getValue().intValue());

        addListenerForIndexChange(tabManager.getDisplayTabPaneIndex());
    }

    /**
     * Adds a list of display tabs to the tab pane.
     *
     * @param displayTabs List of tabs to be displayed.
     */
    private void addTabsToTabPane(DisplayTab... displayTabs) {
        for (DisplayTab displayTab: displayTabs) {
            this.tabsPlaceholder.getTabs().add(displayTab.getTab());
        }
    }

    /**
     * Adds a listener to the tab pane that watches for an index change.
     *
     * @param displayTabPaneIndex The observable index.
     */
    private void addListenerForIndexChange(ObservableIntegerValue displayTabPaneIndex) {
        displayTabPaneIndex.addListener((observable, oldValue, newValue) -> {
            logger.info(
                "Selected Display Tab changed to index " + Index.fromZeroBased(newValue.intValue()).getOneBased()
            );

            selectTab((newValue.intValue()));
        });
    }

    /**
     * Adds an index change listener to the tab pane.
     */
    private void addListenerToTabPaneIndexChange() {
        this.tabsPlaceholder.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                logger.info(
                    "Creating new UiActionDetails with type " + UiActionType.SWITCH_DISPLAY_TAB
                        + " with index value of " + Index.fromZeroBased(newValue.intValue()).getOneBased()
                );

                uiActionExecutor.execute(new UiActionDetails(
                    UiActionType.SWITCH_DISPLAY_TAB,
                    Index.fromZeroBased(newValue.intValue())
                ));
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
