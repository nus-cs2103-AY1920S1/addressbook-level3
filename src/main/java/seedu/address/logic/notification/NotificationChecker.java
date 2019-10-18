package seedu.address.logic.notification;

import java.util.ArrayList;

import seedu.address.model.ModelManager;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.ui.systemtray.PopupNotification;

/**
 * A class checks through lists of Events and Tasks to generate notifications to be posted
 */
public class NotificationChecker {
    private ModelManager model;

    public NotificationChecker(ModelManager model) {
        this.model = model;
    }

    public ArrayList<PopupNotification> getListOfPopupNotifications() {
        ArrayList<PopupNotification> returnValue = new ArrayList<>();

        for (EventSource eventSource : model.getEventList()) {
            if (eventSource.notificationTimeMatchesCurrentTime()) {
                String name = eventSource.getDescription();
                DateTime eventDateTime = eventSource.getStartDateTime();

                returnValue.add(new PopupNotification(name, eventDateTime.toString()));
            }
        }

        return returnValue;
    }
}
