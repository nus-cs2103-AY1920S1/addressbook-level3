package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.LogsCenter;
import dukecooks.model.recipe.components.Recipe;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of recipes.
 */
public class RecipeListPanel extends UiPart<Region> {
    private static final String FXML = "RecipeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecipeListPanel.class);

    @FXML
    private FlowPane recipeListView;

    @FXML
    private ListView<Recipe> recipeIndexListView;

    public RecipeListPanel(ObservableList<Recipe> recipeList) {
        super(FXML);
        initializeFlowPaneView(recipeList);
        initializeIndexList(recipeList);
    }

    /**
     * Initialises FlowPane Config.
     * Gives the overview of health records recorded by user
     */
    void initializeFlowPaneView(ObservableList<Recipe> recipeList) {
        recipeListView.setHgap(5);
        recipeListView.setVgap(5);
        recipeListView.setPadding(new Insets(10, 10, 10, 10));

        // Creates a RecipeCard for each recipe and adds to FlowPane
        int i = 0;
        for (Recipe recipe : recipeList) {
            recipeListView.getChildren().add(new RecipeCard(recipe, i + 1).getRoot());
            i++;
        }
        //add listener for new recipe book changes
        recipeList.addListener((ListChangeListener<Recipe>) c -> {
            recipeListView.getChildren().clear();
            int x = 0;
            for (Recipe recipe: recipeList) {
                recipeListView.getChildren().add(new RecipeCard(recipe, x + 1).getRoot());
                x++;
            }
        });
    }

    /**
     * Initialises IndexList Config.
     * Shows the type of health records that can be recorded
     */
    void initializeIndexList(ObservableList<Recipe> recipeList) {
        recipeIndexListView.setItems(recipeList);
        recipeIndexListView.setCellFactory(listView -> new RecipeIndexListViewCell());
    }

    /**
     * Display inner components within Health Record Panel.
     * Make use of boolean variables to decide which components to show/hide.
     */
    private void showPanels(boolean showIndexView, boolean showCardView) {
        recipeIndexListView.setVisible(showIndexView);
        recipeIndexListView.setManaged(showIndexView);
        recipeListView.setVisible(showCardView);
        recipeListView.setManaged(showCardView);
    }

    /**
     * Hide all inner components within Health Record Panel.
     */
    private void hidePanels() {
        recipeIndexListView.setVisible(false);
        recipeListView.setVisible(false);
    }

    /**
     * Switch view within Recipe Book Panel.
     */
    @FXML
    void handleSwitch(String type) {
        switch (type) {
        case "all":
            showPanels(true, true);
            break;
        default:
            throw new AssertionError("Something's Wrong! Invalid recipe book!");
        }
    }

    /**
     * Switch to meal plan page.
     */
    @FXML
    private void switchMealPlan() {
        Event event;
        event = Event.getInstance();
        event.set("mealPlan", "all");
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Recipe} using a {@code RecipeIndexCard}.
     */
    class RecipeIndexListViewCell extends ListCell<Recipe> {
        @Override
        protected void updateItem(Recipe recipe, boolean empty) {
            super.updateItem(recipe, empty);

            if (empty || recipe == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RecipeIndexCard(recipe, getIndex() + 1).getRoot());
            }
        }
    }

}
