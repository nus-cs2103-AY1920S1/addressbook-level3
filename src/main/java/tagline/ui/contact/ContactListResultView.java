package tagline.ui.contact;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import tagline.model.contact.Contact;
import tagline.ui.ResultView;

/**
 * The UI component that displays the contact list as a result.
 */
public class ContactListResultView extends ResultView {

    private static final String FXML = "ContactListResultView.fxml";

    private ContactListPanel contactListPanel;

    @FXML
    private StackPane contactListPanelPlaceholder;

    public ContactListResultView() {
        super(FXML);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts(ObservableList<Contact> contactList) {
        contactListPanel = new ContactListPanel(contactList);
        contactListPanelPlaceholder.getChildren().add(contactListPanel.getRoot());
    }
}
