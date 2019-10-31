package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.LogsCenter;
import dukecooks.model.mealplan.components.MealPlan;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of meal plans.
 */
public class MealPlanListPanel extends UiPart<Region> {
    private static final String FXML = "MealPlanListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MealPlanListPanel.class);

    @FXML
    private FlowPane mealPlanListView;

    @FXML
    private ListView<MealPlan> mealPlanIndexListView;

    public MealPlanListPanel(ObservableList<MealPlan> mealPlanList) {
        super(FXML);
        initializeFlowPaneView(mealPlanList);
        initializeIndexList(mealPlanList);
    }

    /**
     * Initialises FlowPane Config.
     * Gives the overview of health records recorded by user
     */
    void initializeFlowPaneView(ObservableList<MealPlan> mealPlanList) {
        mealPlanListView.setHgap(10);
        mealPlanListView.setVgap(10);
        mealPlanListView.setPadding(new Insets(10, 10, 10, 10));

        // Creates a MealPlanCard for each meal plan and adds to FlowPane
        int i = 0;
        for (MealPlan mealPlan : mealPlanList) {
            mealPlanListView.getChildren().add(new MealPlanCard(mealPlan, i + 1).getRoot());
            i++;
        }
        //add listener for new meal plan book changes
        mealPlanList.addListener((ListChangeListener<MealPlan>) c -> {
            mealPlanListView.getChildren().clear();
            int x = 0;
            for (MealPlan mealPlan: mealPlanList) {
                mealPlanListView.getChildren().add(new MealPlanCard(mealPlan, x + 1).getRoot());
                x++;
            }
        });
    }

    /**
     * Initialises IndexList Config.
     * Shows the type of health records that can be recorded
     */
    void initializeIndexList(ObservableList<MealPlan> mealPlanList) {
        mealPlanIndexListView.setItems(mealPlanList);
        mealPlanIndexListView.setCellFactory(listView -> new MealPlanIndexListViewCell());
    }

    /**
     * Display inner components within Health Record Panel.
     * Make use of boolean variables to decide which components to show/hide.
     */
    private void showPanels(boolean showIndexView, boolean showCardView) {
        mealPlanIndexListView.setVisible(showIndexView);
        mealPlanIndexListView.setManaged(showIndexView);
        mealPlanListView.setVisible(showCardView);
        mealPlanListView.setManaged(showCardView);
    }

    /**
     * Hide all inner components within Health Record Panel.
     */
    private void hidePanels() {
        mealPlanIndexListView.setVisible(false);
        mealPlanListView.setVisible(false);
    }

    /**
     * Switch view within MealPlan Book Panel.
     */
    @FXML
    void handleSwitch(String type) {
        switch (type) {
        case "all":
            showPanels(true, true);
            break;
        default:
            throw new AssertionError("Something's Wrong! Invalid meal plan book!");
        }
    }

    /**
     * Switch to recipe page.
     */
    @FXML
    private void switchRecipe() {
        Event event;
        event = Event.getInstance();
        event.set("recipe", "all");
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code MealPlan} using a {@code MealPlanIndexCard}.
     */
    class MealPlanIndexListViewCell extends ListCell<MealPlan> {
        @Override
        protected void updateItem(MealPlan mealPlan, boolean empty) {
            super.updateItem(mealPlan, empty);

            if (empty || mealPlan == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MealPlanIndexCard(mealPlan, getIndex() + 1).getRoot());
            }
        }
    }

}
