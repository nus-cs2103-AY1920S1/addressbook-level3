package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * The UI component that displays a attendance for a person.
 */
public class AttendanceRateTableContent extends UiPart<Region> {

    private static final String FXML = "AttendanceRateTableContent.fxml";

    @FXML
    private Label name;

    @FXML
    private Label attendanceRate;

    public AttendanceRateTableContent(String personName, String attendanceRate) {
        super(FXML);
        this.name.setText(personName);
        this.attendanceRate.setText(attendanceRate);
    }
}


