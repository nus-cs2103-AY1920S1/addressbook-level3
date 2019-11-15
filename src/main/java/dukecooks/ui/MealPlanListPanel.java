package dukecooks.ui;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.LogsCenter;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeName;
import dukecooks.model.recipe.exceptions.RecipeNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Panel containing the list of meal plans.
 */
public class MealPlanListPanel extends UiPart<Region> {
    private static final String FXML = "MealPlanListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MealPlanListPanel.class);

    @FXML
    private ScrollPane mealPlanListPanel;

    @FXML
    private VBox mealPlanViewPanel;

    @FXML
    private FlowPane mealPlanListView;

    @FXML
    private HBox mealPlanViewView;

    @FXML
    private Label mealPlanHeader;

    @FXML
    private Label mealPlanNutrition;

    @FXML
    private ScrollPane mealPlanDay1Panel;

    @FXML
    private FlowPane mealPlanDay1ListView;

    @FXML
    private ScrollPane mealPlanDay2Panel;

    @FXML
    private FlowPane mealPlanDay2ListView;

    @FXML
    private ScrollPane mealPlanDay3Panel;

    @FXML
    private FlowPane mealPlanDay3ListView;

    @FXML
    private ScrollPane mealPlanDay4Panel;

    @FXML
    private FlowPane mealPlanDay4ListView;

    @FXML
    private ScrollPane mealPlanDay5Panel;

    @FXML
    private FlowPane mealPlanDay5ListView;

    @FXML
    private ScrollPane mealPlanDay6Panel;

    @FXML
    private FlowPane mealPlanDay6ListView;

    @FXML
    private ScrollPane mealPlanDay7Panel;

    @FXML
    private FlowPane mealPlanDay7ListView;

    @FXML
    private ListView<MealPlan> mealPlanIndexListView;

    private ObservableList<MealPlan> mealPlanList;
    private ObservableList<Recipe> recipeList;

    public MealPlanListPanel(ObservableList<MealPlan> mealPlanList, ObservableList<Recipe> recipeList) {
        super(FXML);
        initializeFlowPaneView(mealPlanList);
        initializeIndexList(mealPlanList);
        this.mealPlanList = mealPlanList;
        this.recipeList = recipeList;
    }

    /**
     * Initialises FlowPane Config.
     * Gives the overview of meal plans recorded by user
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
     * Initialises FlowPane Config.
     * Gives the overview of each meal plan day recorded by user
     */
    void initializeMealPlanDayFlowPaneView(ObservableList<Recipe> recipeList, FlowPane mealPlanDayListView) {
        mealPlanDayListView.setHgap(10);
        mealPlanDayListView.setVgap(10);
        mealPlanDayListView.setPadding(new Insets(10, 10, 10, 10));
        mealPlanDayListView.getChildren().remove(0, mealPlanDayListView.getChildren().size());

        // Creates a RecipeCard for each recipe and adds to FlowPane
        int i = 0;
        for (Recipe recipe : recipeList) {
            mealPlanDayListView.getChildren().add(new RecipeCard(recipe, i + 1).getRoot());
            i++;
        }
        //add listener for new recipe book changes
        recipeList.addListener((ListChangeListener<Recipe>) c -> {
            mealPlanDayListView.getChildren().clear();
            int x = 0;
            for (Recipe recipe: recipeList) {
                mealPlanDayListView.getChildren().add(new RecipeCard(recipe, x + 1).getRoot());
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
     * Iterates through the stored recipe list and returns the Recipe with the corresponding RecipeName.
     */
    Recipe getRecipe(RecipeName name) {
        for (Recipe recipe : recipeList) {
            if (recipe.getName().equals(name)) {
                return recipe;
            }
        }

        throw new RecipeNotFoundException(name.fullName);
    }

    /**
     * Initialises Meal Plan List View Config
     * Gives the overview of pages in that specified diary
     */
    void initializeMealPlanListView(int targetIndex) {

        ObservableList<Recipe> day1List = mealPlanList.get(targetIndex).getDay1().stream().map(this::getRecipe)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        ObservableList<Recipe> day2List = mealPlanList.get(targetIndex).getDay2().stream().map(this::getRecipe)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        ObservableList<Recipe> day3List = mealPlanList.get(targetIndex).getDay3().stream().map(this::getRecipe)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        ObservableList<Recipe> day4List = mealPlanList.get(targetIndex).getDay4().stream().map(this::getRecipe)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        ObservableList<Recipe> day5List = mealPlanList.get(targetIndex).getDay5().stream().map(this::getRecipe)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        ObservableList<Recipe> day6List = mealPlanList.get(targetIndex).getDay6().stream().map(this::getRecipe)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        ObservableList<Recipe> day7List = mealPlanList.get(targetIndex).getDay7().stream().map(this::getRecipe)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));


        // Sets the name of the meal plan
        mealPlanHeader.setText(mealPlanList.get(targetIndex).getName().fullName);
        mealPlanNutrition.setText(initializeMealPlanNutritionalData(day1List, day2List, day3List, day4List, day5List,
                day6List, day7List));

        initializeMealPlanDayFlowPaneView(day1List, mealPlanDay1ListView);
        initializeMealPlanDayFlowPaneView(day2List, mealPlanDay2ListView);
        initializeMealPlanDayFlowPaneView(day3List, mealPlanDay3ListView);
        initializeMealPlanDayFlowPaneView(day4List, mealPlanDay4ListView);
        initializeMealPlanDayFlowPaneView(day5List, mealPlanDay5ListView);
        initializeMealPlanDayFlowPaneView(day6List, mealPlanDay6ListView);
        initializeMealPlanDayFlowPaneView(day7List, mealPlanDay7ListView);

    }

    /**
     * Initialize nutritional data for the meal plan given the input dayLists.
     */
    String initializeMealPlanNutritionalData(ObservableList<Recipe>... dayLists) {
        int calories = 0;
        int carbs = 0;
        int fats = 0;
        int protein = 0;

        for (List<Recipe> day : dayLists) {
            for (Recipe recipe : day) {
                calories += Integer.parseInt(recipe.getCalories().value);
                carbs += Integer.parseInt(recipe.getCarbs().value);
                fats += Integer.parseInt(recipe.getFats().value);
                protein += Integer.parseInt(recipe.getProtein().value);
            }
        }

        String result = "Total Calories: " + calories + "kcal\n"
                + "Total Carbohydrates: " + carbs + "g\n"
                + "Total Fats: " + fats + "g\n"
                + "Total Protein: " + protein + "g";

        return result;
    }

    /**
     * Display inner components within Meal Plan Panel.
     * Make use of boolean variables to decide which components to show/hide.
     */
    private void showPanels(boolean showIndexView, boolean showCardList, boolean showCardView) {
        mealPlanIndexListView.setVisible(showIndexView);
        mealPlanIndexListView.setManaged(showIndexView);

        mealPlanListPanel.setVisible(showCardList);
        mealPlanListPanel.setManaged(showCardList);

        mealPlanViewPanel.setVisible(showCardView);
        mealPlanViewPanel.setManaged(showCardView);
    }

    /**
     * Hide all inner components within Meal Plan Panel.
     */
    private void hidePanels() {
        mealPlanIndexListView.setVisible(false);
        mealPlanListView.setVisible(false);
        mealPlanViewView.setVisible(false);
    }

    /**
     * Switch view within MealPlan Book Panel.
     */
    @FXML
    void handleSwitch(String type) {
        String[] typeArr = type.split("-", 2);
        switch (typeArr[0]) {
        case "all":
            showPanels(true, true, false);
            break;

        case "update":
            initializeMealPlanListView(Integer.parseInt(typeArr[1]));
            showPanels(true, false, true);
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

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Recipe} using a {@code RecipeCard}.
     */
    class RecipeListViewCell extends ListCell<Recipe> {
        @Override
        protected void updateItem(Recipe recipe, boolean empty) {
            super.updateItem(recipe, empty);

            if (empty || recipe == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RecipeCard(recipe, getIndex() + 1).getRoot());
            }
        }
    }

}
