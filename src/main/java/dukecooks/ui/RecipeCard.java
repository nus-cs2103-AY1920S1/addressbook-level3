package dukecooks.ui;

import java.util.Comparator;

import dukecooks.model.recipe.components.Recipe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class RecipeCard extends UiPart<Region> {

    private static final String FXML = "RecipeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Recipe recipe;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label calories;
    @FXML
    private Label carbs;
    @FXML
    private Label fats;
    @FXML
    private Label protein;
    @FXML
    private VBox ingredients;

    public RecipeCard(Recipe recipe, int displayedIndex) {
        super(FXML);
        this.recipe = recipe;
        id.setText(displayedIndex + ". ");
        name.setText(recipe.getName().fullName);
        recipe.getIngredients().stream()
                .sorted(Comparator.comparing(ingredient -> ingredient.ingredientName))
                .forEach(ingredient -> ingredients.getChildren().add(new Label(ingredient.ingredientName)));
        calories.setText(recipe.getCalories().cardValue);
        carbs.setText(recipe.getCarbs().cardValue);
        fats.setText(recipe.getFats().cardValue);
        protein.setText(recipe.getProtein().cardValue);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecipeCard)) {
            return false;
        }

        // state check
        RecipeCard card = (RecipeCard) other;
        return id.getText().equals(card.id.getText())
                && recipe.equals(card.recipe);
    }
}
