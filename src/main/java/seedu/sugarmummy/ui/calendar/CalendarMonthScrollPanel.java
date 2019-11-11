package seedu.sugarmummy.ui.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.time.Today;
import seedu.sugarmummy.model.time.YearMonth;
import seedu.sugarmummy.model.time.YearMonthDay;
import seedu.sugarmummy.ui.UiPart;

/**
 * Panel containing a monthly calendar and calendar entries on each day.
 */
public class CalendarMonthScrollPanel extends UiPart<Region> {
    private static final String FXML = "CalendarMonthScrollPane.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarMonthScrollPanel.class);

    @FXML
    private VBox calendarMonthVBox;
    @FXML
    private VBox monthlyCalendarEntries;

    public CalendarMonthScrollPanel(YearMonth yearMonth, Optional<YearMonthDay> yearMonthDay, boolean isShowingWeek,
                                    ObservableList<CalendarEntry> calendarEntries, Today today) {
        super(FXML);
        calendarMonthVBox.getChildren().add(0, new MonthlyCalendar(yearMonth, calendarEntries, today).getRoot());
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
}
