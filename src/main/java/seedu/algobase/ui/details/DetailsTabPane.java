package seedu.algobase.ui.details;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.Logic;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.model.gui.TabManager;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.ui.UiPart;

/**
 * Contains details about a specific model.
 */
public class DetailsTabPane extends UiPart<Region> {

    private static final String FXML = "DetailsTabPane.fxml";

    private final ObservableList<Problem> problems;
    private final ObservableList<Plan> plans;
    private final ObservableList<Tag> tags;
    private final TabManager tabManager;

    @FXML
    private TabPane tabsPlaceholder;

    public DetailsTabPane(Logic logic) {
        super(FXML);
        this.problems = logic.getProcessedProblemList();
        this.plans = logic.getProcessedPlanList();
        this.tags = logic.getFilteredTagList();
        this.tabManager = logic.getGuiState().getTabManager();

        addTabsToTabPane(tabManager.getTabsDataList());

        addListenerForTabChanges();
        addListenerForIndexChange(tabManager.getDetailsTabPaneIndex());
        addListenerToTabPaneIndexChange(tabManager::setDetailsTabPaneIndex);
    }

    /**
     * Adds a list of TabData to the tab pane.
     *
     * @param tabsData List of tabs to be displayed.
     */
    private void addTabsToTabPane(List<? extends TabData> tabsData) {
        tabsData.stream()
            .map(this::convertTabDataToDetailsTab)
            .collect(Collectors.toList())
            .forEach(this::addTabToTabPane);
    }

    /**
     * Adds a list of details tabs to the tab pane.
     *
     * @param detailsTab List of tabs to be displayed.
     */
    private void addTabToTabPane(DetailsTab detailsTab) {
        this.tabsPlaceholder.getTabs().add(detailsTab.getTab());
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
     */
    private void addListenerForTabChanges() {
        tabManager.getTabs().addListener(new ListChangeListener<TabData>() {
            @Override
            public void onChanged(Change<? extends TabData> change) {
                clearTabs();
                addTabsToTabPane(change.getList());
                selectLastTab();
            }
        });
    }

    private void clearTabs() {
        tabsPlaceholder.getTabs().clear();
    }

    /**
     * Converts a {code: TabData} object into a {code: DetailsTab} object.
     *
     * @param tabData The TabData to be converted.
     */
    private DetailsTab convertTabDataToDetailsTab(TabData tabData) throws IllegalArgumentException {
        int zeroBasedIndex = tabData.getModelIndex().getZeroBased();
        switch (tabData.getModelType()) {
        case PROBLEM:
            Problem problem = problems.get(zeroBasedIndex);
            return new DetailsTab(problem.getName().fullName, new ProblemDetails(problem));
        case PLAN:
            Plan plan = plans.get(zeroBasedIndex);
            return new DetailsTab(plan.getPlanName().fullName);
        case TAG:
            Tag tag = tags.get(zeroBasedIndex);
            return new DetailsTab(tag.getName());
        default:
            throw new IllegalArgumentException("Model does not exist");
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

    /**
     * Selects the last tab to be displayed.
     */
    public void selectLastTab() {
        selectTab(tabsPlaceholder.getTabs().size() - 1);
    }
}
