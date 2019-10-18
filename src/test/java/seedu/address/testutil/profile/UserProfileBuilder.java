package seedu.address.testutil.profile;

import seedu.address.model.profile.UserProfile;
import seedu.address.model.profile.person.Person;

/**
 * A utility class to help with building UserProfile objects.
 * Example usage: <br>
 *     {@code UserProfile dc = new UserProfileBuilder().withPerson("John", "Doe").build();}
 */
public class UserProfileBuilder {

    private UserProfile userProfile;

    public UserProfileBuilder() {
        userProfile = new UserProfile();
    }

    public UserProfileBuilder(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * Adds a new {@code Person} to the {@code UserProfile} that we are building.
     */
    public UserProfileBuilder withPerson(Person person) {
        userProfile.addPerson(person);
        return this;
    }

    public UserProfile build() {
        return userProfile;
    }
}
