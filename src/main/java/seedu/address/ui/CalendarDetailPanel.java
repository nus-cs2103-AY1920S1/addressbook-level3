package seedu.address.ui;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.Model;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.performance.CalendarCompatibleRecord;
import seedu.address.model.performance.Event;
import seedu.address.model.person.Person;
import seedu.address.model.training.AttendanceEntry;

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

    /**
     * Retrieves attendance data from model. Creates a row for each person and displays their
     * name and attendance in the same row.
     */
    private void initialiseAttendanceData() {
        if (model.hasTrainingOn(date)) {
            addAttendanceChart();
            addAttendanceTableHeader();
            addAttendanceTableContent();
        } else {
            displayNoAttendanceError();
        }
    }

    /**
     * Adds a piechart indicating overall team attendance.
     */
    private void addAttendanceChart() {
        List<AttendanceEntry> attendanceData = model.getTrainingAttendanceListOnDate(date);
        int total = attendanceData.size();
        int present = 0;
        for (AttendanceEntry entry: attendanceData) {
            if (entry.getIsPresent()) {
                present++;
            }
        }
        AttendanceChart chart = new AttendanceChart(present, total - present);
        attendanceBox.getChildren().add(chart.getRoot());
    }

    /**
     * Adds header for attendance table.
     */
    private void addAttendanceTableHeader() {
        attendanceBox.getChildren().add(new AttendanceTableHeader().getRoot());
    }

    /**
     * Adds content for attendance table.
     */
    private void addAttendanceTableContent() {
        List<AttendanceEntry> attendanceData = model.getTrainingAttendanceListOnDate(date);
        for (AttendanceEntry entry: attendanceData) {
            String name = entry.getPerson().getName().toString();
            boolean isPresent = entry.getIsPresent();
            AttendanceTableContent content = new AttendanceTableContent(name, isPresent);
            attendanceBox.getChildren().add(content.getRoot());
        }
    }

    /**
     * Displays message informing user that there is no attendance data on the specified date.
     */
    private void displayNoAttendanceError() {
        String errorMsg = "No Training Record on " + date.toString();
        ErrorMessageLabel error = new ErrorMessageLabel(errorMsg);
        attendanceBox.getChildren().add(error.getRoot());
    }

    /**
     * Retrieves performance data from model. Creates a header for each event and lists records for
     * the event taken on the particular date.
     */
    private void initialisePerformanceData() {
        if (model.hasPerformanceOn(date)) {
            addPerformanceStats();
            addPerformanceTable();
        } else {
            displayNoPerformanceError();
        }
    }

    /**
     * Adds a header indicating total number of performance records on a specified date.
     */
    private void addPerformanceStats() {
        AtomicInteger counter = new AtomicInteger();
        HashMap<Event, List<CalendarCompatibleRecord>> performanceData =
                model.getCalendarCompatiblePerformance(date);
        performanceData.forEach((event, recordList) -> {
            counter.addAndGet(recordList.size());
        });
        PerformanceStats stats = new PerformanceStats(counter.get());
        performanceBox.getChildren().add(stats.getRoot());
    }

    /**
     * Adds performance data in the form of a table to {@code CalendarDetailPanel}.
     */
    private void addPerformanceTable() {
        HashMap<Event, List<CalendarCompatibleRecord>> performanceData =
                model.getCalendarCompatiblePerformance(date);
        performanceData.forEach((event, recordList) -> {
            int numRecords = recordList.size();
            if (numRecords > 0) {
                PerformanceTableHeader header = new PerformanceTableHeader(event.getName());
                performanceBox.getChildren().add(header.getRoot());
                for (int i = 0; i < numRecords; i++) {
                    CalendarCompatibleRecord record = recordList.get(i);
                    addPerformanceRecord(record);
                }
            }
        });
    }

    /**
     * Adds performance {@code record} for a particular event.
     * @param record Record of a particular event
     */
    private void addPerformanceRecord(CalendarCompatibleRecord record) {
        Person athlete = record.getAthlete();
        String name = athlete.getName().toString();
        String timing = record.getTiming();
        PerformanceTableContent content = new PerformanceTableContent(name, timing);
        performanceBox.getChildren().add(content.getRoot());
    }

    /**
     * Displays message informing user that there is no performance data on the specified date.
     */
    private void displayNoPerformanceError() {
        String errorMsg = "No Performance Record on " + date.toString();
        ErrorMessageLabel error = new ErrorMessageLabel(errorMsg);
        performanceBox.getChildren().add(error.getRoot());
    }
}
