package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.sgm.model.food.Food;

/**
 * Displays information of a {@code Food}
 */
public class FoodCard extends UiPart<Region> {
    private static final String FXML = "FoodListCard.fxml";

    public final Food food;

    @FXML
    private Label foodName;
    @FXML
    private Label calorie;
    @FXML
    private Label gi;
    @FXML
    private Label sugar;
    @FXML
    private Label fat;

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public FoodCard(Food food) {


        super(FXML);
        this.food = food;
        foodName.setText(food.getFoodName().foodName);
        calorie.setText(food.getCalorie().value);
        gi.setText(food.getGi().value);
        sugar.setText(food.getSugar().value);
        fat.setText(food.getFat().value);
    }

}
