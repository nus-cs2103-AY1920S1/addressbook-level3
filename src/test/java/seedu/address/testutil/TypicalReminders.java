package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TutorAid;
import seedu.address.model.reminder.Reminder;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalReminders {
    public static final Reminder CS2103T = new ReminderBuilder().withDescription("CS2103T")
            .withReminderTimes("10/10/2019 13:00, 10/10/2019 15:00")
            .build();
    public static final Reminder CS2100 = new ReminderBuilder().withDescription("CS2100")
            .withReminderTimes("10/10/2019 13:00, 10/10/2019 15:00", "13/10/2019 15:00, 13/10/2019 16:00")
            .build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalReminders() {} // prevents instantiation

    /**
     * Returns an {@code TutorAid} with all the typical reminders.
     */
    public static TutorAid getTypicalTutorAid() {
        TutorAid ab = new TutorAid();
        for (Reminder reminder : getTypicalReminders()) {
            ab.addReminder(reminder);
        }
        return ab;
    }

    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(CS2103T, CS2100));
    }
}
