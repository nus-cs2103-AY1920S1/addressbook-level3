package seedu.address.notification;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import seedu.address.model.events.DateTime;


public class PopupNotification {
    public final String name;
    public final String description;
    public final DateTime timeToNotify;

    public PopupNotification (String name, String description, DateTime timeToNotify) {
        requireAllNonNull(name, description, timeToNotify);
        this.name = name;
        this.description = description;
        this.timeToNotify = timeToNotify;
    }
}
