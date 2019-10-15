package seedu.address.logic.notification;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ModelManager;
import seedu.address.ui.systemtray.PopupListener;
import seedu.address.ui.systemtray.PopupNotification;

/**
 * A thread meant that handles checking for notifications and notifying the appropriate listeners
 */
public class NotificationCheckingThread extends Thread {
    private static final Logger logger = LogsCenter.getLogger(NotificationCheckingThread.class);
    private static final long millisecondsInAMinute = 60000;

    private ArrayList<PopupListener> popupListeners = new ArrayList<>();

    private ModelManager modelManager;

    public NotificationCheckingThread(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public void addPopupListener(PopupListener popupListener) {
        popupListeners.add(popupListener);
    }

    @Override
    public void run() {
        try {
            while (true) {
                ArrayList<PopupNotification> notifications = modelManager.getListOfPopupNotifications();

                for (PopupNotification pn : notifications) {
                    for (PopupListener popupListener : popupListeners) {
                        popupListener.notify(pn);
                    }
                }

                Thread.sleep(millisecondsInAMinute);
            }

        } catch (InterruptedException e) {
            logger.info("NotificationManagingThread successfully interrupted.");
        }
    }
}
