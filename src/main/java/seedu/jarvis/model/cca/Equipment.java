package seedu.jarvis.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

/**
 * Represents a CCA's equipment in Jarvis.
 * Guarantees: immutable; is valid as declared in {@link #isValidEquipmentName(String)}
 */
public class Equipment {

    public static final String MESSAGE_CONSTRAINTS =
            "Equipment names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the equipment must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String equipmentName;

    /**
     * Constructs a {@code Equipment}.
     *
     * @param equipmentName A valid equipment name.
     */
    public Equipment(String equipmentName) {
        requireNonNull(equipmentName);
        checkArgument(isValidEquipmentName(equipmentName), MESSAGE_CONSTRAINTS);
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidEquipmentName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return equipmentName;
    }

    /**
     * Returns true if both equipment of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two equipments.     *
     *
     * @param otherEquipment
     * @return
     */
    public boolean isSameEquipment(Equipment otherEquipment) {
        if (otherEquipment == this) {
            return true;
        }

        return otherEquipment != null
                && otherEquipment.getEquipmentName().equals(getEquipmentName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Equipment // instanceof handles nulls
                && equipmentName.equals(((Equipment) other).equipmentName)); // state check
    }

    @Override
    public int hashCode() {
        return equipmentName.hashCode();
    }

}
