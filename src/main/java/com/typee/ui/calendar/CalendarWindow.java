package com.typee.ui.calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.commons.util.DateUtil;
import com.typee.model.engagement.Engagement;
import com.typee.ui.UiPart;
import com.typee.ui.calendar.exceptions.CalendarInteractionException;

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
    private static final String MAXIMUM_YEAR_MESSAGE = "The maximum allowable calendar year is 9999.";
    private static final String MINIMUM_YEAR_MESSAGE = "The minimum allowable calendar year is 0001.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private GridPane dateDisplayGrid;

    @FXML
    private Text calendarTitle;

    private List<CalendarDateCell> calendarDateCells;
    private ObservableList<Engagement> engagements;
    private YearMonth currentDisplayedYearMonth;

    /**
     * Constructs a calendar window with the current month as reference.
     */
    public CalendarWindow(ObservableList<Engagement> engagements) {
        super(FXML);
        calendarDateCells = new ArrayList<>();
        this.engagements = engagements;
        engagements.addListener((ListChangeListener<? super Engagement>) change -> populateCalendar());
        currentDisplayedYearMonth = YearMonth.now();
        initializeUiDisplay();
        populateCalendar();
    }

    /**
     * Initializes the UI elements of the calendar window to be displayed.
     */
    private void initializeUiDisplay() {
        for (int i = 0; i < MAXIMUM_NUMBER_OF_WEEKS_PER_MONTH; i++) {
            for (int j = 0; j < NUMBER_OF_DAYS_IN_A_WEEK; j++) {
                CalendarDateCell calendarDateCell = new CalendarDateCell();
                calendarDateCells.add(calendarDateCell);
                StackPane calendarDateStackPane = calendarDateCell.getCalendarDateStackPane();
                dateDisplayGrid.add(calendarDateStackPane, j, i);
            }
        }
    }

    /**
     * Populates the calendar based on the currently displayed year and month.
     */
    private void populateCalendar() {
        updateCalendarTitle();
        LocalDate calendarDate = getDateOfFirstSundayToBeDisplayed();
        for (CalendarDateCell calendarDateCell : calendarDateCells) {
            setDate(calendarDateCell, calendarDate);
            addAllEngagementsForDate(calendarDateCell, calendarDate);
            calendarDateCell.updateEngagementCountDisplay();
            calendarDate = calendarDate.plusDays(1);
        }
    }

    /**
     * Updates the calendar's title to reflect the current displayed month and year.
     */
    private void updateCalendarTitle() {
        String calendarTitleText = String.format("%s %04d", currentDisplayedYearMonth.getMonth().toString(),
                currentDisplayedYearMonth.getYear());
        calendarTitle.setText(calendarTitleText);
    }

    /**
     * Returns a {@code LocalDate} instance representing the first Sunday to be displayed.
     * This Sunday is defined as the Sunday before or on the first day of the month.
     *
     * @return A {@code LocalDate} instance representing the first Sunday to be displayed.
     */
    private LocalDate getDateOfFirstSundayToBeDisplayed() {
        LocalDate dateOfFirstSundayToBeDisplayed = LocalDate.of(currentDisplayedYearMonth.getYear(),
                currentDisplayedYearMonth.getMonth(), FIRST_DATE_OF_MONTH);
        while (!dateOfFirstSundayToBeDisplayed.getDayOfWeek().toString().equals("SUNDAY")) {
            dateOfFirstSundayToBeDisplayed = dateOfFirstSundayToBeDisplayed.minusDays(1);
        }
        return dateOfFirstSundayToBeDisplayed;
    }

    /**
     * Sets the display date of the specified {@code CalendarDateCell} to the
     * date represented by the specified {@code LocalDate}.
     *
     * @param calendarDateCell The specified {@code CalendarDateCell}.
     * @param calendarDate The specified {@code LocalDate}.
     */
    private void setDate(CalendarDateCell calendarDateCell, LocalDate calendarDate) {
        StackPane calendarDateStackPane = calendarDateCell.getCalendarDateStackPane();
        calendarDateStackPane.getChildren().clear();
        Text dateText = new Text(String.format("%02d", calendarDate.getDayOfMonth()));
        StackPane.setAlignment(dateText, Pos.TOP_LEFT);
        calendarDateStackPane.getChildren().add(dateText);
        calendarDateCell.setDate(calendarDate);
    }

    /**
     * Adds all engagements which occur on the date represented by the specified {@code LocalDate}
     * to the specified {@code CalendarDateCell}.
     *
     * @param calendarDateCell The specified {@CalendarDate}.
     * @param calendarDate The specified {@CalendarDateCell}.
     */
    private void addAllEngagementsForDate(CalendarDateCell calendarDateCell, LocalDate calendarDate) {
        calendarDateCell.clearEngagements();
        for (Engagement engagement : engagements) {
            if (DateUtil.isWithinTimeSlot(calendarDate, engagement.getTimeSlot())) {
                calendarDateCell.addEngagement(engagement);
            }
        }
    }

    /**
     * Populates the calendar with information about the next month.
     *
     * @throws CalendarInteractionException If the next month falls outside the maximum allowable year.
     */
    public void populateCalendarWithNextMonth() throws CalendarInteractionException {
        if (DateUtil.isValidYear(currentDisplayedYearMonth.plusMonths(1).getYear())) {
            closeAllDisplayedEngagementWindows();
            currentDisplayedYearMonth = currentDisplayedYearMonth.plusMonths(1);
            populateCalendar();
            logger.info("Switching calendar view to next month.");
        } else {
            throw new CalendarInteractionException(MAXIMUM_YEAR_MESSAGE);
        }
    }

    /**
     * Changes the calendar window's display to the next month.
     */
    @FXML
    private void changeCalendarDisplayToNextMonth() {
        try {
            populateCalendarWithNextMonth();
        } catch (CalendarInteractionException e) {
            return;
        }
    }

    /**
     * Populates the calendar with information about the previous month.
     *
     * @throws CalendarInteractionException If the previous month falls outside the minimum allowable year.
     */
    public void populateCalendarWithPreviousMonth() throws CalendarInteractionException {
        if (DateUtil.isValidYear(currentDisplayedYearMonth.minusMonths(1).getYear())) {
            closeAllDisplayedEngagementWindows();
            currentDisplayedYearMonth = currentDisplayedYearMonth.minusMonths(1);
            populateCalendar();
            logger.info("Switching calendar view to previous month.");
        } else {
            throw new CalendarInteractionException(MINIMUM_YEAR_MESSAGE);
        }
    }

    /**
     * Changes the calendar window's display to the previous month.
     */
    @FXML
    private void changeCalendarDisplayToPreviousMonth() {
        try {
            populateCalendarWithPreviousMonth();
        } catch (CalendarInteractionException e) {
            return;
        }
    }

    /**
     * Opens a window which displays the engagements on the specified date.
     *
     * @param date The specified date.
     */
    public void openSingleDayEngagementsDisplayWindow(LocalDate date) {
        if (!DateUtil.hasSameMonthAndYear(currentDisplayedYearMonth, date)) {
            closeAllDisplayedEngagementWindows();
        }
        currentDisplayedYearMonth = YearMonth.of(date.getYear(), date.getMonthValue());
        populateCalendar();
        for (CalendarDateCell calendarDateCell : calendarDateCells) {
            if (calendarDateCell.getDate().equals(date)) {
                calendarDateCell.displayEngagements();
                logger.info(String.format("Opening engagements display for %s",
                        DateUtil.getFormattedDateString(date)));
            }
        }
    }

    /**
     * Closes the single day engagements window for the specified date.
     *
     * @param date The specified date.
     * @throws CalendarInteractionException If there is no open window for the specified date.
     */
    public void closeSingleDayEngagementsDisplayWindow(LocalDate date) throws CalendarInteractionException {
        for (CalendarDateCell calendarDateCell : calendarDateCells) {
            if (calendarDateCell.getDate().equals(date)
                    && calendarDateCell.hasOpenEngagementsDisplay()) {
                calendarDateCell.closeDisplayedEngagements();
                logger.info(String.format("Closing engagements display for %s",
                        DateUtil.getFormattedDateString(date)));
                return;
            }
        }
        String formattedDateString = DateUtil.getFormattedDateString(date);
        throw new CalendarInteractionException("There is no open engagements display window for "
                + formattedDateString);
    }

    /**
     * Handles the exit process for this calendar window.
     */
    @Override
    public void handleExit() {
        closeAllDisplayedEngagementWindows();
    }

    /**
     * Closes all displayed engagement windows.
     */
    private void closeAllDisplayedEngagementWindows() {
        for (CalendarDateCell calendarDateCell : calendarDateCells) {
            calendarDateCell.closeDisplayedEngagements();
        }
    }

}
