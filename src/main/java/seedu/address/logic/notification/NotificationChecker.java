package seedu.address.logic.notification;

import java.util.ArrayList;

import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventList;
import seedu.address.model.events.EventSource;
import seedu.address.ui.systemtray.PopupNotification;

/**
 * A class checks through lists of Events and Tasks to generate notifications to be posted
 */
public class NotificationChecker {
    private EventList mainEventList;

    public NotificationChecker(EventList mainEventList) {
        this.mainEventList = mainEventList;
    }

    public ArrayList<PopupNotification> getListOfPopupNotifications() {
        ArrayList<PopupNotification> returnValue = new ArrayList<>();

        for (EventSource eventSource : mainEventList.getReadOnlyList()) {
            if (eventSource.notificationTimeMatchesCurrentTime()) {
                String name = eventSource.getDescription();
                DateTime eventDateTime = eventSource.getStartDateTime();

                returnValue.add(new PopupNotification(name, eventDateTime.toString()));
            }
        }

        return returnValue;
    }
}
