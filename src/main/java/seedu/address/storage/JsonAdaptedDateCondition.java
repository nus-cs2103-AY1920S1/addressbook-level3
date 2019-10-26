package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Date;
import seedu.address.model.person.Description;
import seedu.address.model.reminders.conditions.DateCondition;
/**
 * Jackson-friendly version of {@link DateCondition}.
 */
public class JsonAdaptedDateCondition {
    private final String start;
    private final String end;
    private final String desc;
    /**
     * Constructs a {@code JsonAdaptedDateCondition} with the given condition details.
     */
    @JsonCreator
    public JsonAdaptedDateCondition(@JsonProperty("start") String start, @JsonProperty("end") String end,
                                    @JsonProperty("desc") String desc) {
        this.start = start;
        this.end = end;
        this.desc = desc;
    }
    public JsonAdaptedDateCondition(DateCondition source) {
        this.start = source.getStart().toString();
        this.end = source.getEnd().toString();
        this.desc = source.getDesc().toString();
    }

    /**
     * Converts this Jackson-friendly adapted DateCondition object into the model's {@code DateCondition} object.
     * @return
     * @throws IllegalValueException
     */
    public DateCondition toModelType() throws IllegalValueException {
        final Description modelDesc = new Description(desc);
        final Date modelStart = new Date(start);
        final Date modelEnd = new Date(end);
        return new DateCondition(modelDesc, modelStart, modelEnd);
    }

}
