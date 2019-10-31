package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.conditions.Condition;
import seedu.address.storage.conditions.JsonAdaptedCondition;

/**
 * Responsible for generating Condition List from Reminder Lists.
 */
public class ReminderConditionMapper {
    private final List<Reminder> reminders = new ArrayList<>();
    private final List<Condition> conditions = new ArrayList<>();
    private final Logger logger = LogsCenter.getLogger(getClass());

    public ReminderConditionMapper(List<JsonAdaptedReminder> jsonReminders,
                                    List<JsonAdaptedCondition> jsonConditions)
        throws IllegalValueException {
        for (JsonAdaptedReminder jsonAdaptedReminder : jsonReminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            map(reminder);
            this.reminders.add(reminder);
        }
        for (JsonAdaptedCondition jsonConditionWrapper : jsonConditions) {
            Condition condition = jsonConditionWrapper.toModelType();
            if (!conditions.contains(condition)) {
                this.conditions.add(condition);
            }
        }
    }

    /**
     * Adds conditions in reminders to list.
     * If reminders is already added, maps condition to reminder in list and vice versa.
     * @param reminder
     */
    private void map(Reminder reminder) {
        for (Condition condition : reminder.getConditions()) {
            if (!conditions.contains(condition)) {
                conditions.add(condition);
            } else {
                /*Condition toMap = conditions.get(conditions.indexOf((condition)));
                reminder.removeCondition(condition);
                reminder.addCondition(toMap);*/
            }
        }
    }
    public List<Reminder> getReminders() {
        return reminders;
    }
    public List<Condition> getConditions() {
        return conditions;
    }
}
