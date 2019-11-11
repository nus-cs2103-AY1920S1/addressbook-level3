package seedu.address.ui.systemtray;

/**
 * A listener for new popups to be posted
 */
public class PopupListener {
    private static SystemTrayCommunicator systemTrayCommunicator;

    public PopupListener(SystemTrayCommunicator systemTrayCommunicator) {
        this.systemTrayCommunicator = systemTrayCommunicator;
    }

    public void notify(PopupNotification popupNotification) {
        systemTrayCommunicator.postNewNotification(popupNotification.name, popupNotification.description);
    }
}
