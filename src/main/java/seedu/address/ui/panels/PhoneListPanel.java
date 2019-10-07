package seedu.address.ui.panels;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.phone.Phone;
import seedu.address.ui.UiPart;
import seedu.address.ui.cards.PhoneCard;

/**
 * Panel containing the list of phones.
 */
public class PhoneListPanel extends UiPart<Region> {
    private static final String FXML = "PhoneListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CustomerListPanel.class);

    @FXML
    private ListView<Phone> phoneListView;

    public PhoneListPanel(ObservableList<Phone> phoneList) {
        super(FXML);
        phoneListView.setItems(phoneList);
        phoneListView.setCellFactory(listView -> new PhoneListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Phone} using a {@code PhoneCard}.
     */
    class PhoneListViewCell extends ListCell<Phone> {
        @Override
        protected void updateItem(Phone phone, boolean empty) {
            super.updateItem(phone, empty);

            if (empty || phone == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PhoneCard(phone, getIndex() + 1).getRoot());
            }
        }
    }

}
