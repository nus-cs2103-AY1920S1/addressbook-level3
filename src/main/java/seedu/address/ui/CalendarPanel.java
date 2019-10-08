package seedu.address.ui;

import java.time.Instant;
import java.time.YearMonth;
import java.time.LocalDate;
import java.text.SimpleDateFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Region;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

import seedu.address.model.events.EventSource;
import seedu.address.model.events.DateTime;

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

    private void setCurrentMonth() {
        String currentMonth = yearMonth.getMonth().toString().toLowerCase();
        currentMonth = currentMonth.substring(0, 1).toUpperCase() + currentMonth.substring(1);
        month.setText(currentMonth);
    }

    private void fillIndexOfCalendar(int startingDay, ObservableList<EventSource> eventList) {
        int index = 1;
        int totalDays = yearMonth.lengthOfMonth();
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j++) {
                if(i == 0 && j == 0) {
                    j = startingDay - 2;
                    continue;
                }
                if(index > totalDays) {
                    break;
                }
                DayCard todayCard = new DayCard(index);
                updateCard(todayCard, index, yearMonth.getMonthValue(), yearMonth.getYear(), eventList);
                calendar.add(todayCard.getRoot(), j, i);
                index++;
            }
        }
    }

    private void updateCard(DayCard todayCard, int day, int month, int year, ObservableList<EventSource> eventList) {
        for(EventSource event : eventList) {
            System.out.println(event.getDescription());
            Instant date = event.getStartDateTime().getDateTime();
            Integer dayMonthyear[] = getDayMonthYear(date);
            if(day == dayMonthyear[0] && month == dayMonthyear[1] && year == dayMonthyear[3]) {
                todayCard.updateEventList(event);
            }
        }
    }

    private Integer[] getDayMonthYear(Instant date) {
        Integer dayMonthyear[] = new Integer[3];
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
            dayMonthyear[0] = Integer.valueOf(dateFormat.format(date));
            dateFormat = new SimpleDateFormat("MM");
            dayMonthyear[1] = Integer.valueOf(dateFormat.format(date));
            dateFormat = new SimpleDateFormat("yyyy");
            dayMonthyear[2] = Integer.valueOf(dateFormat.format(date));
            return dayMonthyear;
        } catch (Exception e) {
            throw e;
        }
    }


}
