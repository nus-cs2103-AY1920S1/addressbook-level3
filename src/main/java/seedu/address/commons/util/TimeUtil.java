package seedu.address.commons.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class that constantly calls method to update current date.
 */
public class TimeUtil {
    private ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private LocalDate currDate;
    private Runnable getCurrDate = () -> {
        LocalDate newCurrDate = LocalDate.now();
        support.firePropertyChange("currDate", currDate, newCurrDate);
        currDate = newCurrDate;
    };

    public TimeUtil() {
        startTimer();
    }

    /**
     * Starts timer to constantly update the current Date;
     */
    public void startTimer() {
        getCurrDate.run();
        ses.schedule(getCurrDate, 5, TimeUnit.SECONDS);
    }

    /**
     * Manually updates currDate;
     */
    public void manualUpdate() {
        getCurrDate.run();
    }
    public void endTimer() {
        ses.shutdown();
    }
    public PropertyChangeSupport getSupport() {
        return support;
    }

    /**
     * Updates local time to new listener.
     * @param pcl
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
        manualUpdate();
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
