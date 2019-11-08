package com.typee.ui.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.model.engagement.Engagement;
import com.typee.ui.UiPart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Represents a calendar date cell in the calendar window.
 */
public class CalendarDateCell extends UiPart<Region> {

    private static final String FXML = "CalendarDateCell.fxml";

    @FXML
    private StackPane calendarDateStackPane;

    private LocalDate date;
    private ObservableList<Engagement> engagements;
    private SingleDayEngagementsDisplayWindow engagementsDisplayWindow;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Constructs a calendar date cell.
     */
    public CalendarDateCell() {
        super(FXML);
        engagements = FXCollections.observableList(new ArrayList<>());
        engagementsDisplayWindow = new SingleDayEngagementsDisplayWindow(engagements);
    }

    /**
     * Returns the {@code StackPane} that is used to mount this {@code CalendarDateCell}.
     * @return The {@code StackPane} that is used to mount this {@code CalendarDateCell}.
     */
    public StackPane getCalendarDateStackPane() {
        return calendarDateStackPane;
    }

    /**
     * Adds the specified engagement to this {@code CalendarDateCell}.
     * @param engagement The specified engagement.
     */
    public void addEngagement(Engagement engagement) {
        engagements.add(engagement);
    }

    /**
     * Clears all engagements from this {@code CalendarDateCell}.
     */
    public void clearEngagements() {
        engagements.clear();
    }

    /**
     * Returns the number of engagements in this {@code CalendarDateCell}.
     * @return The number of engagements in this {@code CalendarDateCell}.
     */
    public int getNumberOfEngagements() {
        return engagements.size();
    }

    /**
     * Sets the date of this {@code CalendarDateCell} to the specified date.
     * @param date The specified date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
        engagementsDisplayWindow.setDate(date);
    }

    /**
     * Returns the date of this {@code CalendarDateCell}.
     * @return The date of this {@code CalendarDateCell}.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Closes the displayed engagements list if it is opened.
     */
    public void closeDisplayedEngagements() {
        engagementsDisplayWindow.hide();
    }

    /**
     * Displays the engagements for the day represented by this {@code CalendarDateCell}.
     */
    @FXML
    public void displayEngagements() {
        if (!engagementsDisplayWindow.isShowing()) {
            engagementsDisplayWindow.show();
        } else {
            engagementsDisplayWindow.focus();
        }
    }

    /**
     * Returns true if this calendar date cell has an open engagements display window.
     * @return True if this calendar date cell has an open engagements display window.
     */
    public boolean hasOpenEngagementsDisplay() {
        return engagementsDisplayWindow.isShowing();
    }

}
