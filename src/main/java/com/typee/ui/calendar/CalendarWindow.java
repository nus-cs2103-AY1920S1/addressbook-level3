package com.typee.ui.calendar;

import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.ui.UiPart;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * The calendar window.
 * Solution below adapted from https://github.com/SirGoose3432/javafx-calendar
 */
public class CalendarWindow extends UiPart<Region> {

    public static final String FXML = "CalendarWindow.fxml";
    private static final int MAXIMUM_NUMBER_OF_DAYS_PER_MONTH = 5;
    private static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private GridPane dateDisplayGrid;

    /**
     * Constructs a calendar window using the calendar's FXML file path.
     */
    public CalendarWindow() {
        super(FXML);
    }

    /**
     * Initializes the calendar window to display individual dates of the current month.
     */
    @FXML
    public void initialize() {
        initializeDateDisplayGridWithCurrentMonth();
    }

    /**
     * Initializes the date display grid with the current month.
     */
    private void initializeDateDisplayGridWithCurrentMonth() {
        for (int i = 0; i < MAXIMUM_NUMBER_OF_DAYS_PER_MONTH; i++) {
            for (int j = 0; j < NUMBER_OF_DAYS_IN_A_WEEK; j++) {
                dateDisplayGrid.add(new IndividualDatePane().getIndividualDatePane(), j, i);
            }
        }
    }

}
