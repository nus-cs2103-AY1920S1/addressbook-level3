package organice.model.person;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents a Person's blood type in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodType(String)}
 */
public class BloodType {

    public static final HashSet<String> BLOOD_TYPES =
            new HashSet<>(Arrays.asList("A+", "B+", "AB+", "O+", "A-", "B-", "AB-", "O-"));

    public static final BloodType BLOODTYPE_A_PLUS = new BloodType("A+");
    public static final BloodType BLOODTYPE_B_PLUS = new BloodType("B+");
    public static final BloodType BLOODTYPE_AB_PLUS = new BloodType("AB+");
    public static final BloodType BLOODTYPE_O_PLUS = new BloodType("O+");

    public static final BloodType BLOODTYPE_A_MINUS = new BloodType("A-");
    public static final BloodType BLOODTYPE_B_MINUS = new BloodType("B-");
    public static final BloodType BLOODTYPE_AB_MINUS = new BloodType("AB-");
    public static final BloodType BLOODTYPE_O_MINUS = new BloodType("O-");

    public static final HashMap<BloodType, HashSet<BloodType>> BLOOD_TYPES_MATCHES = BloodType.getBloodTypeMatches();

    public static final String MESSAGE_CONSTRAINTS =
            "Valid blood type inputs are: 'A+', 'B+', 'O+', 'AB+', 'A-', 'B-', 'O-', 'AB-'.\n"
            + "Inputs should not be blank";




    public final String value;

    /**
     * Constructs a {@code BloodType}.
     *
     * @param bloodType A valid blood type.
     */
    public BloodType(String bloodType) {
        requireNonNull(bloodType);
        checkArgument(isValidBloodType(bloodType), MESSAGE_CONSTRAINTS);
        value = bloodType.toUpperCase();
    }

    /**
     * Returns a hashmap where a blood type maps to a list of blood types that can be donated to.
     * @return
     */
    public static HashMap<BloodType, HashSet<BloodType>> getBloodTypeMatches() {
        HashMap hashMap = new HashMap<BloodType, HashSet<BloodType>>();

        // positive blood types
        hashMap.put(BLOODTYPE_A_PLUS, new HashSet<BloodType>(Arrays.asList(BLOODTYPE_O_PLUS, BLOODTYPE_A_PLUS,
                BLOODTYPE_O_MINUS, BLOODTYPE_A_MINUS)));
        hashMap.put(BLOODTYPE_B_PLUS, new HashSet<BloodType>(Arrays.asList(BLOODTYPE_O_PLUS, BLOODTYPE_B_PLUS,
                BLOODTYPE_O_MINUS, BLOODTYPE_B_MINUS)));
        hashMap.put(BLOODTYPE_AB_PLUS, new HashSet<BloodType>(Arrays.asList(BLOODTYPE_B_PLUS, BLOODTYPE_A_PLUS,
                BLOODTYPE_O_PLUS, BLOODTYPE_AB_PLUS, BLOODTYPE_B_MINUS, BLOODTYPE_A_MINUS,
                BLOODTYPE_O_MINUS, BLOODTYPE_AB_MINUS)));
        hashMap.put(BLOODTYPE_O_PLUS, new HashSet<BloodType>(Arrays.asList(BLOODTYPE_O_PLUS, BLOODTYPE_O_MINUS)));

        // negative blood types
        hashMap.put(BLOODTYPE_A_MINUS, new HashSet<BloodType>(Arrays.asList(BLOODTYPE_O_MINUS, BLOODTYPE_A_MINUS)));
        hashMap.put(BLOODTYPE_B_MINUS, new HashSet<BloodType>(Arrays.asList(BLOODTYPE_O_MINUS, BLOODTYPE_B_MINUS)));
        hashMap.put(BLOODTYPE_AB_MINUS, new HashSet<BloodType>(Arrays.asList(BLOODTYPE_B_MINUS, BLOODTYPE_A_MINUS,
                BLOODTYPE_O_MINUS, BLOODTYPE_AB_MINUS)));
        hashMap.put(BLOODTYPE_O_MINUS, new HashSet<BloodType>(Arrays.asList(BLOODTYPE_O_MINUS)));

        return hashMap;
    }

    /**
     * Returns true if a given string is a valid blood type.
     */
    public static boolean isValidBloodType(String test) {
        return BLOOD_TYPES.contains(test.toUpperCase());
    }

    /**
     * Checks if the blood of a {@code Donor} can be donated to a {@code Patient}.
     * This article explains what blood types can patients receive:
     * https://www.kidney.org/transplantation/livingdonors/what-blood-types-match
     * @param donorBloodType BloodType of a {@code Donor}
     * @return true if donorBloodType can be donated to the patient.
     */
    public boolean isCompatibleBloodType(BloodType donorBloodType) {
        HashSet<BloodType> matchingBloodTypes = BLOOD_TYPES_MATCHES.get(this);

        if (matchingBloodTypes.contains(donorBloodType)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BloodType // instanceof handles nulls
                && value.equals(((BloodType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
