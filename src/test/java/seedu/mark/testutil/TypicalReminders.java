package seedu.mark.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.mark.model.reminder.Reminder;

/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {
    public static final Reminder OPEN = new ReminderBuilder().withNote("Open")
            .withBookmark(TypicalBookmarks.ALICE).withTime("12/12/2020 1800")
            .build();
    public static final Reminder READ = new ReminderBuilder().withNote("Read")
            .withBookmark(TypicalBookmarks.BENSON).withTime("02/03/2019 0500")
            .build();

    private TypicalReminders() {} // prevents instantiation

    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(OPEN, READ));
    }
}
