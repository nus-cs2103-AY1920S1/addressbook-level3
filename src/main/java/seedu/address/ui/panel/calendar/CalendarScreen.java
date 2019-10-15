package seedu.address.ui.panel.calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import javafx.scene.layout.StackPane;
import seedu.address.ui.UiParser;
import seedu.address.ui.UiPart;

/**
 * An Ui that represents an actual calendar of a month.
 */
public class CalendarScreen extends UiPart<Region> {

    private static final String FXML = "Calendar.fxml";

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
                CalendarGridDay calendarGridDay = new CalendarGridDay(index);
                calendarGrid.add(calendarGridDay.getRoot(), days, weeks);
                dayIndexList.add(calendarGridDay);
                index++;
            }
        }
    }
}
