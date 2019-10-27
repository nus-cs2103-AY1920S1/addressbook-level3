package seedu.algobase.ui.details;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.logic.Logic;
import seedu.algobase.model.Id;
import seedu.algobase.model.ReadOnlyAlgoBase;
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

    private final ReadOnlyAlgoBase algoBase;
    private final TabManager tabManager;

    @FXML
    private TabPane tabsPlaceholder;

    public DetailsTabPane(Logic logic) {
        super(FXML);
        this.algoBase = logic.getAlgoBase();
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
            .forEach((tabData) -> tabData.ifPresent(this::addTabToTabPane));
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
        tabManager.getDetailsTabs().addListener(new ListChangeListener<TabData>() {
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
    private Optional<DetailsTab> convertTabDataToDetailsTab(TabData tabData) throws IllegalArgumentException {
        Id modelId = tabData.getModelId();
        try {
            switch (tabData.getModelType()) {
            case PROBLEM:
                Problem problem = algoBase.findProblemById(modelId);
                return Optional.of(new DetailsTab(problem.getName().fullName, new ProblemDetails(problem)));
            case PLAN:
                Plan plan = algoBase.findPlanById(modelId);
                return Optional.of(new DetailsTab(plan.getPlanName().fullName, new PlanDetails(plan)));
            case TAG:
                Tag tag = algoBase.findTagById(modelId);
                return Optional.of(new DetailsTab(tag.getName()));
            default:
                throw new IllegalArgumentException("Model does not exist");
            }
        } catch (IllegalValueException e) {
            return Optional.empty();
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
