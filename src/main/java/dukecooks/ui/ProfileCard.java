package dukecooks.ui;

import java.util.Comparator;

import dukecooks.model.profile.person.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ProfileCard extends UiPart<Region> {

    private static final String FXML = "ProfileCard.fxml";

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
    private Label dob;
    @FXML
    private Label gender;
    @FXML
    private Label bloodType;
    @FXML
    private Label weight;
    @FXML
    private Label height;
    @FXML
    private ImageView femaleAvatar;
    @FXML
    private ImageView maleAvatar;
    @FXML
    private VBox medicalHistories;

    public ProfileCard(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        dob.setText(person.getDateOfBirth().dateOfBirth);
        gender.setText(person.getGender().gender);
        bloodType.setText(person.getBloodType().bloodGroup);
        height.setText(person.getHeight().toString() + "cm");
        weight.setText(person.getWeight().toString() + "kg");
        medicalHistories.setSpacing(5);
        person.getMedicalHistories().stream()
                .sorted(Comparator.comparing(medicalHistory -> medicalHistory.medicalHistoryName))
                .forEach(medicalHistory -> medicalHistories.getChildren()
                        .add(new Label(medicalHistory.medicalHistoryName)));

        switch (person.getGender().gender) {
        case "female":
            showAvatar(true, false);
            break;
        case "male":
            showAvatar(false, true);
            break;
        default:
            throw new AssertionError("Unable to generate image for profile due to invalid Gender!");
        }
    }

    /**
     * Shows a avatar image base on the user's gender.
     */
    void showAvatar(boolean isFemale, boolean isMale) {
        femaleAvatar.setVisible(isFemale);
        femaleAvatar.setManaged(isFemale);

        maleAvatar.setVisible(isMale);
        maleAvatar.setManaged(isMale);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProfileCard)) {
            return false;
        }

        // state check
        ProfileCard card = (ProfileCard) other;
        return person.equals(card.person);
    }
}
