package seedu.guilttrip.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.commons.util.ListenerSupport;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.IEWReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;

/**
 * Responsible for mapping reminders to conditions and to entries.
 */
public class ReminderMapper {
    private final List<Reminder> reminders = new ArrayList<>();
    private final HashMap<String, IEWReminder> iewReminders = new HashMap<>();
    private final List<Condition> conditions = new ArrayList<>();
    private final Logger logger = LogsCenter.getLogger(getClass());

    public ReminderMapper(List<JsonAdaptedReminder> jsonReminders)
        throws IllegalValueException {
        for (JsonAdaptedReminder jsonAdaptedReminder : jsonReminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            if (reminder instanceof GeneralReminder) {
                GeneralReminder generalReminder = (GeneralReminder) reminder;
                mapCondition(generalReminder);
                reminders.add(generalReminder);
            } else if (reminder instanceof IEWReminder) {
                IEWReminder iewReminder = (IEWReminder) reminder;
                iewReminders.put(iewReminder.getUniqueID(), iewReminder);
            }
        }
    }

    /**
     * Adds conditions in generalReminders to list.
     * If generalReminders is already added, maps condition to generalReminder in list and vice versa.
     * @param generalReminder
     */
    private void mapCondition(GeneralReminder generalReminder) {
        for (Condition condition : generalReminder.getConditions()) {
            if (!conditions.contains(condition)) {
                conditions.add(condition);
            } else {
                int index = conditions.indexOf(condition);
                Condition existingCondition = conditions.get(index);
                for (ListenerSupport listener : condition.getSupport().getPropertyChangeListeners()) {
                    existingCondition.addPropertyChangeListener(listener);
                }
            }
        }
    }

    public void mapEntry(Entry entry) {
        IEWReminder reminder = iewReminders.get(entry.getUniqueID());
        logger.info("Successfully mapped reminder " + reminder + " to entry " + entry);
        reminder.setEntry(entry);
        reminders.add(reminder);
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public HashMap<String, IEWReminder> getIewReminders() {
        return this.iewReminders;
    }

    public List<Condition> getConditions() {
        return conditions;
    }
}
