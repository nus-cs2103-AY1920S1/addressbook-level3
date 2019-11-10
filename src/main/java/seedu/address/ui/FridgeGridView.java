package seedu.address.ui;

import java.util.logging.Logger;

import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.fridge.Fridge;

//@@author shaoyi1997
/**
 * Represents the grid view that displays the fridges in the dashboard.
 */
public class FridgeGridView extends UiPart<Region> {

    private static final String FXML = "FridgeGridView.fxml";

    @FXML
    private GridView fridgeGridView;

    private final Logger logger = LogsCenter.getLogger(BodyTableView.class);

    public FridgeGridView (ObservableList<Fridge> fridgeList) {
        super(FXML);
        fridgeGridView.setItems(fridgeList);
        fridgeGridView.setCellFactory(gridview -> new FridgeGridCell());
        fridgeGridView.setCellWidth(70);
        fridgeGridView.setCellHeight(80);
        fridgeGridView.setHorizontalCellSpacing(5);
    }

    /**
     * Custom {@code GridCell} that displays the attributes of a {@code Fridge}.
     */
    class FridgeGridCell extends GridCell<Fridge> {
        @Override
        protected void updateItem(Fridge fridge, boolean empty) {
            super.updateItem(fridge, empty);

            if (empty || fridge == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FridgeCard(fridge).getRoot());
            }
        }
    }

}
//@@author
