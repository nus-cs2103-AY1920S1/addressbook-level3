package seedu.address.ui;

import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.Model;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.performance.CalendarCompatibleRecord;
import seedu.address.model.performance.Event;
import seedu.address.model.person.Person;

/**
 * UI component that is displayed when the command to view trainings and performance on a
 * specified date is issued.
 */
public class CalendarDetailPanel extends UiPart<Region> {

    private static final String FXML = "CalendarDetailPanel.fxml";

    private AthletickDate date;
    private Model model;

    @FXML
    private VBox attendanceBox;

    @FXML
    private VBox performanceBox;

    public CalendarDetailPanel(AthletickDate date, Model model) {
        super(FXML);
        this.date = date;
        this.model = model;
        initialiseAttendanceData();
        initialisePerformanceData();
    }

    private void initialiseAttendanceData() {

    }

    /**
     * Retrieves data from model. Creates a header for each event and lists records for the event
     * taken on the particular date.
     */
    private void initialisePerformanceData() {
        HashMap<Event, List<CalendarCompatibleRecord>> performanceData =
                model.getCalendarCompatiblePerformance(date);
        performanceData.forEach((event, recordList) -> {
            int numRecords = recordList.size();
            if (numRecords > 0) {
                PerformanceTableHeader header = new PerformanceTableHeader(event.getName());
                performanceBox.getChildren().add(header.getRoot());
                for (int i = 0; i < numRecords; i++) {
                    CalendarCompatibleRecord record = recordList.get(i);
                    Person athlete = record.getAthlete();
                    String name = athlete.getName().toString();
                    String timing = record.getTiming();
                    PerformanceTableContent content = new PerformanceTableContent(name, timing);
                    performanceBox.getChildren().add(content.getRoot());
                }
            }
        });
    }
}
