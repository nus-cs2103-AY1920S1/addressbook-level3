package seedu.address.ui.cap;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import seedu.address.model.common.Module;
import seedu.address.model.cap.person.Semester;

public class SemesterCard extends UiPart<Region> {

    private static final String FXML = "SemesterListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final seedu.address.model.cap.person.Semester semester;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label semesterYear;


    public SemesterCard(Semester semester) {
        super(FXML);
        this.semester = semester;
        semesterYear.setText(semester.toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCard)) {
            return false;
        }

        // state check
        SemesterCard card = (SemesterCard) other;
        return this.semester.getSemesterPeriod().equals(card.semester.toString());
    }
}
