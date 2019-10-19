package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person} in the display panel.
 */
public class PersonInformationHolder extends UiPart<Region> {

    private static final String FXML = "PersonInformationHolder.fxml";

    private final Person person;

    @FXML
    private Label name;
    @FXML
    private Label nric;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox informationHolder;

    private String nameHeader = "Name: ";
    private String nricHeader = "NRIC: ";
    private String phoneHeader = "Phone: ";
    private String addressHeader = "Address: ";
    private String emailHeader = "Email: ";
    private String dateOfBirthHeader = "Date of birth: ";

    public PersonInformationHolder(Person person) {
        super(FXML);
        this.person = person;
        name.setText(nameHeader + person.getName().fullName);
        nric.setText(nricHeader + person.getNric().nric);
        phone.setText(phoneHeader + person.getPhone().value);
        address.setText(addressHeader + person.getAddress().value);
        email.setText(emailHeader + person.getEmail().value);
        dateOfBirth.setText(dateOfBirthHeader + person.getDateOfBirth().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonInformationHolder)) {
            return false;
        }

        // state check
        PersonInformationHolder card = (PersonInformationHolder) other;
        return person.equals(card.person);
    }
}
