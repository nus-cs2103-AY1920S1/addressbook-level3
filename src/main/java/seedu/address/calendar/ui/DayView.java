package seedu.address.calendar.ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * Creates the day view for the calendar. Each day view is labelled with the relevant event type.
 */
public class DayView extends UiPart<Region> {
    private static final String FXML = "CalendarDayView.fxml";
    private static final double BREAKPOINT_WIDTH = 450.0;
    private ReadOnlyDoubleProperty monthViewWidth;
    private final ChangeListener listener;

    @FXML
    private Label dayOfMonth;
    @FXML
    private HBox eventLabels;
    @FXML
    private Group commitment;
    @FXML
    private Group holiday;
    @FXML
    private Group schoolBreak;
    @FXML
    private Group trip;

    /**
     * Creates a day view.
     *
     * @param date The date which the day view represents
     * @param monthViewWidth The width of the month view
     * @param hasCommitment Indicates whether the user has a commitment on the day
     * @param hasHoliday Indicates whether the user has a holiday on the day
     * @param hasSchoolBreak Indicates whether the user has a school break on the day
     * @param hasTrip Indicates whether the user has a trip on the day
     */
    public DayView(int date, ReadOnlyDoubleProperty monthViewWidth, boolean hasCommitment, boolean hasHoliday,
                   boolean hasSchoolBreak, boolean hasTrip) {
        super(FXML);

        this.monthViewWidth = monthViewWidth;

        // set listener to determine whether the screen size is too small to show labels
        listener = (observable, oldValue, newValue) -> {
            if ((Double) newValue < BREAKPOINT_WIDTH) {
                showSmall();
            } else {
                showLarge();
            }
        };
        monthViewWidth.addListener(listener);

        setEventLabels(hasCommitment, hasHoliday, hasSchoolBreak, hasTrip);

        // show current day of month
        dayOfMonth.setText(Integer.toString(date));

        // determine view
        if (monthViewWidth.get() < BREAKPOINT_WIDTH) {
            showSmall();
        } else {
            showLarge();
        }
    }

    /**
     * Sets the necessary labels to indicate the event types for {@code this}.
     *
     * @param hasCommitment Indicates whether the user has a commitment on the day
     * @param hasHoliday Indicates whether the user has a holiday on the day
     * @param hasSchoolBreak Indicates whether the user has a school break on the day
     * @param hasTrip Indicates whether the user has a trip on the day
     */
    private void setEventLabels(boolean hasCommitment, boolean hasHoliday, boolean hasSchoolBreak, boolean hasTrip) {
        if (!hasCommitment) {
            setInvisible(commitment);
        }

        if (!hasHoliday) {
            setInvisible(holiday);
        }

        if (!hasSchoolBreak) {
            setInvisible(schoolBreak);
        }

        if (!hasTrip) {
            setInvisible(trip);
        }
    }

    /**
     * Sets the specified label has invisible.
     *
     * @param eventLabel The specified label
     */
    private void setInvisible(Group eventLabel) {
        eventLabel.setManaged(false);
        eventLabel.setVisible(false);
    }

    /**
     * Removes listener from {@code this}.
     */
    public void removeListener() {
        monthViewWidth.removeListener(listener);
    }

    /**
     * Shows the calendar view (without labels) for a small window screen.
     */
    private void showSmall() {
        showView(true);
    }

    /**
     * Shows the calendar view (with labels) for a large window screen.
     */
    private void showLarge() {
        showView(false);
        eventLabels.setSpacing(0.005 * monthViewWidth.get());
    }

    /**
     * Shows the day view
     * @param isSmall Indicates whether the current view is small
     */
    private void showView(boolean isSmall) {
        eventLabels.setVisible(!isSmall);
        eventLabels.setManaged(!isSmall);
    }
}
