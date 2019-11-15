package dukecooks.ui;

import dukecooks.model.mealplan.components.MealPlan;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code MealPlan}.
 */
public class MealPlanIndexCard extends UiPart<Region> {

    private static final String FXML = "MealPlanIndexListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final MealPlan recipe;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    public MealPlanIndexCard(MealPlan mealPlan, int displayedIndex) {
        super(FXML);
        this.recipe = mealPlan;
        id.setText(displayedIndex + ". ");
        name.setText(mealPlan.getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MealPlanIndexCard)) {
            return false;
        }

        // state check
        MealPlanIndexCard card = (MealPlanIndexCard) other;
        return id.getText().equals(card.id.getText())
                && recipe.equals(card.recipe);
    }
}
