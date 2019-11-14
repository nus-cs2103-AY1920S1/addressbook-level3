package seedu.address.testutil;

import static seedu.address.testutil.TypicalIds.ID_FIRST_PARTICIPANT;

import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;

/**
 * Builds a valid {@link Participant} to facilitate testing.
 */
public class ParticipantBuilder {

    public static final Id DEFAULT_ID = ID_FIRST_PARTICIPANT;
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";

    private Id id;
    private Name name;
    private Phone phone;
    private Email email;

    public ParticipantBuilder() {
        id = DEFAULT_ID;
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
    }

    /**
     * Initializes the ParticipantBuilder with the data of {@code participantToCopy}.
     */
    public ParticipantBuilder(Participant participantToCopy) {
        id = participantToCopy.getId();
        name = participantToCopy.getName();
        phone = participantToCopy.getPhone();
        email = participantToCopy.getEmail();
    }

    /**
     * Sets the {@code Id} of the {@code Participant} that we are building.
     */
    public ParticipantBuilder withId(int id) {
        this.id = new Id(PrefixType.P, id);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Participant} that we are building.
     */
    public ParticipantBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Participant} that we are building.
     */
    public ParticipantBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Participant} that we are building.
     */
    public ParticipantBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * This builds a standard {@code Participant} object.
     *
     * @return Participant
     */
    public Participant build() {
        return new Participant(name, id, email, phone);
    }

}
