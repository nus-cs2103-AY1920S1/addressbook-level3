package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Description;
import seedu.address.model.reminders.conditions.ClassCondition;
/**
 * Jackson-friendly version of {@link ClassCondition}.
 */
public class JsonAdaptedClassCondition {
    private final String desc;
    private final String entryType;
    /**
     * Constructs a {@code JsonAdaptedClassCondition} with the given condition details.
     */
    @JsonCreator
    public JsonAdaptedClassCondition(@JsonProperty("entryType") String entryType, @JsonProperty("desc") String desc) {
        this.entryType = entryType;
        this.desc = desc;
    }
    public JsonAdaptedClassCondition(ClassCondition source) {
        this.entryType = source.typeToString();
        this.desc = source.getDesc().toString();
    }

    /**
     * Converts this Jackson-friendly adapted ClassCondition object into the model's {@code ClassCondition} object.
     * @return
     * @throws IllegalValueException
     */
    public ClassCondition toModelType() throws IllegalValueException {
        final Description modelDesc = new Description(desc);
        return new ClassCondition(modelDesc, entryType);
    }
}
