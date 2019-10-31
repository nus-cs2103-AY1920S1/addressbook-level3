package seedu.address.logic.notification;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.address.model.DateTime;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.systemtray.PopupNotification;

/**
 * A class checks through lists of Events and Tasks to generate notifications to be posted
 */
public class NotificationChecker {
    private ModelManager model;

    public NotificationChecker(ModelManager model) {
        requireAllNonNull(model);
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

        for (TaskSource taskSource : model.getTaskList()) {
            DateTime taskDueDate = taskSource.getDueDate();
            if (taskDueDate != null && taskDueDate.equalsPrecisionMinute(DateTime.now())) {
                String name = taskSource.getDescription();

                returnValue.add(new PopupNotification(name, taskDueDate.toString()));
            }
        }

        return returnValue;
    }
}
