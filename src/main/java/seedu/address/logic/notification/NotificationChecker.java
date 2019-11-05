package seedu.address.logic.notification;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.DateTime;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.systemtray.PopupNotification;

/**
 * A class checks through lists of Events and Tasks to generate notifications to be posted
 */
public class NotificationChecker {
    private static final Logger logger = LogsCenter.getLogger(NotificationCheckingThread.class);
    private ModelManager model;

    public NotificationChecker(ModelManager model) {
        requireAllNonNull(model);
        this.model = model;
    }

    public ArrayList<PopupNotification> getListOfPopupNotifications() {
        ArrayList<PopupNotification> returnValue = new ArrayList<>();

        logger.info("Checking for Event notifications.");
        for (EventSource eventSource : model.getEventList()) {
            if (eventSource.notificationTimeMatchesCurrentTime()) {
                String name = eventSource.getDescription();
                DateTime eventDateTime = eventSource.getStartDateTime();

                returnValue.add(new PopupNotification(name, eventDateTime.toString()));
            }
        }

        logger.info("Checking for Task notifications.");
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
