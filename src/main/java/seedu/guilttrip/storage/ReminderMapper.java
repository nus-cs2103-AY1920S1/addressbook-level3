package seedu.guilttrip.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.reminders.EntryReminder;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.QuotaCondition;

/**
 * Responsible for mapping reminders to conditions and to entries.
 */
public class ReminderMapper {
    private final List<Reminder> reminders = new ArrayList<>();
    private final HashMap<String, EntryReminder> iewReminders = new HashMap<>();
    private final List<Condition> conditions = new ArrayList<>();
    private final Logger logger = LogsCenter.getLogger(getClass());

    public ReminderMapper(List<JsonAdaptedReminder> jsonReminders)
        throws IllegalValueException {
        logger.info("Mapping reminders");
        for (JsonAdaptedReminder jsonAdaptedReminder : jsonReminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            logger.info("Successfully converted to model.");
            if (reminder instanceof GeneralReminder) {
                GeneralReminder generalReminder = (GeneralReminder) reminder;
                mapCondition(generalReminder);
                reminders.add(generalReminder);
            } else if (reminder instanceof EntryReminder) {
                EntryReminder entryReminder = (EntryReminder) reminder;
                iewReminders.put(entryReminder.getUniqueId(), entryReminder);
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
            if (!(condition instanceof QuotaCondition)) {
                logger.info("adding condition");
                conditions.add(condition);
            } else {
                QuotaCondition quotaCondition = (QuotaCondition) condition;
                if (quotaCondition.getQuota() != 0) {
                    logger.info("adding condition");
                    conditions.add(condition);
                }
            }

        }
    }

    /**
     * Map entry specific reminder to ebrty.
     */
    public void mapEntry(Entry entry) {
        EntryReminder reminder = iewReminders.get(entry.getUniqueId());
        logger.info("Successfully mapped reminder " + reminder + " to entry " + entry);
        reminder.setEntry(entry);
        reminders.add(reminder);
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public HashMap<String, EntryReminder> getIewReminders() {
        return this.iewReminders;
    }

    public List<Condition> getConditions() {
        return conditions;
    }
}
