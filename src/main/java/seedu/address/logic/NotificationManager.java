package seedu.address.logic;

import seedu.address.logic.notification.NotificationChecker;
import seedu.address.logic.notification.NotificationCheckingThread;
import seedu.address.model.ModelManager;
import seedu.address.ui.systemtray.PopupListener;
import seedu.address.ui.systemtray.SystemTrayCommunicator;

/**
 * A class that manages the multi-threading operations necessary for posting notifications.
 */
public class NotificationManager {
    private NotificationCheckingThread notificationCheckingThread;
    private SystemTrayCommunicator systemTrayCommunicator;

    public NotificationManager(ModelManager modelManager) {
        NotificationChecker notificationChecker = new NotificationChecker(modelManager);

        notificationCheckingThread = new NotificationCheckingThread(notificationChecker);
        systemTrayCommunicator = new SystemTrayCommunicator();
        notificationCheckingThread.addPopupListener(new PopupListener(systemTrayCommunicator));
        notificationCheckingThread.setDaemon(true);
        notificationCheckingThread.start();
    }

    /**
     * Switches off notifications.
     */
    public void switchOffNotifications() {
        notificationCheckingThread.switchOffNotifications();
        systemTrayCommunicator.switchOffNotifications();
    }

    /**
     * Switches on notifications.
     */
    public void switchOnNotifications() {
        notificationCheckingThread.switchOnNotifications();
        systemTrayCommunicator.switchOnNotifications();
    }
}
