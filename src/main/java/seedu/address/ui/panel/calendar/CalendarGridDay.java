package seedu.address.ui.panel.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays the day on the calendar.
 */
public class CalendarGridDay extends UiPart<Region> {

    private static final float BASE_SATURATION = 0.25f;
    private static final float MAX_SATURATION = 0.75f;
    private static final int MAX_EVENT = 10;
    private static final String FXML = "CalendarGridDay.fxml";
    private Integer day;
    private Integer month;
    private Integer year;
    private Integer totalEvents;

    @FXML
    private Circle calendarDayCircle;

    @FXML
    private Label calendarDay;

    public CalendarGridDay(Integer day, Integer month, Integer year, Integer totalEvents) {
        super(FXML);
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalEvents = totalEvents;
        calendarDay.setText(day.toString());
        colorChange();
    }

    public void addAnEvent() {
        this.totalEvents++;
        colorChange();
    }

    public Integer[] getDayMonthYear() {
        Integer[] dayMonthYear = {day, month, year};
        return dayMonthYear;
    }

    private void colorChange() {
        ColorAdjust colorAdjust = new ColorAdjust();
        if(this.totalEvents == 0) {
            calendarDayCircle.setStyle("-fx-opacity: " + 0);
            calendarDayCircle.setEffect(colorAdjust);
        } else {
            calendarDayCircle.setStyle("-fx-opacity: " + 0.5);
            colorAdjust.setSaturation(getSaturationValue(this.totalEvents, MAX_EVENT));
            calendarDayCircle.setEffect(colorAdjust);
        }
    }

    private float getSaturationValue(float events, float max) {
        return (events / max) * (MAX_SATURATION - BASE_SATURATION) + BASE_SATURATION;
    }


}
