package sugarmummy.recmfood.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiPart;
import sugarmummy.recmfood.model.Food;

/**
 * Represents a panel of a list of foods.
 */
public class FoodFlowPanel extends UiPart<Region> {
    private static final String FXML = "FoodFlowPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FoodFlowPanel.class);

    @FXML
    private FlowPane flowPane;
    @FXML
    private ScrollPane mainScrollPanel;

    public FoodFlowPanel(ObservableList<Food> foodList) {
        super(FXML);

        foodList.addListener(new ListChangeListener<Food>() {
            @Override
            public void onChanged(Change<? extends Food> c) {
                refreshFlowPanel(foodList);
            }
        });

        foodList.stream().sorted(Comparator.comparing(food -> food.getFoodType()))
            .forEach(food -> flowPane.getChildren().add(new FoodCard(food).getRoot()));
        mainScrollPanel.setContent(flowPane);
    }

    /**
     * Updates the food recommendation panel.
     */
    private void refreshFlowPanel(ObservableList<Food> foodList) {
        flowPane.getChildren().clear();
        for (Food food : foodList) {
            flowPane.getChildren().add(new FoodCard(food).getRoot());
        }
    }
}
