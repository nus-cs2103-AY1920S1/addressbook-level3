package seedu.address.model.Employee;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represent's the unique identifying ID of an Employee Object
 */
public class EmployeeID {
    public static final String MESSAGE_CONSTRAINTS =
            "Employee IDs is a 3-digit unique number";
    private static final String VALIDATION_REGEX = "\\d{3}";
    private static int nextID = 0;
    public final String id;

    /**
     * Constructs an {@code EmployeeID}.
     */
    public EmployeeID() {
        String id = String.format("%03d", nextID);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        this.id = id;
        nextID++;
    }

    public EmployeeID(String id) {
        this.id = id;
    }

    /**
     * Returns true if a given string is a valid name.
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
                || (other instanceof EmployeeID // instanceof handles nulls
                && id.equals(((EmployeeID) other).id)); // state check
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
