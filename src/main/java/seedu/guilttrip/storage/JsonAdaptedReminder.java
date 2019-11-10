package seedu.guilttrip.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.IEWReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.util.Frequency;
import seedu.guilttrip.storage.conditions.JsonAdaptedCondition;

/**
 * Constructs a {@code JsonAdaptedReminder} with the given generalReminder details.
 */
public class JsonAdaptedReminder {
    private String reminderType;
    private String header;
    private String uniqueID;

    private final List<JsonAdaptedCondition> conditions = new ArrayList<>();

    private String period;
    private String freq;

    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("reminderType") String reminderType,
                               @JsonProperty("header") String header,
                               @JsonProperty("status") String status,
                               @JsonProperty("uniqueID") String uniqueID,
                               @JsonProperty("conditions") List<JsonAdaptedCondition> conditions,
                               @JsonProperty("period") String period,
                               @JsonProperty("freq") String freq) {
        this.reminderType = reminderType;
        this.header = header;
        this.uniqueID = uniqueID;
        this.conditions.addAll(conditions);
        this.period = period;
        this.freq = freq;
    }

    /**
     * Converts a given {@code GeneralReminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        uniqueID = source.getUniqueID();
        header = source.getHeader().toString();
        if (source instanceof GeneralReminder) {
            GeneralReminder generalReminder = (GeneralReminder) source;
            reminderType = "GeneralReminder";
            conditions.addAll(generalReminder.getConditions().stream()
                    .map(JsonAdaptedCondition::new)
                    .collect(Collectors.toList()));
        } else if (source instanceof IEWReminder) {
            IEWReminder iewReminder = (IEWReminder) source;
            reminderType = "IEWReminder";
            period = iewReminder.getPeriod().toString();
            freq = iewReminder.getFrequency().toString();
        }
    }

    /**
     * Converts this Jackson-friendly adapted generalReminder object into the model's {@code GeneralReminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted generalReminder.
     */
    public Reminder toModelType() throws IllegalValueException {
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
            return modelGeneralReminder;
        } else if (reminderType.equalsIgnoreCase("IEWReminder")) {
            if (!Description.isValidDescription(header)) {
                throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
            }
            final Description modelHeader = new Description(header);
            final Period modelPeriod = new Period(period);
            final Frequency modelFreq = Frequency.parse(freq);
            IEWReminder iewReminder = new IEWReminder(modelHeader, uniqueID, modelPeriod, modelFreq);
            return iewReminder;
        } else {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
    }
}
