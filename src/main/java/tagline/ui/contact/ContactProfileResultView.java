package tagline.ui.contact;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import tagline.model.contact.Contact;
import tagline.model.note.Note;
import tagline.ui.ResultView;
import tagline.ui.note.NoteListPanel;

/**
 * The UI component that displays a single contact as a result.
 */
public class ContactProfileResultView extends ResultView {

    private static final String FXML = "ContactProfileResultView.fxml";

    private NoteListPanel noteListPanel;

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label description;

    @FXML
    private StackPane noteListPanelPlaceholder;

    public ContactProfileResultView() {
        super(FXML);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts(ObservableList<Contact> contactList, ObservableList<Note> noteList) {
        noteListPanel = new NoteListPanel(noteList);
        noteListPanelPlaceholder.getChildren().add(noteListPanel.getRoot());

        contactList.addListener(new ListChangeListener<Contact>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Contact> change) {
                if (contactList.size() == 1) { //Single group display
                    updateLabels(contactList.get(0));
                }
            }
        });
    }

    /**
     * Updates the inner labels of this component, and hides them when empty.
     */
    public void updateLabels(Contact contact) {
        name.setText(contact.getName().fullName);
        id.setText("ID: " + contact.getContactId());

        setLabelText(phone, contact.getPhone().value);
        setLabelText(address, contact.getAddress().value);
        setLabelText(email, contact.getEmail().value);
        setLabelText(description, contact.getDescription().value);
    }

    /**
     * Sets the content in a {@code label} to {@code text}.
     * If {@code text} is empty, hides the {@code label}.
     */
    private void setLabelText(Label label, String text) {
        if (text.isEmpty()) {
            label.setVisible(false);
        } else {
            label.setVisible(true);
            label.setText(text);
        }
    }
}
