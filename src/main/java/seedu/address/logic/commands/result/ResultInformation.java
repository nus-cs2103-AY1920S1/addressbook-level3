package seedu.address.logic.commands.result;

import seedu.address.commons.core.index.Index;
import seedu.address.model.contact.Contact;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.model.itineraryitem.activity.Activity;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class ResultInformation {
    private final Contact contact;
    private final Activity activity;
    private final Accommodation accommodation;
    private final Index displayedIndex;

    public ResultInformation(Contact contact, Activity activity, Accommodation accommodation, Index displayedIndex) {
        this.displayedIndex = requireNonNull(displayedIndex);
        this.contact = contact;
        this.activity = activity;
        this.accommodation = accommodation;
    }

    public ResultInformation(Contact contact, Index displayedIndex) {
        this(requireNonNull(contact), null, null, displayedIndex);
    }

    public ResultInformation(Activity activity, Index displayedIndex) {
        this(null, requireNonNull(activity), null, displayedIndex);
    }

    public ResultInformation(Accommodation accommodation, Index displayedIndex) {
        this(null, null, requireNonNull(accommodation), displayedIndex);
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
        return this.displayedIndex;
    }
}
