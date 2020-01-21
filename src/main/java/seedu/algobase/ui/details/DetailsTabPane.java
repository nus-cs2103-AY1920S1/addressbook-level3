package seedu.algobase.ui.details;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.logic.Logic;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.gui.ReadOnlyTabManager;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.ui.UiPart;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiActionExecutor;
import seedu.algobase.ui.action.UiActionType;

/**
 * Contains details about a specific model.
 */
public class DetailsTabPane extends UiPart<Region> {

    private static final String FXML = "DetailsTabPane.fxml";
    private static final Logger logger = LogsCenter.getLogger(DetailsTabPane.class);

    private final ReadOnlyAlgoBase algoBase;
    private final ReadOnlyTabManager readOnlyTabManager;
    private final UiActionExecutor uiActionExecutor;

    @FXML
    private TabPane tabsPlaceholder;

    public DetailsTabPane(Logic logic, UiActionExecutor uiActionExecutor) {
        super(FXML);
        requireAllNonNull(logic, uiActionExecutor);

        this.uiActionExecutor = uiActionExecutor;

        tabsPlaceholder.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);

        this.algoBase = logic.getAlgoBase();
        this.readOnlyTabManager = logic.getGuiState().getTabManager();

        addTabsToTabPane(readOnlyTabManager.getTabsDataList());
        if (!readOnlyTabManager.getTabsDataList().isEmpty()) {
            selectTab(readOnlyTabManager.getDetailsTabPaneIndex().getValue().intValue());
        }

        addListenerForTabChanges();
        addListenerForIndexChange(readOnlyTabManager.getDetailsTabPaneIndex());
        addListenerToTabPaneIndexChange();
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
            logger.info(
                "Selected Details Tab changed to index " + Index.fromZeroBased(newValue.intValue()).getOneBased()
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
                if (newValue.intValue() >= 0) {
                    logger.info(
                        "Details Tab with index value of "
                        + Index.fromZeroBased(newValue.intValue()).getOneBased()
                        + " clicked"
                    );
                    logger.info(
                        "Creating new UiActionDetails with type " + UiActionType.SWITCH_DETAILS_TAB
                            + " with index value of " + Index.fromZeroBased(newValue.intValue()).getOneBased()
                    );

                    uiActionExecutor.execute(new UiActionDetails(
                        UiActionType.SWITCH_DETAILS_TAB,
                        Index.fromZeroBased(newValue.intValue())
                    ));
                }
            }
        });
    }

    /**
     * Adds a listener to handle tab changes.
     */
    private void addListenerForTabChanges() {
        readOnlyTabManager.getTabsDataList().addListener(new ListChangeListener<TabData>() {
            @Override
            public void onChanged(Change<? extends TabData> change) {
                logger.info(
                    "TabData List modified with a new size of " + change.getList().size()
                );

                clearTabs();
                addTabsToTabPane(change.getList());
                tabsPlaceholder.requestLayout();
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
        ModelType modelType = tabData.getModelType();
        Id modelId = tabData.getModelId();
        try {
            switch (modelType) {
            case PROBLEM:
                Problem problem = algoBase.findProblemById(modelId);
                return Optional.of(
                    new DetailsTab(
                        problem.getName().fullName,
                        new ProblemDetails(problem, uiActionExecutor),
                        modelType,
                        modelId,
                        uiActionExecutor
                    )
                );
            case PLAN:
                Plan plan = algoBase.findPlanById(modelId);
                return Optional.of(
                    new DetailsTab(
                        plan.getPlanName().fullName,
                        new PlanDetails(plan, uiActionExecutor),
                        modelType,
                        modelId,
                        uiActionExecutor
                    )
                );
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
