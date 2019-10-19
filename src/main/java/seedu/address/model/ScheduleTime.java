package seedu.address.model;

import java.util.Calendar;

import javafx.beans.property.SimpleObjectProperty;

public class ScheduleTime {

    private SimpleObjectProperty<Calendar> property = new SimpleObjectProperty<>();

    public ScheduleTime(Calendar calendar) {
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
