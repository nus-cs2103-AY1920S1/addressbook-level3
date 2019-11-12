package seedu.address.testutil;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordModifiedAt;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.Website;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataPasswordUtil;

/**
 * A utility class to help with building Password objects.
 */
public class PasswordBuilder {
    public static final String DEFAULT_USERNAME = "Randomguy";
    public static final String DEFAULT_PASSWORD_VALUE = "%^&(**&RFVG";
    public static final String DEFAULT_PASSWORD_DESCRIPTION = "Test 2";

    private Username username;
    private PasswordValue passwordValue;
    private PasswordDescription passwordDescription;
    private Website website;
    private PasswordModifiedAt passwordModifiedAt;
    private Set<Tag> tags;

    public PasswordBuilder() {
        username = new Username(DEFAULT_USERNAME);
        passwordValue = new PasswordValue(DEFAULT_PASSWORD_VALUE);
        passwordDescription = new PasswordDescription(DEFAULT_PASSWORD_DESCRIPTION);
        website = new Website("NIL");
        passwordModifiedAt = new PasswordModifiedAt(new Date());
        tags = new HashSet<>();
    }

    /**
     * Initializes the PasswordBuilder with the data of {@code passwprdToCopy}.
     */
    public PasswordBuilder(Password passwordToCopy) {
        username = passwordToCopy.getUsername();
        passwordValue = passwordToCopy.getPasswordValue();
        passwordDescription = passwordToCopy.getPasswordDescription();
        website = passwordToCopy.getWebsite();
        passwordModifiedAt = passwordToCopy.getPasswordModifiedAt();
        tags = new HashSet<>(passwordToCopy.getTags());
    }

    /**
     * Sets the {@code Username} of the {@code Password} that we are building.
     */
    public PasswordBuilder withUsername(String name) {
        this.username = new Username(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Password}
     * that we are building.
     */
    public PasswordBuilder withTags(String ... tags) {
        this.tags = SampleDataPasswordUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code PasswordValue} of the {@code Password} that we are building.
     */
    public PasswordBuilder withPasswordValue(String passwordValue) {
        this.passwordValue = new PasswordValue(passwordValue);
        return this;
    }

    /**
     * Sets the {@code PasswordDescription} of the {@code Password} that we are building.
     */
    public PasswordBuilder withPasswordDescription(String passwordDescription) {
        this.passwordDescription = new PasswordDescription(passwordDescription);
        return this;
    }

    /**
     * Sets the {@code Website} of the {@code Password} that we are building.
     */
    public PasswordBuilder withWebsite(String website) {
        this.website = new Website(website);
        return this;
    }

    /**
     * Builds a new Password with inputted values
     * @return Password
     */
    public Password build() {
        return new Password(passwordDescription,
                username, passwordValue, passwordModifiedAt, website, tags);
    }

}
