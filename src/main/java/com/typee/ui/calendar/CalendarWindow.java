package com.typee.ui.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.model.engagement.Engagement;
import com.typee.ui.UiPart;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * The calendar window.
 * Solution below adapted from https://github.com/SirGoose3432/javafx-calendar
 */
public class CalendarWindow extends UiPart<Region> {

    public static final String FXML = "CalendarWindow.fxml";
    private static final int FIRST_DATE_OF_MONTH = 1;
    private static final int MAXIMUM_NUMBER_OF_WEEKS_PER_MONTH = 5;
    private static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private GridPane dateDisplayGrid;

    @FXML
    private Text calendarTitle;

    private List<CalendarDateCell> calendarDateCells;
    private ListChangeListener listener;
    private ObservableList<Engagement> engagements;
    private YearMonth currentDisplayedYearMonth;

    /**
     * Constructs a calendar window.
     */
    public CalendarWindow(ObservableList<Engagement> engagements) {
        super(FXML);
        calendarDateCells = new ArrayList<>();
        this.engagements = engagements;
        listener = change -> populateCalendar();
        engagements.addListener(listener);
        currentDisplayedYearMonth = YearMonth.now();
        initializeCalendarDisplay();
        populateCalendar();
    }

    /**
     * Initializes the date display grid with the current month.
     */
    private void initializeCalendarDisplay() {
        for (int i = 0; i < MAXIMUM_NUMBER_OF_WEEKS_PER_MONTH; i++) {
            for (int j = 0; j < NUMBER_OF_DAYS_IN_A_WEEK; j++) {
                CalendarDateCell calendarDateCell = new CalendarDateCell();
                calendarDateCells.add(calendarDateCell);
                StackPane calendarDatePane = calendarDateCell.getCalendarDatePane();
                dateDisplayGrid.add(calendarDatePane, j, i);
            }
        }
    }

    /**
     * Populates the calendar based on the currently displayed year and month.
     */
    private void populateCalendar() {
        LocalDate calendarDate = getDateOfFirstSundayToBeDisplayed();
        for (CalendarDateCell calendarDateCell : calendarDateCells) {
            setDisplayDate(calendarDateCell, calendarDate);
            addAllEngagementsForDate(calendarDateCell, calendarDate);
            updateEngagementCountDisplay(calendarDateCell);
            calendarDateCell.setDate(calendarDate);
            calendarDate = calendarDate.plusDays(1);
        }
        calendarTitle.setText(this.currentDisplayedYearMonth.getMonth().toString() + " "
                + this.currentDisplayedYearMonth.getYear());
    }

    /**
     * Returns a {@code LocalDate} instance representing the first Sunday to be displayed.
     * @return A {@code LocalDate} instance representing the first Sunday to be displayed.
     */
    private LocalDate getDateOfFirstSundayToBeDisplayed() {
        LocalDate firstSundayDate = LocalDate.of(currentDisplayedYearMonth.getYear(),
                currentDisplayedYearMonth.getMonth(), FIRST_DATE_OF_MONTH);
        while (!firstSundayDate.getDayOfWeek().toString().equals("SUNDAY")) {
            firstSundayDate = firstSundayDate.minusDays(1);
        }
        return firstSundayDate;
    }

    /**
     * Sets the display date of the specified {@code CalendarDateCell} to the
     * date represented by the specified {@code LocalDate}.
     * @param calendarDateCell The specified {@code CalendarDateCell}.
     * @param calendarDate The specified {@code LocalDate}.
     */
    private void setDisplayDate(CalendarDateCell calendarDateCell, LocalDate calendarDate) {
        StackPane calendarDatePane = calendarDateCell.getCalendarDatePane();
        calendarDatePane.getChildren().clear();
        Text dateText = new Text(calendarDate.getDayOfMonth() + "");
        StackPane.setAlignment(dateText, Pos.TOP_LEFT);
        calendarDatePane.getChildren().add(dateText);
    }

    /**
     * Adds all engagements which occur on the date represented by the specified {@code LocalDate}
     * to the specified {@code CalendarDateCell}.
     * @param calendarDateCell The specified {@CalendarDate}.
     * @param calendarDate The specified {@CalendarDateCell}.
     */
    private void addAllEngagementsForDate(CalendarDateCell calendarDateCell, LocalDate calendarDate) {
        calendarDateCell.clearEngagements();
        for (Engagement engagement : engagements) {
            LocalDateTime startDateTime = engagement.getTimeSlot().getStartTime();
            if (startDateTime.getDayOfMonth() == calendarDate.getDayOfMonth()
                    && startDateTime.getMonthValue() == calendarDate.getMonthValue()
                    && startDateTime.getYear() == calendarDate.getYear()) {
                calendarDateCell.addEngagement(engagement);
            }
        }
    }

    /**
     * Updates the specified {@code CalendarDatePane} to display the number of engagements
     * in the specified {@code CalendarDateCell}.
     * @param calendarDateCell The specified {@code CalendarDateCell}.
     */
    private void updateEngagementCountDisplay(CalendarDateCell calendarDateCell) {
        if (calendarDateCell.getNumberOfEngagements() > 0) {
            Text engagementCountDisplay = new Text(calendarDateCell.getNumberOfEngagements() + " engagement(s)");
            StackPane.setAlignment(engagementCountDisplay, Pos.CENTER);
            calendarDateCell.getCalendarDatePane().getChildren().add(engagementCountDisplay);
        }
    }

    /**
     * Populates the calendar with information about the next month.
     */
    @FXML
    public void populateCalendarWithNextMonth() {
        currentDisplayedYearMonth = currentDisplayedYearMonth.plusMonths(1);
        populateCalendar();
    }

    /**
     * Populates the calendar with information about the previous month.
     */
    @FXML
    public void populateCalendarWithPreviousMonth() {
        currentDisplayedYearMonth = currentDisplayedYearMonth.minusMonths(1);
        populateCalendar();
    }

    /**
     * Opens a window which displays the engagements on the specified date.
     * @param date The specified date.
     */
    public void display(LocalDate date) {
        for (CalendarDateCell calendarDateCell : calendarDateCells) {
            if (calendarDateCell.getDate().equals(date)) {
                calendarDateCell.displayEngagements();
                return;
            }
        }
        throw new IllegalArgumentException("Please choose a date within the displayed calendar month"
                + " to show engagements for.");
    }

    /**
     * Closes all displayed daily engagement lists, if any.
     */
    @Override
    public void handleExit() {
        for (CalendarDateCell calendarDateCell : calendarDateCells) {
            calendarDateCell.closeDisplayedEngagements();
        }
    }

}
