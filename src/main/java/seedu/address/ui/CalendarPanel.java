package seedu.address.ui;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import seedu.address.model.events.EventSource;

/**
 * An Ui that stores the logged feedback from the program to the user.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private YearMonth yearMonth;
    private LocalDate calendarDate;

    @FXML
    private GridPane calendar;

    @FXML
    private Label month;

    /**
     * Constructor for ListPanel. Stores the event list, and task list[in v2.0].
     */
    public CalendarPanel(ObservableList<EventSource> eventList) {
        super(FXML);
        this.yearMonth = YearMonth.now();
        this.calendarDate = LocalDate.now();
        setCurrentMonth();
        fillIndexOfCalendar(calendarDate.getDayOfWeek().getValue(), eventList);

    }

    /**
     * Sets the label to be the current Month.
     */
    private void setCurrentMonth() {
        String currentMonth = yearMonth.getMonth().toString().toLowerCase();
        currentMonth = currentMonth.substring(0, 1).toUpperCase() + currentMonth.substring(1);
        month.setText(currentMonth);
    }

    /**
     * Fills the index of the calendar.
     * @param startingDay Indicates the starting day of the week
     * @param eventList Contains the EventSource
     */
    private void fillIndexOfCalendar(int startingDay, ObservableList<EventSource> eventList) {
        int index = 1;
        int totalDays = yearMonth.lengthOfMonth();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0 && j == 0) {
                    j = startingDay - 2;
                    continue;
                }
                if (index > totalDays) {
                    break;
                }
                DayCard todayCard = new DayCard(index);
                updateCard(todayCard, index, yearMonth.getMonthValue(), yearMonth.getYear(), eventList);
                calendar.add(todayCard.getRoot(), j, i);
                index++;
            }
        }
    }

    /**
     * Updates the given DayCard.
     * @param todayCard The given DayCard
     * @param day Day of the DayCard
     * @param month Month of the DayCard
     * @param year Year of the DayCard
     * @param eventList EventList containing the events
     */
    private void updateCard(DayCard todayCard, int day, int month, int year, ObservableList<EventSource> eventList) {
        for (EventSource event : eventList) {
            System.out.println(event.getDescription());
            Instant date = event.getStartDateTime().getDateTime();
            Integer[] dayMonthyear = getDayMonthYear(date);
            if (day == dayMonthyear[0] && month == dayMonthyear[1] && year == dayMonthyear[3]) {
                todayCard.updateEventList(event);
            }
        }
    }

    /**
     * Returns an Integer Array containing {day, month, year} of a particular event given the date.
     * @param date The date of the event
     * @return An array containing the day, month and year of an event.
     */
    private Integer[] getDayMonthYear(Instant date) {
        Integer[] dayMonthYear = new Integer[3];
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
            dayMonthYear[0] = Integer.valueOf(dateFormat.format(date));
            dateFormat = new SimpleDateFormat("MM");
            dayMonthYear[1] = Integer.valueOf(dateFormat.format(date));
            dateFormat = new SimpleDateFormat("yyyy");
            dayMonthYear[2] = Integer.valueOf(dateFormat.format(date));
            return dayMonthYear;
        } catch (Exception e) {
            throw e;
        }
    }


}
