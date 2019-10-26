package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.binitem.BinItem;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

/**
 * Panel containing the list of items in the bin.
 */
public class BinItemListPanel extends UiPart<Region> {

    private static final String FXML = "BinItemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BinItemListPanel.class);

    @FXML
    private ListView<BinItem> binItemListView;

    public BinItemListPanel(ObservableList<BinItem> binItemList) {
        super(FXML);
        binItemListView.setItems(binItemList);
        binItemListView.setCellFactory(listView -> new BinItemListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class BinItemListViewCell extends ListCell<BinItem> {
        @Override
        protected void updateItem(BinItem binItem, boolean empty) {
            super.updateItem(binItem, empty);

            if (empty || binItem == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (binItem.getItem() instanceof Person) {
                    Person p = (Person) binItem.getItem();
                    setGraphic(new BinPersonCard(p, getIndex() + 1, binItem.getDateDeleted(), binItem.getExpiryDate())
                        .getRoot());
                } else {
                    Policy p = (Policy) binItem.getItem();
                    setGraphic(new BinPolicyCard(p, getIndex() + 1, binItem.getDateDeleted(), binItem.getExpiryDate())
                        .getRoot());
                }
            }
        }
    }

}
