package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.semester.Semester;

/**
 * An UI component that displays a simplified semester on the left side of the GUI.
 */
public class SimpleSemesterListCard extends UiPart<Region> {

    private static final String FXML = "SimpleSemesterListCard.fxml";

    public final Semester semester;

    @FXML
    private Label semesterName;

    @FXML
    private Label semesterDescription;

    public SimpleSemesterListCard(Semester semester) {
        super(FXML);
        requireNonNull(semester);
        this.semester = semester;
        semesterName.setText(semester.getSemesterName().toString());
        semesterDescription.setText(semester.toStringForSimplifiedStudyPlan());
    }

}
