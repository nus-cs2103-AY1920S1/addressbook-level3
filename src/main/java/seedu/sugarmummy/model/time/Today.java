package seedu.sugarmummy.model.time;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents the date of today.
 */
public class Today {
    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();

    public Today() {
        date.set(LocalDate.now());
    }

    public ObjectProperty<LocalDate> getDateProperty() {
        return date;
    }

    public void refresh() {
        date.set(LocalDate.now());
    }

    public LocalDate getDate() {
        return date.get();
    }
}
