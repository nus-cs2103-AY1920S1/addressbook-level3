package seedu.address.model.employee;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represent's the unique identifying ID of an Employee Object
 */
public class EmployeeId {
    public static final String MESSAGE_CONSTRAINTS =
            "Employee IDs is a 3-digit unique number, therefore employees added (cumulative) cannot exceed 1000!";
    private static final String VALIDATION_REGEX = "\\d{3}";
    private static int nextID = 0;
    public final String id;

    /**
     * Constructs an {@code EmployeeID}.
     */
    public EmployeeId() {
        String id = String.format("%03d", nextID);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        this.id = id;
        nextID++;
    }

    public EmployeeId(String id) {
        this.id = id;
    }


    public static void setNextId(int nextId) {
        EmployeeId.nextID = nextId;
    }

    /**
     * Returns true if a given string is a valid id.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeId // instanceof handles nulls
                && id.equals(((EmployeeId) other).id)); // state check
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
