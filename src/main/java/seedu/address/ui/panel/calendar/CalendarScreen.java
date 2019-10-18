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
import seedu.address.ui.UiParser;
import seedu.address.ui.UiPart;

/**
 * An Ui that represents an actual calendar of a month.
 */
public class CalendarScreen extends UiPart<Region> {

    private static final String FXML = "CalendarScreen.fxml";

    private Integer month;
    private Integer year;
    private LocalDate localDate;
    private YearMonth yearMonth;
    private ArrayList<CalendarGridDay> dayIndexList;

    private UiParser uiParser;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Label monthYearTitle;

    @FXML
    private StackPane details;

    /**
     * Constructor for CalendarScreen that adds a month and year to form the calendar.
     *
     * @param month Represents the month of the calendar.
     * @param year Represents the year of the calendar.
     * @param uiParser Represents a parser to convert certain types of objects into other types of objects.
     *
     */
    public CalendarScreen(Integer month, Integer year, UiParser uiParser) {
        super(FXML);
        this.month = month;
        this.year = year;
        this.uiParser = uiParser;
        this.localDate = LocalDate.of(year, month, 1);
        this.yearMonth = YearMonth.of(year, month);
        this.dayIndexList = new ArrayList<>();
        this.monthYearTitle.setText(uiParser.getEnglishDate(month, year));

        resetCalendar();
    }

    /**
     * Fills the index of the calendar and resets when needed to.
     */
    private void resetCalendar() {
        int index = 1;
        int startingDay = this.localDate.withDayOfMonth(1).getDayOfWeek().getValue();
        int totalDays = this.yearMonth.lengthOfMonth();
        for (int weeks = 0; weeks < 6; weeks++) {
            for (int days = 0; days < 7; days++) {
                if (weeks == 0 && days == 0) {
                    days = startingDay - 2;
                    continue;
                }
                if (index > totalDays) {
                    break;
                }
                // TODO: Change Total Events to fit actual list.
                CalendarGridDay calendarGridDay = new CalendarGridDay(index, 0);
                calendarGrid.add(calendarGridDay.getRoot(), days, weeks);
                dayIndexList.add(calendarGridDay);
                index++;
            }
        }
    }

    /**
     * Changes the color scheme for each day of the calendar screen.
     * @param events The list of events.
     */
    public void eventChange(List<EventSource> events) {
        changeColor(events);
    }

    private void changeColor(List<EventSource> events) {
        for(EventSource event : events) {
            if(sameMonthYear(event)) {
                Integer day = uiParser.getDay(event.getStartDateTime().getDateTime());
                dayIndexList.get(day - 1).addAnEvent();
            }
        }
    }

    private boolean sameMonthYear(EventSource event) {
        Integer[] monthYear = uiParser.getDateToNumbers(event.getStartDateTime().getDateTime());
        return monthYear[1].equals(this.month) && monthYear[2].equals(this.year);
    }
}
