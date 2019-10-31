package dukecooks.commons.core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Singleton Event Handler class that manages all event changes in the Main App.
 * There should only be one instance present throughout.
 */
public class Event {
    private static Event event;

    private static String mode;
    private static String type;
    private static final Logger logger = LogsCenter.getLogger(Object.class);

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private Event() {
        //default start up screen
        this.type = "dashboard";
        this.mode = "all";
    }

    public static synchronized Event getInstance() {
        if (event == null) {
            event = new Event();
        }
        return event;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    public String getType() {
        return this.type;
    }

    public String getMode() {
        return this.mode;
    }

    public void set(String mode, String type) {
        if (!this.type.equals(type) || !this.mode.equals(mode)) {
            this.type = type;
            this.mode = mode;
            changes.firePropertyChange("event", false, true);
        }
    }

    public void set(String mode) {
        if (!this.type.equals(type)) {
            this.type = type;
            changes.firePropertyChange("event", false, true);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Event)) { //this handles null as well.
            return false;
        }

        Event o = (Event) other;

        return Objects.equals(type, o.type)
                && Objects.equals(mode, o.mode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, mode);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Type: " + type + "\n");
        sb.append("Mode : " + mode + "\n");
        return sb.toString();
    }
}
