package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.profile.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane medicalHistories;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label gender;
    @FXML
    private Label bloodGroup;
    @FXML
    private Label weight;
    @FXML
    private Label height;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        dateOfBirth.setText(person.getDateOfBirth().dateOfBirth);
        gender.setText(person.getGender().gender);
        bloodGroup.setText(person.getBloodType().bloodGroup);
        weight.setText(String.valueOf(person.getWeight().weight) + "kg (last updated: "
                + person.getWeight().timestamp + ")");
        height.setText(String.valueOf(person.getHeight().height) + "cm (last updated: "
                + person.getHeight().timestamp + ")");
        person.getMedicalHistories().stream()
                .sorted(Comparator.comparing(history -> history.medicalHistoryName))
                .forEach(history -> medicalHistories.getChildren().add(new Label(history.medicalHistoryName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
