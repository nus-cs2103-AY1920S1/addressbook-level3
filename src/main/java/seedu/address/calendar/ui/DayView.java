package seedu.address.calendar.ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.address.ui.UiPart;

public class DayView extends UiPart<Region> {
    private static final String FXML = "CalendarDayView.fxml";
    private static final double BREAKPOINT_WIDTH = 405.0;
    private static final double MAX_CIRCLES_PER_ROW = 2; // only if the view is small
    private ReadOnlyDoubleProperty monthViewWidth;
    private final ChangeListener listener;

    @FXML
    private Label dayOfMonth;
    @FXML
    private HBox eventLabels;
    @FXML
    private Circle commitment;
    @FXML
    private Circle holiday;
    @FXML
    private Circle schoolBreak;
    @FXML
    private Circle trip;

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

    private void setInvisible(Circle eventLabel) {
        eventLabel.setManaged(false);
        eventLabel.setVisible(false);
    }

    public void removeListener() {
        monthViewWidth.removeListener(listener);
    }

    private void showSmall() {
        showView(true);
    }

    private void showLarge() {
        showView(false);
        eventLabels.setSpacing(0.005 * monthViewWidth.get());
    }

    private void showView(boolean isSmall) {
        eventLabels.setVisible(!isSmall);
        eventLabels.setManaged(!isSmall);
    }
}
