package seedu.address.logic.commands.trips.edit;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.Date;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.itinerary.trip.TripId;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class EditTripFieldCommand {

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditTripDescriptor {
        private Name name;
        private Date from;
        private Date to;
        private Location destintaion;
        private Expenditure totalBudget;
        private DayList dayList;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTripDescriptor(EditTripDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCommand.EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditCommand.EditPersonDescriptor e = (EditCommand.EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
