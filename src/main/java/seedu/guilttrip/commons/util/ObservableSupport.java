package seedu.guilttrip.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.guilttrip.commons.core.LogsCenter;

/**
 * Functions similar to PropertyChange in that it uses the Observable pattern,
 * but modified to always fire when asked to.
 */
public class ObservableSupport {
    private List<ListenerSupport> supportList;
    private final Logger logger = LogsCenter.getLogger(getClass());
    public ObservableSupport() {
        supportList = new ArrayList<>();
    }
    public void addPropertyChangeListener(ListenerSupport listener) {
        supportList.add(listener);
    }
    public void removePropertyChangeListener(ListenerSupport listener) {
        supportList.remove(listener);
    }

    /**
     * Notifies all listeners observing the object of a change.
     * @param propertyName
     * @param oldValue
     * @param newValue
     */
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        logger.info("Firing Property Change");
        for (ListenerSupport listener : supportList) {
            listener.propertyChange(new Evt(propertyName, oldValue, newValue));
        }

    }

    /**
     * Get List of listening for property changes.
     * @return
     */
    public List<ListenerSupport> getPropertyChangeListeners() {
        return supportList;
    }

    /**
     * Stores information to be transmitted to listeners.
     */
    public class Evt {
        final String propertyName;
        final Object oldValue;
        final Object newValue;
        public Evt(String propertyName, Object oldValue, Object newValue) {
            this.propertyName = propertyName;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }
        public String getPropertyName() {
            return propertyName;
        }

        public Object getOldValue() {
            return this.oldValue;
        }

        public Object getNewValue() {
            return newValue;
        }
    }
}
