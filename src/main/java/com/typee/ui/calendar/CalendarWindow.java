package com.typee.ui.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.logic.Logic;
import com.typee.model.engagement.Engagement;
import com.typee.ui.UiPart;

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
    private static final String FIRST_DAY_TO_DISPLAY = "SUNDAY";
    private static final int FIRST_DATE_OF_MONTH = 1;
    private static final int MAXIMUM_NUMBER_OF_DAYS_PER_MONTH = 5;
    private static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private GridPane dateDisplayGrid;

    @FXML
    private Text calendarTitle;

    private List<Engagement> engagementList;
    private List<StackPane> allCalendarDays;
    private Logic logic;
    private YearMonth currentDisplayedYearMonth;

    /**
     * Constructs a calendar window.
     */
    public CalendarWindow() {
        super(FXML);
        allCalendarDays = new ArrayList<>();
        engagementList = new ArrayList<>();
    }

    /**
     * Returns this calendar window's logic component.
     * @return This calendar window's logic component.
     */
    public Logic getLogic() {
        return logic;
    }

    /**
     * Sets this calendar window's logic component to the specified logic instance.
     * @param logic The specified logic instance.
     */
    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    /**
     * Populates the calendar with the information to be displayed.
     */
    public void populateCalendar() {
        for (Engagement engagement : logic.getFilteredEngagementList()) {
            engagementList.add(engagement);
        }
        currentDisplayedYearMonth = YearMonth.now();
        initializeDateDisplayGrid();
        populateCalendarWithSpecifiedMonth(currentDisplayedYearMonth);
    }

    /**
     * Initializes the date display grid with the current month.
     */
    private void initializeDateDisplayGrid() {
        for (int i = 0; i < MAXIMUM_NUMBER_OF_DAYS_PER_MONTH; i++) {
            for (int j = 0; j < NUMBER_OF_DAYS_IN_A_WEEK; j++) {
                StackPane individualDateStackPane = new IndividualDatePane().getIndividualDateStackPane();
                dateDisplayGrid.add(individualDateStackPane, j, i);
                allCalendarDays.add(individualDateStackPane);
            }
        }
    }

    /**
     * Populates the calendar based on the specified {@code YearMonth}
     * @param yearMonth The specified {@code YearMonth}
     */
    private void populateCalendarWithSpecifiedMonth(YearMonth yearMonth) {
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(),
                FIRST_DATE_OF_MONTH);
        while (!calendarDate.getDayOfWeek().toString().equals(FIRST_DAY_TO_DISPLAY)) {
            calendarDate = calendarDate.minusDays(1);
        }
        for (StackPane individualDateStackPane : allCalendarDays) {
            if (individualDateStackPane.getChildren().size() > 0) {
                individualDateStackPane.getChildren().clear();
            }
            Text dateText = new Text(calendarDate.getDayOfMonth() + "");
            StackPane.setAlignment(dateText, Pos.TOP_LEFT);
            individualDateStackPane.getChildren().add(dateText);
            for (Engagement engagement : engagementList) {
                LocalDateTime startDateTime = engagement.getStartTime();
                if (startDateTime.getDayOfMonth() == calendarDate.getDayOfMonth()
                        && startDateTime.getMonth().equals(calendarDate.getMonth())) {
                    Text appointmentDescription = new Text(engagement.getDescription());
                    StackPane.setAlignment(appointmentDescription, Pos.CENTER);
                    individualDateStackPane.getChildren().add(appointmentDescription);
                }
            }
            calendarDate = calendarDate.plusDays(1);
        }
        calendarTitle.setText(currentDisplayedYearMonth.getMonth().toString() + " "
                + currentDisplayedYearMonth.getYear());
    }

    /**
     * Populates the calendar with information about the next month.
     */
    @FXML
    private void populateCalendarWithNextMonth() {
        currentDisplayedYearMonth = currentDisplayedYearMonth.plusMonths(1);
        populateCalendarWithSpecifiedMonth(currentDisplayedYearMonth);
    }

    /**
     * Populates the calendar with information about the previous month.
     */
    @FXML
    private void populateCalendarWithPreviousMonth() {
        currentDisplayedYearMonth = currentDisplayedYearMonth.minusMonths(1);
        populateCalendarWithSpecifiedMonth(currentDisplayedYearMonth);
    }

}
