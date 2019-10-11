package tagline.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import tagline.model.contact.Contact;

/**
 * The UI component that displays the contact list as a result.
 */
public class ContactResultView extends ResultView {

    private static final String FXML = "ContactResultView.fxml";

    private ContactListPanel contactListPanel;

    @FXML
    private StackPane contactListPanelPlaceholder;

    public ContactResultView() {
        super(FXML);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts(ObservableList<Contact> contactList) {
        contactListPanel = new ContactListPanel(contactList);
        contactListPanelPlaceholder.getChildren().add(contactListPanel.getRoot());
    }
}
