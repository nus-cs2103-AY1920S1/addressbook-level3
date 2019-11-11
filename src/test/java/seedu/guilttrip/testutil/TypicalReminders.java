package seedu.guilttrip.testutil;

import java.util.Arrays;
import java.util.List;

import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.DateCondition;
import seedu.guilttrip.model.reminders.conditions.QuotaCondition;
import seedu.guilttrip.model.reminders.conditions.TagsCondition;
import seedu.guilttrip.model.reminders.conditions.TypeCondition;
import seedu.guilttrip.model.tag.Tag;

public class TypicalReminders {
    //GeneralReminder
    public static final GeneralReminder generateTypicalReminder() {
        final Description message = new Description("Test Reminder");
        final TypeCondition typeCondition = new TypeCondition("Expense");
        final QuotaCondition lowerBound = new QuotaCondition(5.00, true);
        final QuotaCondition upperBound = new QuotaCondition(10.00, true);
        final DateCondition start = new DateCondition(new Date("2019 09 08"), true);
        final DateCondition end = new DateCondition(new Date("2019 09 10"), true);
        final TagsCondition tagsCondition =
                new TagsCondition(Arrays.asList(new Tag[]{new Tag("food")}));
        final List conditions = Arrays.asList(new Condition[]{lowerBound, upperBound, start, end, tagsCondition});
        final GeneralReminder typicalGeneralReminder = new GeneralReminder(message, conditions);
        return typicalGeneralReminder;
    }
}
