package seedu.algobase.ui.details;

import java.util.function.Consumer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.Logic;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.model.gui.TabManager;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.ui.ProblemDetails;
import seedu.algobase.ui.UiPart;

/**
 * Contains details about a specific model.
 */
public class DetailsTabPane extends UiPart<Region> {

    private static final String FXML = "DetailsTabPane.fxml";

    @FXML
    private TabPane tabsPlaceholder;

    public DetailsTabPane(Logic logic) {
        super(FXML);
        TabManager tabManager = logic.getGuiState().getTabManager();
        addListenerForTabChanges(tabManager, logic);
        addListenerForIndexChange(logic.getGuiState().getTabManager().getDetailsTabPaneIndex());
        addListenerToTabPaneIndexChange(logic.getGuiState().getTabManager()::setDetailsTabPaneIndex);
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

    /**
     * Adds a listener to the tab pane that watches for an index change.
     *
     * @param detailsTabPaneIndex The observable index.
     */
    private void addListenerForIndexChange(ObservableIntegerValue detailsTabPaneIndex) {
        detailsTabPaneIndex.addListener((observable, oldValue, newValue) -> {
            selectTab((newValue.intValue()));
        });
    }

    /**
     * Adds an index change listener to the tab pane.
     *
     * @param indexChangeHandler A callback function for when the index of the tabPane changes.
     */
    private void addListenerToTabPaneIndexChange(Consumer<Index> indexChangeHandler) {
        this.tabsPlaceholder.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                indexChangeHandler.accept(Index.fromZeroBased(newValue.intValue()));
            }
        });
    }

    /**
     * Adds a listener to handle tab changes.
     *
     * @param tabManager Tab manager that processes list changes.
     * @param logic The logic to retrieve objects from.
     */
    private void addListenerForTabChanges(TabManager tabManager, Logic logic) {
        tabManager.getTabs().addListener(new ListChangeListener<TabData>() {
            @Override
            public void onChanged(Change<? extends TabData> change) {
                tabsPlaceholder.getTabs().clear();
                for (TabData tabData : change.getList()) {
                    Problem problem = logic.getProcessedProblemList().get(tabData.getModelIndex().getZeroBased());
                    addTabsToTabPane(new DetailsTab(problem.getName().fullName, new ProblemDetails(problem)));
                }
                selectLastTab();
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

    /**
     * Selects the last tab to be displayed.
     */
    public void selectLastTab() {
        selectTab(tabsPlaceholder.getTabs().size() - 1);
    }
}
