package seedu.address.notification;

import seedu.address.model.events.ReadOnlyEventList;

/**
 * The interface for the Notification component of the app.
 */
public interface Notification {
    void updateNotificationQueue(ReadOnlyEventList readOnlyEventsBook);

    void shutDown();
}
