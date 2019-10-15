package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.display.sidepanel.PersonDisplay;

/**
 * A class for viewing the full details of a person.
 */
public class PersonDetailCard extends UiPart<Region> {
    private static final String FXML = "PersonDetailCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final PersonDisplay person;

    @FXML
    private HBox detailCard;
    @FXML
    private Label name;
    @FXML
    private Label nameLabel;
    @FXML
    private Label phone;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label address;
    @FXML
    private Label addressLabel;
    @FXML
    private Label email;
    @FXML
    private Label emailLabel;
    @FXML
    private Label remark;
    @FXML
    private Label remarkLabel;
    @FXML
    private FlowPane tags;

    public PersonDetailCard(PersonDisplay person) {
        super(FXML);
        this.person = person;
        initialiseLabels();
        name.setText(formatText(person.getName().fullName));
        phone.setText(formatText(person.getPhone().value));
        address.setText(formatText(person.getAddress().value));
        email.setText(formatText(person.getEmail().value));
        remark.setText(formatText(person.getRemark().value));
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Method to initialise the labels for the content of PersonDetailCard.
     */
    private void initialiseLabels() {
        nameLabel.setText("Name: ");
        phoneLabel.setText("Phone: ");
        addressLabel.setText("Address: ");
        emailLabel.setText("Email: ");
        remarkLabel.setText("Remark: ");
    }

    /**
     * Method to format the text to be displayed.
     * @param text The string to be formatted.
     * @return String that should be in the UI.
     */
    public static String formatText(String text) {
        if (text == null || text.equals("")) {
            return "-";
        } else {
            return text;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonDetailCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return person.isSamePerson(card.person);
    }
}
