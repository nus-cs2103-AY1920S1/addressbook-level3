package seedu.guilttrip.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.storage.conditions.JsonAdaptedCondition;

/**
 * Constructs a {@code JsonAdaptedReminder} with the given generalReminder details.
 */
public class JsonAdaptedReminder {
    private String header;
    private final List<JsonAdaptedCondition> conditions = new ArrayList<>();
    private String status;
    private String reminderType;

    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("reminderType") String reminderType,
                               @JsonProperty("message") String header,
                               @JsonProperty("conditions") List<JsonAdaptedCondition> conditions,
                               @JsonProperty("status") String status) {
        this.reminderType = reminderType;
        this.header = header;
        this.conditions.addAll(conditions);
        this.status = status;
    }

    /**
     * Converts a given {@code GeneralReminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        if (source instanceof GeneralReminder) {
            GeneralReminder generalReminder = (GeneralReminder) source;
            this.reminderType = "GeneralReminder";
            this.header = generalReminder.getHeader().toString();
            conditions.addAll(generalReminder.getConditions().stream()
                    .map(JsonAdaptedCondition::new)
                    .collect(Collectors.toList()));
            this.status = source.getStatus().toString();
        }
    }

    /**
     * Converts this Jackson-friendly adapted generalReminder object into the model's {@code GeneralReminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted generalReminder.
     */
    public GeneralReminder toModelType() throws IllegalValueException {
        if (reminderType.equalsIgnoreCase("GeneralReminder")) {
            final List<Condition> conditionList = new ArrayList<>();
            for (JsonAdaptedCondition condition : conditions) {
                conditionList.add(condition.toModelType());
            }
            if (!Description.isValidDescription(header)) {
                throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
            }
            final Description modelMessage = new Description(header);
            GeneralReminder modelGeneralReminder = new GeneralReminder(modelMessage, conditionList);
            modelGeneralReminder.setStatus(this.status);
            return modelGeneralReminder;
        } else {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
    }
}
