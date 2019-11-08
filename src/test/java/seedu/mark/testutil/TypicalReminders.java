package seedu.mark.testutil;

import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.BENSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.mark.model.reminder.Reminder;

/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {
    public static final Reminder OPEN = new ReminderBuilder().withNote("Open")
            .withUrl(ALICE.getUrl().toString()).withTime("12/12/2020 1800")
            .build();
    public static final Reminder READ = new ReminderBuilder().withNote("Read")
            .withUrl(BENSON.getUrl().toString()).withTime("02/03/2099 0500")
            .build();

    private TypicalReminders() {} // prevents instantiation

    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(OPEN, READ));
    }
}
