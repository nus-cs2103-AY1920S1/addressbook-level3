package seedu.guilttrip.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.guilttrip.MainApp;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.IewReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.util.Frequency;
import seedu.guilttrip.storage.conditions.JsonAdaptedCondition;

/**
 * Constructs a {@code JsonAdaptedReminder} with the given generalReminder details.
 */
public class JsonAdaptedReminder {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private String status;
    private String reminderType;
    private String header;
    private String uniqueId;

    private final List<JsonAdaptedCondition> conditions = new ArrayList<>();

    private String period;
    private String freq;


    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("status") String status,
                               @JsonProperty("reminderType") String reminderType,
                               @JsonProperty("header") String header,
                               @JsonProperty("uniqueID") String uniqueId,
                               @JsonProperty("conditions") List<JsonAdaptedCondition> conditions,
                               @JsonProperty("period") String period,
                               @JsonProperty("freq") String freq) {
        this.status = status;
        this.reminderType = reminderType;
        this.header = header;
        this.uniqueId = uniqueId;
        this.conditions.addAll(conditions);
        this.period = period;
        this.freq = freq;
    }

    /**
     * Converts a given {@code GeneralReminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        status = source.getStatus().toString();
        uniqueId = source.getUniqueId();
        header = source.getHeader().toString();
        if (source instanceof GeneralReminder) {
            GeneralReminder generalReminder = (GeneralReminder) source;
            reminderType = "GeneralReminder";
            conditions.addAll(generalReminder.getConditions().stream()
                    .map(JsonAdaptedCondition::new)
                    .collect(Collectors.toList()));
        } else if (source instanceof IewReminder) {
            IewReminder iewReminder = (IewReminder) source;
            reminderType = "IewReminder";
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
        logger.info("Header: " + header);
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
        } else if (reminderType.equalsIgnoreCase("IewReminder")) {
            if (!Description.isValidDescription(header)) {
                throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
            }
            final Description modelHeader = new Description(header);
            final Period modelPeriod = new Period(period);
            final Frequency modelFreq = Frequency.parse(freq);
            IewReminder iewReminder = new IewReminder(modelHeader, uniqueId, modelPeriod, modelFreq);
            return iewReminder;
        } else {
            throw new IllegalValueException("Unsupported reminder.");
        }
    }
}
