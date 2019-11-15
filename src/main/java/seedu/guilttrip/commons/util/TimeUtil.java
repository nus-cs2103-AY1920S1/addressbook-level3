package seedu.guilttrip.commons.util;

import java.time.LocalDate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import seedu.guilttrip.MainApp;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.entry.Date;

/**
 * Class that constantly calls method to update current date.
 */
public class TimeUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static TimeUtil tracker;
    private static LocalDate currDate;
    private static final ScheduledExecutorService ses = Executors.newScheduledThreadPool(0);
    private ObservableSupport support = new ObservableSupport();
    private Runnable getCurrDate = () -> {
        LocalDate newCurrDate = LocalDate.now();
        support.firePropertyChange("currDate", null, newCurrDate);
        currDate = newCurrDate;
    };


    public static LocalDate getLastRecordedDate() {
        return currDate;
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
        tracker.support.firePropertyChange("currDate", null, currDate);
    }

    /**
     * ends timer
     */
    public static void endTimer() {
        ses.shutdownNow();
        logger.info("Timer ends");
    }


    public ObservableSupport getSupport() {
        return support;
    }

    /**
     * Updates local time to new listener.
     * @param pcl
     */
    public static void addPropertyChangeListener(ListenerSupport pcl) {
        tracker.support.addPropertyChangeListener(pcl);
    }
    public static void removePropertyChangeListener(ListenerSupport pcl) {
        tracker.support.removePropertyChangeListener(pcl);
    }

    /**
     * For testing reminders
     */
    public static void forceSetDate(Date date) {
        currDate = date.getDate();
        tracker.support.firePropertyChange("currDate", null, currDate);
    }
}
