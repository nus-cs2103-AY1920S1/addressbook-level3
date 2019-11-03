//@@author SakuraBlossom
package seedu.address.model.person.parameters;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashMap;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReferenceId;
import seedu.address.model.exceptions.ReferenceIdCannotChangeClassificationException;
import seedu.address.model.exceptions.ReferenceIdIncorrectGroupClassificationException;


/**
 * Represents a Reference ID for Person.
 * Guarantees: Reference Id is present, validated and immutable.
 */
public class PersonReferenceId implements ReferenceId {

    public static final String MESSAGE_CONSTRAINTS =
        "Reference Id should only contain 3 to 10 (inclusive) alphanumeric characters.";
    /*
     * The reference ID should only contain alphanumeric characters.
     */
    private static final String VALIDATION_REGEX = "[a-zA-Z0-9]{3,10}";
    private static final HashMap<String, ReferenceId> UNIQUE_UNIVERSAL_REFERENCE_ID_MAP = new HashMap<>();


    private final String referenceId;
    private final boolean isStaff;

    /**
     * Constructs a {@code PersonReferenceId}.
     *
     * @param referenceId A valid identifier.
     */
    private PersonReferenceId(String referenceId, boolean isStaff) {
        requireNonNull(referenceId);
        checkArgument(isValidId(referenceId), MESSAGE_CONSTRAINTS);
        this.referenceId = referenceId.toUpperCase();
        this.isStaff = isStaff;
    }

    /**
     * Returns an existing {@code PersonReferenceId} if {@code String refId} is registered. Otherwise, Optional.empty.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code PersonReferenceId} is invalid or not found
     */
    private static Optional<ReferenceId> lookUpReferenceId(String refId) throws ParseException {
        requireNonNull(refId);
        String trimmedRefId = refId.trim().toUpperCase();
        if (!isValidId(trimmedRefId)) {
            throw new ParseException(MESSAGE_CONSTRAINTS);
        }

        ReferenceId storedRefId = UNIQUE_UNIVERSAL_REFERENCE_ID_MAP.get(trimmedRefId);
        if (storedRefId == null) {
            return Optional.empty();
        }
        return Optional.of(storedRefId);
    }

    /**
     * Parses a {@code String refId} into an {@code PersonReferenceId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code PersonReferenceId} is invalid or the {@code String refId}
     * has been grouped under a different classification.
     */
    private static ReferenceId issueReferenceId(String refId, boolean isStaff) throws ParseException {

        Optional<ReferenceId> optionalReferenceId = lookUpReferenceId(refId);

        if (optionalReferenceId.isEmpty()) {
            String trimmedRefId = refId.trim().toUpperCase();
            return new PersonReferenceId(trimmedRefId, isStaff);
        } else {
            ReferenceId referenceId = optionalReferenceId.get();

            if (referenceId.isStaffDoctor() != isStaff) {
                throw new ReferenceIdCannotChangeClassificationException(
                        referenceId.toString(), referenceId.isStaffDoctor());
            }

            return optionalReferenceId.get();
        }
    }

    /**
     * Returns an existing {@code PersonReferenceId} if {@code String refId} is registered.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code PersonReferenceId} is invalid, not found or the {@code String refId}
     * has been grouped under a different classification.
     */
    private static ReferenceId lookupExistingReferenceId(String refId, boolean isStaff) throws ParseException {
        ReferenceId referenceId =
                lookUpReferenceId(refId)
                        .orElseThrow(() -> new ParseException(
                                String.format(Messages.MESSAGE_INVAILD_REFERENCE_ID, refId.trim().toUpperCase())));

        if (referenceId.isStaffDoctor() != isStaff) {
            throw new ReferenceIdIncorrectGroupClassificationException(
                    referenceId.toString(),
                    referenceId.isStaffDoctor());
        }

        return referenceId;
    }

    /**
     * Returns an existing {@code PersonReferenceId} if {@code String refId} is registered as a staff.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code PersonReferenceId} is invalid, not found or the {@code String refId}
     * has been grouped under a different classification.
     */
    public static ReferenceId lookupStaffReferenceId(String staffRefId) throws ParseException {
        return lookupExistingReferenceId(staffRefId, true);
    }

    /**
     * Returns an existing {@code PersonReferenceId} if {@code String refId} is registered as a patient.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code PersonReferenceId} is invalid, not found or the {@code String refId}
     * has been grouped under a different classification.
     */
    public static ReferenceId lookupPatientReferenceId(String patientRefId) throws ParseException {
        return lookupExistingReferenceId(patientRefId, false);
    }

    /**
     * Parses a {@code String refId} into an {@code PersonReferenceId} is registered as a staff.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code PersonReferenceId} is invalid.
     */
    public static ReferenceId issueStaffReferenceId(String staffRefId) throws ParseException {
        return issueReferenceId(staffRefId, true);
    }

    /**
     * Parses a {@code String refId} into an {@code PersonReferenceId} is registered as a patient.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code PersonReferenceId} is invalid.
     */
    public static ReferenceId issuePatientReferenceId(String patientRefId) throws ParseException {
        return issueReferenceId(patientRefId, false);
    }

    /**
     * Returns true if a given string is a valid id.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean isSameAs(ReferenceId other) {
        return equals(other);
    }

    @Override
    public int compareTo(ReferenceId o) {
        requireNonNull(o);
        return o.toString().compareTo(toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof PersonReferenceId // instanceof handles nulls
                && toString().equals(other.toString())); // state check
    }

    @Override
    public String toString() {
        return referenceId;
    }

    @Override
    public int hashCode() {
        return referenceId.hashCode();
    }

    @Override
    public boolean isStaffDoctor() {
        return isStaff;
    }

    @Override
    public boolean isPatient() {
        return !isStaff;
    }

    @Override
    public void registerId() {
        UNIQUE_UNIVERSAL_REFERENCE_ID_MAP.putIfAbsent(this.toString(), this);
    }

    @Override
    public void unregisterId() {
        UNIQUE_UNIVERSAL_REFERENCE_ID_MAP.remove(this.toString());
    }
}
