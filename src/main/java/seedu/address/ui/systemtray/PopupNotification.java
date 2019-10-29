package seedu.address.ui.systemtray;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a notification to be posted to the system tray.
 */
public class PopupNotification {
    public final String name;
    public final String description;

    /**
     * Creates a new PopupNotification with the appropriate name and description.
     *
     * @param name The name to show up when the notification is posted
     * @param description The description to accompany the name when the notification is posted
     */
    public PopupNotification (String name, String description) {
        requireAllNonNull(name, description);
        this.name = name;
        this.description = description;
    }
}
