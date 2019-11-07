package seedu.guilttrip.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.storage.conditions.JsonAdaptedCondition;

/**
 * Responsible for generating Condition List from GeneralReminder Lists.
 */
public class ReminderConditionMapper {
    private final List<GeneralReminder> generalReminders = new ArrayList<>();
    private final List<Condition> conditions = new ArrayList<>();
    private final Logger logger = LogsCenter.getLogger(getClass());

    public ReminderConditionMapper(List<JsonAdaptedReminder> jsonReminders,
                                    List<JsonAdaptedCondition> jsonConditions)
        throws IllegalValueException {
        for (JsonAdaptedReminder jsonAdaptedReminder : jsonReminders) {
            GeneralReminder generalReminder = jsonAdaptedReminder.toModelType();
            map(generalReminder);
            this.generalReminders.add(generalReminder);
        }
        for (JsonAdaptedCondition jsonConditionWrapper : jsonConditions) {
            Condition condition = jsonConditionWrapper.toModelType();
            if (!conditions.contains(condition)) {
                this.conditions.add(condition);
            }
        }
    }

    /**
     * Adds conditions in generalReminders to list.
     * If generalReminders is already added, maps condition to generalReminder in list and vice versa.
     * @param generalReminder
     */
    private void map(GeneralReminder generalReminder) {
        for (Condition condition : generalReminder.getConditions()) {
            if (!conditions.contains(condition)) {
                conditions.add(condition);
            } else {
                Condition toMap = conditions.get(conditions.indexOf((condition)));
                generalReminder.removeCondition(condition);
                generalReminder.addCondition(toMap);
            }
        }
    }
    public List<GeneralReminder> getGeneralReminders() {
        return generalReminders;
    }
    public List<Condition> getConditions() {
        return conditions;
    }
}
