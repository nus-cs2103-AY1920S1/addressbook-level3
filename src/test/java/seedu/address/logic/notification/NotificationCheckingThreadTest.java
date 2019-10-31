package seedu.address.logic.notification;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;

public class NotificationCheckingThreadTest {

    @Test
    void whileRunningThread_requestInterrupt_shouldExitByThrowingInterruptedException() {
        assertDoesNotThrow((
        ) -> {
            NotificationCheckingThread nct =
                    new NotificationCheckingThread(new NotificationChecker(new ModelManager()));

            nct.setDaemon(true);
            nct.start();

            nct.interrupt();
        });
    }

    @Test
    void setNotificationsOff_checkNotificationsSwitchedOffSuccessfully_valueAsExpected() {
        NotificationCheckingThread nct =
                new NotificationCheckingThread(new NotificationChecker(new ModelManager()));
        nct.switchOffNotifications();
        assertFalse(nct.getNotificationsOnStatus());
    }

    @Test
    void setNotificationsOn_checkNotificationsSwitchedOnSuccessfully_valueAsExpected() {
        NotificationCheckingThread nct =
                new NotificationCheckingThread(new NotificationChecker(new ModelManager()));
        nct.switchOffNotifications();
        nct.switchOnNotifications();
        assertTrue(nct.getNotificationsOnStatus());
    }
}
