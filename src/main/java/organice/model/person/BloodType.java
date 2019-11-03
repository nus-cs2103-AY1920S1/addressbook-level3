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
            new HashSet<>(Arrays.asList("A", "B", "AB", "O"));

    public static final BloodType BLOODTYPE_A = new BloodType("A");
    public static final BloodType BLOODTYPE_B = new BloodType("B");
    public static final BloodType BLOODTYPE_AB = new BloodType("AB");
    public static final BloodType BLOODTYPE_O = new BloodType("O");


    public static final HashMap<BloodType, HashSet<BloodType>> BLOOD_TYPES_MATCHES = BloodType.getBloodTypeMatches();

    public static final String MESSAGE_CONSTRAINTS =
            "Blood type should only have A, B, O or AB. Inputs should not be blank";




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

    public static HashMap<BloodType, HashSet<BloodType>> getBloodTypeMatches() {
        HashMap hashMap = new HashMap<BloodType, HashSet<BloodType>>();

        hashMap.put(BLOODTYPE_A, new HashSet<BloodType>(Arrays.asList(BLOODTYPE_O, BLOODTYPE_A)));
        hashMap.put(BLOODTYPE_B, new HashSet<BloodType>(Arrays.asList(BLOODTYPE_O, BLOODTYPE_B)));
        hashMap.put(BLOODTYPE_AB, new HashSet<BloodType>(Arrays.asList(BLOODTYPE_B, BLOODTYPE_A, BLOODTYPE_O,
                BLOODTYPE_AB)));
        hashMap.put(BLOODTYPE_O, new HashSet<BloodType>(Arrays.asList(BLOODTYPE_O)));
        return hashMap;
    }

    /**
     * Returns true if a given string is a valid blood type.
     */
    public static boolean isValidBloodType(String test) {
        return BLOOD_TYPES.contains(test.toUpperCase());
    }

    /**
     * Returns true if a {@code BloodType} matches this blood type.
     */
    public boolean isBloodTypeMatch(BloodType match) {
        HashSet<BloodType> matchingBloodTypes = BLOOD_TYPES_MATCHES.get(this);

        if (matchingBloodTypes.contains(match)) {
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
