package seedu.address.ui;

import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.YearMonth;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.calendar.YearMonthDay;

/**
 * Panel containing a monthly calendar and calendar entries on each day.
 */
public class CalendarMonthScrollPanel extends UiPart<Region> {
    private static final String FXML = "CalendarMonthScrollPane.fxml";
    private static int numberOfColumns = 7;
    private final Logger logger = LogsCenter.getLogger(CalendarMonthScrollPanel.class);

    @FXML
    private Label yearMonth;
    @FXML
    private GridPane calendarMonthPanel;
    @FXML
    private VBox monthlyCalendarEntries;

    public CalendarMonthScrollPanel(YearMonth yearMonth, Optional<YearMonthDay> yearMonthDay, boolean isShowingWeek,
                                    ObservableList<CalendarEntry> calendarEntries) {
        super(FXML);
        initializeTitle(yearMonth.getYear(), yearMonth.getMonth());
        setStyle();
        initializeHeader();
        initializeDates(yearMonth.getYear(), yearMonth.getMonth(), calendarEntries);
        initializeDailyCalendarEntries(yearMonth, yearMonthDay, isShowingWeek, calendarEntries);
    }

    /**
     * Initializes calendar entries.
     */
    private void initializeDailyCalendarEntries(YearMonth yearMonth, Optional<YearMonthDay> yearMonthDay,
                                                boolean isShowingWeek, ObservableList<CalendarEntry> calendarEntries) {
        if (yearMonthDay.isPresent()) {
            if (isShowingWeek) {
                initializeWeeklyCalendarEntries(yearMonthDay.get(), calendarEntries);
            } else {
                initializeDailyCalendarEntries(yearMonthDay.get(), calendarEntries);
            }
        } else {
            initializeDailyCalendarEntries(yearMonth, calendarEntries);
        }
    }

    /**
     * Initializes calendar entries of each day in the give year and month.
     */
    private void initializeDailyCalendarEntries(YearMonth yearMonth, ObservableList<CalendarEntry> calendarEntries) {
        LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        for (; date.getMonthValue() == yearMonth.getMonth(); date = date.plusDays(1)) {
            monthlyCalendarEntries.getChildren().add(new DailyCalendarEntries(date, calendarEntries).getRoot());
        }
    }

    /**
     * Initializes calendar entries on the given year month and day.
     */
    private void initializeDailyCalendarEntries(YearMonthDay yearMonthDay,
                                                ObservableList<CalendarEntry> calendarEntries) {
        monthlyCalendarEntries.getChildren().add(new DailyCalendarEntries(yearMonthDay.getYearMonthDay(),
            calendarEntries).getRoot());
    }

    /**
     * Initializes calendar entries in the week containing the given year month and day.
     */
    private void initializeWeeklyCalendarEntries(YearMonthDay yearMonthDay,
                                                 ObservableList<CalendarEntry> calendarEntries) {
        LocalDate date = yearMonthDay.getYearMonthDay();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        date = date.minusDays(dayOfWeek.getValue() - 1);
        for (int i = 1; i <= 7; i++) {
            monthlyCalendarEntries.getChildren().add(new DailyCalendarEntries(date, calendarEntries).getRoot());
            date = date.plusDays(1);
        }
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
        calendarMonthPanel.setBackground(new Background(
            new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
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
    private void initializeDates(int year, int month, ObservableList<CalendarEntry> calendarEntries) {
        LocalDate currentDate = LocalDate.of(year, month, 1);
        int currentDayOfWeek = currentDate.getDayOfWeek().getValue();

        int rowNum = 1;
        int colNum = 0;

        for (; colNum < currentDayOfWeek - 1; colNum++) {
            calendarMonthPanel.add(new CalendarMonthDayCard().getRoot(), colNum, rowNum, 1, 1);
        }
        for (; currentDate.getMonthValue() == month; currentDate = currentDate.plusDays(1)) {
            calendarMonthPanel.add(new CalendarMonthDayCard(currentDate, calendarEntries).getRoot(),
                colNum, rowNum, 1, 1);
            colNum++;
            if (colNum == numberOfColumns) {
                rowNum++;
                colNum = 0;
            }
        }
    }
}
