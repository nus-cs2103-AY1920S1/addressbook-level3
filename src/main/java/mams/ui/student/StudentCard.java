package mams.ui.student;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import mams.model.student.Student;
import mams.ui.UiPart;

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
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label credits;
    @FXML
    private Label matricId;
    @FXML
    private Label numberOfMods;
    @FXML
    private FlowPane appealTags;

    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        requireNonNull(student);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        credits.setText(student.getCredits().value);
        matricId.setText(student.getMatricId().value);
        numberOfMods.setText(Integer.toString(student.getNumberOfMods()));
        student.getCurrentAppeals().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> appealTags.getChildren().add(new Label(tag.tagName)));
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
