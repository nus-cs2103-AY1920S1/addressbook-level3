package seedu.address.ui.panel.calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import javafx.scene.layout.StackPane;
import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.ui.UiParser;
import seedu.address.ui.UiPart;

/**
 * An Ui that stores the logged feedback from the program to the user.
 */
public class Calendar extends UiPart<Region> {

    private static final String FXML = "Calendar.fxml";

    private YearMonth yearMonth;
    private LocalDate calendarDate;
    private ArrayList<CalendarGridDay> dayIndexList;

    private UiParser uiParser;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Label month;

    @FXML
    private StackPane details;

    /**
     * Constructor for ListPanel. Stores the event list, and task list[in v2.0].
     */
    public Calendar(UiParser uiParser) {
        super(FXML);
        this.yearMonth = YearMonth.now();
        this.calendarDate = LocalDate.now();
        this.uiParser = uiParser;
        this.dayIndexList = new ArrayList<>();
        setCurrentMonth();
        resetCalendar();
    }

    /**
     * Sets the label to be the current Month.
     */
    private void setCurrentMonth() {
        String currentMonth = yearMonth.getMonth().toString().toLowerCase();
        currentMonth = currentMonth.substring(0, 1).toUpperCase() + currentMonth.substring(1);
        month.setText(currentMonth + " " + yearMonth.getYear());
    }

    /**
     * Fills the index of the calendar.
     */
    private void resetCalendar() {
        int index = 1;
        int startingDay = calendarDate.withDayOfMonth(1).getDayOfWeek().getValue();
        int totalDays = yearMonth.lengthOfMonth();
        for (int weeks = 0; weeks < 6; weeks++) {
            for (int days = 0; days < 7; days++) {
                if (weeks == 0 && days == 0) {
                    days = startingDay - 2;
                    continue;
                }
                if (index > totalDays) {
                    break;
                }
                CalendarGridDay calendarGridDay = new CalendarGridDay(index);
                calendarGrid.add(calendarGridDay.getRoot(), days, weeks);
                dayIndexList.add(calendarGridDay);
                index++;
            }
        }
    }
}
