package seedu.address.commons.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * Class that constantly calls method to update current date.
 */
public class TimeUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static TimeUtil tracker;
    private static LocalDate currDate;
    private static final ScheduledExecutorService ses = Executors.newScheduledThreadPool(0);
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private Runnable getCurrDate = () -> {
        LocalDate newCurrDate = LocalDate.now();
        support.firePropertyChange("currDate", currDate, newCurrDate);
        currDate = newCurrDate;
    };

    public static TimeUtil getTracker() {
        if (tracker == null) {
            return new TimeUtil();
        } else {
            return tracker;
        }
    }

    /**
     * Starts timer to constantly update the current Date;
     */
    public static void startTimer() {
        logger.info("Timer starts");
        if (tracker == null) {
            tracker = new TimeUtil();
        }
        tracker.getCurrDate.run();
        ses.scheduleWithFixedDelay(tracker.getCurrDate, 0, 1, TimeUnit.HOURS);
    }

    /**
     * Manually updates currDate;
     */
    public static void manualUpdate() {
        tracker.getCurrDate.run();
    }

    /**
     * ends timer
     */
    public static void endTimer() {
        ses.shutdownNow();
        logger.info("Timer ends");
    }
    public PropertyChangeSupport getSupport() {
        return support;
    }

    /**
     * Updates local time to new listener.
     * @param pcl
     */
    public static void addPropertyChangeListener(PropertyChangeListener pcl) {
        tracker.support.addPropertyChangeListener(pcl);
        manualUpdate();
    }
    public static void removePropertyChangeListener(PropertyChangeListener pcl) {
        tracker.support.removePropertyChangeListener(pcl);
    }
}
