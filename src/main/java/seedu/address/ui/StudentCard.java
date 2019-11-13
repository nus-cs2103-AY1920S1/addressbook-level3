package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Classroom level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label parentPhone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label medicalCondition;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView displayPicture;

    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        phone.setText("(P): " + student.getPhone().value);
        parentPhone.setText("(PP): " + student.getParentPhone().value);
        address.setText("(A): " + student.getAddress().value);
        email.setText("(E): " + student.getEmail().value);
        medicalCondition.setText("(M): " + student.getMedicalCondition().value);
        student.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        Image displayImg = new Image(student.getDisplayPictureFilePath());
        displayPicture.setImage(displayImg);
    }

    /**
     * method for reloading image when picture uploaded
     */
    public void reloadImage() {
        Image img = new Image(student.getDisplayPictureFilePath());
        displayPicture.setImage(img);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentCard)) {
            return false;
        }

        // state check
        StudentCard card = (StudentCard) other;
        return id.getText().equals(card.id.getText())
                && student.equals(card.student);
    }
}
