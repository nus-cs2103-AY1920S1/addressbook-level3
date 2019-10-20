package seedu.address.model;

import java.util.Calendar;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Stores a property to set the date to display on the calendar panel.
 */
public class CalendarDate {

    private SimpleObjectProperty<Calendar> property = new SimpleObjectProperty<>();

    public CalendarDate(Calendar calendar) {
        property.set(calendar);
    }

    public void setCalendar(Calendar calendar) {
        property.set(calendar);
    }

    public SimpleObjectProperty<Calendar> getProperty() {
        return this.property;
    }

    public Calendar getCalendar() {
        return property.get();
    }
}
