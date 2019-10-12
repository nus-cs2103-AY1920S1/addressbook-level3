package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private Rectangle header;
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

    private static final String CALORIE_TEXT = "Calorie";
    private static final String GI_TEXT = "GI";
    private static final String SUGAR_TEXT = "Sugar";
    private static final String FAT_TEXT = "Fat";
    private static final String POSTFIX = ":  ";

    private static final Color ORANGE = Color.rgb(240, 147, 43);
    private static final Color GREEN = Color.rgb(186, 220, 88);
    private static final Color YELLOW = Color.rgb(249, 202, 36);
    private static final Color BLUE = Color.rgb(126, 214, 223);
    private static final Color PINK = Color.rgb(214, 162, 232);
    private static final Color RED= Color.rgb(255, 127, 80);



    public FoodCard(Food food) {

        super(FXML);
        this.food = food;
        setTitleBackgroundColor();
        foodName.setText(food.getFoodName().foodName);
        calorie.setText(CALORIE_TEXT + POSTFIX + food.getCalorie().value);
        gi.setText(GI_TEXT + POSTFIX + food.getGi().value);
        sugar.setText(SUGAR_TEXT + POSTFIX + food.getSugar().value);
        fat.setText(FAT_TEXT + POSTFIX + food.getFat().value);
    }

    private void setTitleBackgroundColor() {
        switch (food.getFoodType()) {
        case NON_STARCHY_VEGETABLE:
            header.setFill(GREEN);
            break;
        case STARCHY_VEGETABLE:
            header.setFill(ORANGE);
            break;
        case FRUIT:
            header.setFill(YELLOW);
            break;
        case PROTEIN:
            header.setFill(BLUE);
            break;
        case SNACK:
            header.setFill(PINK);
            break;
        case MEAL:
            header.setFill(RED);
            break;
        default:
            assert false : "Food type is not found.";
        }
    }

    private Background backgroundBuilder(Color color) {
        return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
    }

}
