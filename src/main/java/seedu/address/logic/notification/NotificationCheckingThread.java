package seedu.address.logic.notification;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.systemtray.PopupListener;
import seedu.address.ui.systemtray.PopupNotification;

/**
 * A thread that handles checking for notifications and notifying the appropriate listeners
 */
public class NotificationCheckingThread extends Thread {
    private static final Logger logger = LogsCenter.getLogger(NotificationCheckingThread.class);
    private static final long millisecondsInAMinute = 60000;

    private boolean notificationsOn = true;
    private ArrayList<PopupListener> popupListeners = new ArrayList<>();

    private NotificationChecker notificationChecker;

    public NotificationCheckingThread(NotificationChecker notificationChecker) {
        this.notificationChecker = notificationChecker;
    }

    public void addPopupListener(PopupListener popupListener) {
        popupListeners.add(popupListener);
    }

    public void switchOffNotifications() {
        notificationsOn = false;
    }

    public void switchOnNotifications() {
        notificationsOn = true;
    }

    public boolean getNotificationsOnStatus() {
        return notificationsOn;
    }

    @Override
    public void run() {
        try {
            notificationCheckingLoop();
        } catch (InterruptedException e) {
            logger.info("NotificationManagingThread successfully interrupted.");
        }
    }

    /**
     * The main loop of the program.
     * @throws InterruptedException If this thread is interrupted.
     */
    private void notificationCheckingLoop() throws InterruptedException {
        while (true) {
            if (notificationsOn) {
                checkAndPostNotifications();

                Thread.sleep(findMillisecondsToNextMinute());
            }
        }
    }

    /**
     * Finds the notifications that require need to be posted, and notifies the relevant listeners.
     */
    private void checkAndPostNotifications() {
        ArrayList<PopupNotification> notifications = notificationChecker.getListOfPopupNotifications();

        for (PopupNotification pn : notifications) {
            for (PopupListener popupListener : popupListeners) {
                popupListener.notify(pn);
            }
        }
    }

    /**
     * Finds the number of milliseconds until the next minute. This is to account for the user not opening the program
     * exactly on the minute.
     *
     * @return the number of milliseconds until the next minute.
     */
    private static long findMillisecondsToNextMinute() {
        Instant currentInstant = Instant.now();
        Instant nextMinute = Instant.now().plusMillis(millisecondsInAMinute).truncatedTo(ChronoUnit.MINUTES);
        return currentInstant.until(nextMinute, ChronoUnit.MILLIS);
    }
}
