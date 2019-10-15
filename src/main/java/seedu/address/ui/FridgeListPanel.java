package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.fridge.Fridge;

/**
 * Panel containing the list of bodies.
 */
public class FridgeListPanel extends UiPart<Region> {
    private static final String FXML = "FridgeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FridgeListPanel.class);

    @FXML
    private ListView<Fridge> fridgeListView;

    public FridgeListPanel(ObservableList<Fridge> fridgeList) {
        super(FXML);
        fridgeListView.setItems(fridgeList);
        fridgeListView.setCellFactory(listView -> new FridgeListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Body} using a {@code BodyCard}.
     */
    class FridgeListViewCell extends ListCell<Fridge> {
        @Override
        protected void updateItem(Fridge firdge, boolean empty) {
            super.updateItem(firdge, empty);

            if (empty || firdge == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FridgeCard(firdge, getIndex() + 1).getRoot());
            }
        }
    }

}
