package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Department;

/**
 * Jackson-friendly version of  {@link Department}.
 */
public class JsonAdaptedDepartment {

    private final String department;

    /**
     * Constructs a {@code JsonAdaptedDepartment} with the given {@code department}.
     */
    @JsonCreator
    public JsonAdaptedDepartment(String department) {
        this.department = department;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedDepartment(Department source) {
        department = source.department;
    }

    @JsonValue
    public String getDepartment() {
        return department;
    }

    /**
     * Converts this Jackson-friendly adapted department object into the model's {@code Department} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted department.
     */
    public Department toModelType() throws IllegalValueException {
        if (!Department.isValidDepartment(department)) {
            throw new IllegalValueException(Department.MESSAGE_CONSTRAINTS);
        }
        return new Department(department);
    }
}
