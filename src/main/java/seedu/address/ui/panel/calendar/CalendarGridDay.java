package seedu.address.ui.panel.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import seedu.address.model.CalendarDate;
import seedu.address.ui.UiPart;

//@@author Kyzure
/**
 * An UI component that displays the day on the calendar.
 */
public class CalendarGridDay extends UiPart<Region> {

    private static final float BASE_SATURATION = 0.10f;
    private static final float MAX_SATURATION = 0.90f;
    private static final int MAX_EVENT = 10;
    private static final String FXML = "CalendarGridDay.fxml";

    private CalendarDate calendarDate;
    private Integer totalEvents;

    @FXML
    private StackPane calendarGridDayBase;

    @FXML
    private Circle calendarDayCircle;

    @FXML
    private Label calendarDay;

    public CalendarGridDay(CalendarDate calendarDate, Integer totalEvents) {
        super(FXML);
        this.calendarDate = calendarDate;
        this.totalEvents = totalEvents;
        calendarDay.setText(calendarDate.getDay().toString());
        colorChange();
    }

    /**
     * Increases the total number of Events or Tasks and changes the color accordingly.
     */
    public void increaseIntensity() {
        this.totalEvents++;
        if (!CalendarDate.now().equals(calendarDate)) {
            colorChange();
        }
    }

    /**
     * Resets the color of the day.
     */
    public void resetColor() {
        this.totalEvents = 0;
        colorChange();
    }

    public void setCurrentDate() {
        Circle circle = new Circle();
        circle.setRadius(14);
        circle.setStyle("-fx-fill: " + "-currentDateColor");
        calendarGridDayBase.getChildren().add(0, circle);

    }

    /**
     * Reduces the opacity as the given Calendar Screen is of a different month.
     */
    public void reduceOpacity() {
        calendarDay.setStyle("-fx-opacity: " + 0.25);
    }

    /**
     * Adjusts the color of the circle to indicate how many events are there on that day.
     */
    private void colorChange() {
        ColorAdjust colorAdjust = new ColorAdjust();
        if (this.totalEvents == 0) {
            calendarDayCircle.setStyle("-fx-opacity: " + 0);
            colorAdjust.setSaturation(getSaturationValue(this.totalEvents));
            calendarDayCircle.setEffect(colorAdjust);
        } else {
            calendarDayCircle.setStyle("-fx-opacity: " + 0.5);
            colorAdjust.setSaturation(getSaturationValue(this.totalEvents));
            calendarDayCircle.setEffect(colorAdjust);
        }
    }

    /**
     * Returns a float value of a saturation by the number of given events against a threshold value.
     *
     * @param events The total number of events of the current day.
     * @return Returns a float value of a saturation by the number of given events against a threshold value.
     */
    private float getSaturationValue(float events) {
        return (events / MAX_EVENT) * (MAX_SATURATION - BASE_SATURATION) + BASE_SATURATION;
    }

}
