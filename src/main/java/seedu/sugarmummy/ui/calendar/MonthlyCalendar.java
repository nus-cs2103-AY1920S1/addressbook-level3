package seedu.sugarmummy.ui.calendar;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.time.Today;
import seedu.sugarmummy.model.time.YearMonth;
import seedu.sugarmummy.ui.UiPart;

/**
 * The UI component for showing a monthly calendar.
 */
public class MonthlyCalendar extends UiPart<Region> {
    private static final String FXML = "MonthlyCalendar.fxml";
    private static int numberOfColumns = 7;
    private final Logger logger = LogsCenter.getLogger(MonthlyCalendar.class);

    @FXML
    private Label yearMonth;
    @FXML
    private GridPane calendarMonthPanel;

    public MonthlyCalendar(YearMonth yearMonth, ObservableList<CalendarEntry> calendarEntries, Today today) {
        super(FXML);
        initializeTitle(yearMonth.getYear(), yearMonth.getMonth());
        setStyle();
        initializeHeader();
        initializeDates(yearMonth.getYear(), yearMonth.getMonth(), calendarEntries, today);
    }

    /**
     * Initializes a title containing year and month.
     */
    private void initializeTitle(int year, int month) {
        String monthName = new DateFormatSymbols().getMonths()[month - 1];
        yearMonth.setText(year + " " + monthName);
    }

    /**
     * Sets style of the calendar month panel.
     */
    private void setStyle() {
        calendarMonthPanel.setStyle("-fx-background-color: #BABFCA; -fx-border-color: white");
        calendarMonthPanel.setGridLinesVisible(true);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100.0 / numberOfColumns);
        columnConstraints.setHgrow(Priority.ALWAYS);

        for (int i = 0; i < numberOfColumns; i++) {
            calendarMonthPanel.getColumnConstraints().add(columnConstraints);
        }
    }

    /**
     * Initializes header of a monthly calendar.
     */
    private void initializeHeader() {
        for (int i = 0; i < numberOfColumns; i++) {
            calendarMonthPanel.add(new CalendarMonthHeader(i + 1).getRoot(), i, 0, 1, 1);
        }
    }

    /**
     * Initializes UI for each date in the given year and month.
     */
    private void initializeDates(int year, int month, ObservableList<CalendarEntry> calendarEntries, Today today) {
        LocalDate currentDate = LocalDate.of(year, month, 1);
        int currentDayOfWeek = currentDate.getDayOfWeek().getValue();

        int rowNum = 1;
        int colNum = 0;

        for (; colNum < currentDayOfWeek - 1; colNum++) {
            calendarMonthPanel.add(new CalendarMonthDayCard().getRoot(), colNum, rowNum, 1, 1);
        }
        for (; currentDate.getMonthValue() == month; currentDate = currentDate.plusDays(1)) {
            calendarMonthPanel.add(new CalendarMonthDayCard(currentDate, calendarEntries, today).getRoot(),
                    colNum, rowNum, 1, 1);
            colNum++;
            if (colNum == numberOfColumns) {
                rowNum++;
                colNum = 0;
            }
        }
    }
}
