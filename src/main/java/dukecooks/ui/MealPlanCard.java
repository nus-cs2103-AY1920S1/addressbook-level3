package dukecooks.ui;

import java.util.Comparator;

import dukecooks.model.mealplan.components.MealPlan;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code MealPlan}.
 */
public class MealPlanCard extends UiPart<Region> {

    private static final String FXML = "MealPlanListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final MealPlan mealPlan;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private VBox day1;
    @FXML
    private VBox day2;
    @FXML
    private VBox day3;
    @FXML
    private VBox day4;
    @FXML
    private VBox day5;
    @FXML
    private VBox day6;
    @FXML
    private VBox day7;

    public MealPlanCard(MealPlan mealPlan, int displayedIndex) {
        super(FXML);
        this.mealPlan = mealPlan;
        id.setText(displayedIndex + ". ");
        name.setText(mealPlan.getName().fullName);

        mealPlan.getDay1().stream()
                .sorted(Comparator.comparing(recipe -> recipe.fullName))
                .forEach(recipe -> day1.getChildren().add(new Label(recipe.fullName)));
        mealPlan.getDay2().stream()
                .sorted(Comparator.comparing(recipe -> recipe.fullName))
                .forEach(recipe -> day2.getChildren().add(new Label(recipe.fullName)));
        mealPlan.getDay3().stream()
                .sorted(Comparator.comparing(recipe -> recipe.fullName))
                .forEach(recipe -> day3.getChildren().add(new Label(recipe.fullName)));
        mealPlan.getDay4().stream()
                .sorted(Comparator.comparing(recipe -> recipe.fullName))
                .forEach(recipe -> day4.getChildren().add(new Label(recipe.fullName)));
        mealPlan.getDay5().stream()
                .sorted(Comparator.comparing(recipe -> recipe.fullName))
                .forEach(recipe -> day5.getChildren().add(new Label(recipe.fullName)));
        mealPlan.getDay6().stream()
                .sorted(Comparator.comparing(recipe -> recipe.fullName))
                .forEach(recipe -> day6.getChildren().add(new Label(recipe.fullName)));
        mealPlan.getDay7().stream()
                .sorted(Comparator.comparing(recipe -> recipe.fullName))
                .forEach(recipe -> day7.getChildren().add(new Label(recipe.fullName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MealPlanCard)) {
            return false;
        }

        // state check
        MealPlanCard card = (MealPlanCard) other;
        return id.getText().equals(card.id.getText())
                && mealPlan.equals(card.mealPlan);
    }
}
