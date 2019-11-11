package seedu.sugarmummy.ui.recmf;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.model.recmf.Food;
import seedu.sugarmummy.ui.UiPart;

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

        foodList.addListener((ListChangeListener<Food>) listener -> refreshFlowPanel(foodList));
        fillFlowPaneContent(foodList);
        mainScrollPanel.setContent(flowPane);
    }

    private void fillFlowPaneContent(ObservableList<Food> foodList) {
        foodList.stream().forEach(food -> flowPane.getChildren().add(new FoodCard(food).getRoot()));
    }

    /**
     * Updates the food recommendation panel.
     */
    private void refreshFlowPanel(ObservableList<Food> foodList) {
        flowPane.getChildren().clear();
        fillFlowPaneContent(foodList);
    }
}
