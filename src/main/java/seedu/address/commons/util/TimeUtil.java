package seedu.address.commons.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * Class that constantly calls method to update current date.
 */
public class TimeUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static TimeUtil tracker;
    private static ScheduledExecutorService ses = Executors.newScheduledThreadPool(0);
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private LocalDate currDate;
    private Runnable getCurrDate = () -> {
        LocalDate newCurrDate = LocalDate.now();
        support.firePropertyChange("currDate", currDate, newCurrDate);
        currDate = newCurrDate;
    };

    private TimeUtil() {
        startTimer();
        tracker = this;
    }

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
    public void startTimer() {
        getCurrDate.run();
        //ses.schedule(getCurrDate, 1, TimeUnit.SECONDS);
    }

    /**
     * Manually updates currDate;
     */
    public void manualUpdate() {
        getCurrDate.run();
    }

    /**
     * ends timer
     */
    public static void endTimer() {
        ses.shutdown();
        logger.info("Timer ends");
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
