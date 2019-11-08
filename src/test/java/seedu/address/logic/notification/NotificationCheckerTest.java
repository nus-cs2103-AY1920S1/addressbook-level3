package seedu.address.logic.notification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.DateTime;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.ui.systemtray.PopupNotification;


public class NotificationCheckerTest {

    @Test
    void constructor_inputNull_shouldThrowError() {
        assertThrows(NullPointerException.class, (
            ) -> new NotificationChecker(null));
    }

    @Test
    void getListOfNotifications_inputEmptyList_returnEmptyList() {
        ModelManager model = new ModelManager();
        NotificationChecker notificationChecker = new NotificationChecker(model);

        ArrayList<PopupNotification> popupNotificationList = notificationChecker.getListOfPopupNotifications();
        assertEquals(0, popupNotificationList.size());
    }

    @Test
    void getListOfNotifications_inputMatchingEventSource_returnSizeTwoList() {
        ModelManager model = new ModelManager();
        NotificationChecker notificationChecker = new NotificationChecker(model);

        DateTime start = DateTime.now();
        EventSource es1 = EventSource.newBuilder("Test1", start).build();
        EventSource es2 = EventSource.newBuilder("Test2", start).build();

        model.setModelData(new ModelData(
            List.of(es1, es2),
            List.of()));
        ArrayList<PopupNotification> popupNotificationList = notificationChecker.getListOfPopupNotifications();
        assertEquals(2, popupNotificationList.size());
    }

    @Test
    void getListOfNotifications_inputMatchingEventSource_returnMatchingEventSource() {
        ModelManager model = new ModelManager();
        NotificationChecker notificationChecker = new NotificationChecker(model);

        DateTime start = DateTime.now();
        EventSource es1 = EventSource.newBuilder("Test1", start).build();
        EventSource es2 = EventSource.newBuilder("Test2", start).build();

        model.setModelData(new ModelData(
            List.of(es1, es2),
            List.of()));
        ArrayList<PopupNotification> popupNotificationList = notificationChecker.getListOfPopupNotifications();
        assertEquals(new PopupNotification(es1.getDescription(),
                es2.getStartDateTime().toString()), popupNotificationList.get(0));

        assertEquals(new PopupNotification(es2.getDescription(),
                es2.getStartDateTime().toString()), popupNotificationList.get(1));
    }
}
