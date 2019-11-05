package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;

import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.model.AttendanceRateEntry;
import seedu.address.model.Model;

/**
 * UI component that is displayed when the command to view attendance is issued.
 */
public class AttendancePanel extends UiPart<Region> {

    private static final String FXML = "AttendancePanel.fxml";

    private Model model;

    @FXML
    private VBox attendanceBox;

    public AttendancePanel(Model model) {
        super(FXML);
        this.model = model;
        initialiseAttendanceData();
    }

    /**
     * Retrieves attendance data from model. Creates a row for each person and displays their
     * name and attendance in the same row.
     */
    private void initialiseAttendanceData() {
        attendanceBox.getChildren().add(new AttendanceRateTableHeader().getRoot());
        List<AttendanceRateEntry> attendanceRates = model.getAttendanceRateOfAll();
        for (AttendanceRateEntry entry : attendanceRates) {
            String name = entry.getPerson().getName().toString();
            String attendanceRate = entry.getAttendanceRateString();
            AttendanceRateTableContent content = new AttendanceRateTableContent(name, attendanceRate);
            attendanceBox.getChildren().add(content.getRoot());
        }
    }

}
