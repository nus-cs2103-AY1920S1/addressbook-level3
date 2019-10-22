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

    public NotificationManager(ModelManager modelManager) {
        NotificationChecker notificationChecker = new NotificationChecker(modelManager);

        notificationCheckingThread = new NotificationCheckingThread(notificationChecker);
        notificationCheckingThread.addPopupListener(new PopupListener(new SystemTrayCommunicator()));
        notificationCheckingThread.setDaemon(true);
        notificationCheckingThread.start();
    }

    public void switchOffNotifications() {
        notificationCheckingThread.switchOffNotifications();
    }

    public void switchOnNotifications() {
        notificationCheckingThread.switchOnNotifications();
    }
}
