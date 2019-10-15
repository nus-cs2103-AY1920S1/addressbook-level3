package seedu.address.testutil;

import seedu.address.profile.UserProfile;
import seedu.address.profile.person.Person;

/**
 * A utility class to help with building UserProfile objects.
 * Example usage: <br>
 *     {@code UserProfile dc = new DukeCooksBuilder().withPerson("John", "Doe").build();}
 */
public class DukeCooksBuilder {

    private UserProfile userProfile;

    public DukeCooksBuilder() {
        userProfile = new UserProfile();
    }

    public DukeCooksBuilder(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * Adds a new {@code Person} to the {@code UserProfile} that we are building.
     */
    public DukeCooksBuilder withPerson(Person person) {
        userProfile.addPerson(person);
        return this;
    }

    public UserProfile build() {
        return userProfile;
    }
}
