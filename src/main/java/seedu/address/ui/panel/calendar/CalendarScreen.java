package seedu.address.ui.panel.calendar;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import javafx.scene.layout.StackPane;
import seedu.address.model.CalendarDate;
import seedu.address.model.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UiPart;

//@@author Kyzure
/**
 * An Ui that represents an actual calendar of a month.
 */
public class CalendarScreen extends UiPart<Region> {

    private static final String FXML = "CalendarScreen.fxml";

    private CalendarDate calendarDate;
    private ArrayList<CalendarGridDay> dayIndexList;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Label monthYearTitle;

    @FXML
    private StackPane details;

    /**
     * Constructor for CalendarScreen that adds a month and year to form the calendar.
     *
     * @param calendarDate Represents a date of the calendar.
     *
     */
    public CalendarScreen(CalendarDate calendarDate) {
        super(FXML);
        this.calendarDate = calendarDate.firstDayOfTheMonth();
        this.dayIndexList = new ArrayList<>();
        this.monthYearTitle.setText(calendarDate.getEnglishMonth() + " " + calendarDate.getYear());

        resetCalendar();
        setCurrentDate();
    }

    /**
     * Fills the index of the calendar and resets when needed to.
     */
    private void resetCalendar() {
        Integer weekIndex = calendarDate.getWeekIndex();
        CalendarDate currentDate = calendarDate.previousDays(weekIndex - 1);

        // Week represents the row.
        for (int week = 0; week < 6; week++) {
            // Day represents the column.
            for (int day = 0; day < 7; day++) {
                CalendarGridDay calendarGridDay = new CalendarGridDay(currentDate, 0);
                if (!calendarDate.sameMonthYear(currentDate.getMonth(), currentDate.getYear())) {
                    calendarGridDay.reduceOpacity();
                } else {
                    dayIndexList.add(calendarGridDay);
                }
                calendarGrid.add(calendarGridDay.getRoot(), day, week);
                currentDate = currentDate.nextDay();
            }
        }
    }

    /**
     * Changes the color scheme for each day of the calendar screen.
     * @param eventTaskList The list of events and list.
     */
    public void onChange(List<Object> eventTaskList) {
        changeColor(eventTaskList);
    }

    /**
     * Changes the color of the current month to indicate the color of the event by adding
     * events to the given CalendarGridDay.
     *
     * @param eventTaskList The given list of events and tasks.
     * @see CalendarGridDay
     */
    private void changeColor(List<Object> eventTaskList) {
        // We do not want to change the color of next and previous month.
        resetColor();
        for (Object source : eventTaskList) {
            if (source instanceof EventSource) {
                EventSource event = (EventSource) source;
                DateTime eventDateTime = event.getStartDateTime();
                if (calendarDate.sameMonthYear(eventDateTime.getMonth(), eventDateTime.getYear())) {
                    Integer day = eventDateTime.getDay();
                    dayIndexList.get(day - 1).increaseIntensity();
                }
            } else if (source instanceof TaskSource) {
                TaskSource task = (TaskSource) source;
                DateTime taskDateTime = task.getDueDate();
                if (taskDateTime == null) {
                    break;
                }
                if (calendarDate.sameMonthYear(taskDateTime.getMonth(), taskDateTime.getYear())) {
                    Integer day = taskDateTime.getDay();
                    dayIndexList.get(day - 1).increaseIntensity();
                }
            }
        }
    }

    private void setCurrentDate() {
        CalendarDate currentDate = CalendarDate.now();
        if (currentDate.sameMonthYear(calendarDate.getMonth(), calendarDate.getYear())) {
            dayIndexList.get(currentDate.getDay() - 1).setCurrentDate();
        }
    }

    private void resetColor() {
        for (CalendarGridDay day : dayIndexList) {
            day.resetColor();
        }
    }
}
