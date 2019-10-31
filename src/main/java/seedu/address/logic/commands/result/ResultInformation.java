package seedu.address.logic.commands.result;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.activity.Activity;
import seedu.address.model.contact.Contact;

/**
 * Represents the information to display to user after command execution.
 */
public class ResultInformation {
    private final Contact contact;
    private final Activity activity;
    private final Accommodation accommodation;
    private final Index displayedIndex;
    private final String descriptionOfInformation;

    public ResultInformation(Contact contact, Activity activity, Accommodation accommodation,
                             Index displayedIndex, String descriptionOfInformation) {
        requireAllNonNull(displayedIndex, descriptionOfInformation);
        this.descriptionOfInformation = descriptionOfInformation;
        this.displayedIndex = displayedIndex;
        onlyOneNonNull(contact, activity, accommodation);
        this.contact = contact;
        this.activity = activity;
        this.accommodation = accommodation;
    }

    public ResultInformation(Contact contact, Index displayedIndex, String description) {
        this(requireNonNull(contact), null, null, displayedIndex, description);
    }

    public ResultInformation(Activity activity, Index displayedIndex, String description) {
        this(null, requireNonNull(activity), null, displayedIndex, description);
    }

    public ResultInformation(Accommodation accommodation, Index displayedIndex, String description) {
        this(null, null, requireNonNull(accommodation), displayedIndex, description);
    }

    public Optional<Contact> getContact() {
        return Optional.ofNullable(contact);
    }

    public Optional<Accommodation> getAccommodation() {
        return Optional.ofNullable(accommodation);
    }

    public Optional<Activity> getActivity() {
        return Optional.ofNullable(activity);
    }

    public Index getIndex() {
        return displayedIndex;
    }

    public String getDescription() {
        return descriptionOfInformation;
    }

    /**
     * Throws an AssertionError if there is no one and only one non-null object.
     */
    private static void onlyOneNonNull(Object ...obj) throws AssertionError {
        int nonNullCounter = 0;
        for (Object o : obj) {
            if (!(o == null)) {
                nonNullCounter++;
            }
        }
        if (nonNullCounter > 1) {
            throw new AssertionError("There is more than 1 non-null object.");
        } else if (nonNullCounter < 1) {
            throw new AssertionError("The objects are all null.");
        }
    }
}
