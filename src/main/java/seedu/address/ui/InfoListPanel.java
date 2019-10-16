package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;

/**
 * Panel containing the list of information.
 */
public class InfoListPanel extends UiPart<Region> {
    private static final String FXML = "InfoListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InfoListPanel.class);

    @FXML
    private ListView<Contact> infoListView;

    public InfoListPanel(ObservableList<Contact> contactList) {
        super(FXML);
        infoListView.setItems(contactList);
        infoListView.setCellFactory(listView -> new InfoListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Contact} using a {@code PersonCard}.
     */
    class InfoListViewCell extends ListCell<Contact> {
        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);

            if (empty || contact == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ContactCardFull(contact, getIndex() + 1).getRoot());
            }
        }
    }

}
