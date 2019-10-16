package seedu.address.model.util;

import static seedu.address.model.util.SampleDataUtil.getTagSet;

import seedu.address.model.PasswordBook;
import seedu.address.model.ReadOnlyPasswordBook;
import seedu.address.model.password.Description;
import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;

/**
 * Contains utility methods for populating {@code PasswordBook} with sample data.
 */
public class SampleDataPasswordUtil {
    public static Password[] getSamplePasswords() {
        return new Password[] {
            new Password(new Description("Gmail"), new Username("Randomguy"),
                        new PasswordValue("password"), getTagSet("Social Media")),
            new Password(new Description("Gmail1"), new Username("Randomguy1"),
                        new PasswordValue("password1"), getTagSet("Work")),
            new Password(new Description("Gmail2"), new Username("Randomguy2"),
                        new PasswordValue("password2"), getTagSet("Social Media")),
            new Password(new Description("Gmail3"), new Username("Randomguy3"),
                        new PasswordValue("password3"), getTagSet("Game")),
            new Password(new Description("Gmail4"), new Username("Randomguy4"),
                        new PasswordValue("password4"), getTagSet("Help")),
        };
    }

    public static ReadOnlyPasswordBook getSamplePasswordBook() {
        PasswordBook samplePb = new PasswordBook();
        for (Password samplePassword : getSamplePasswords()) {
            samplePb.addPassword(samplePassword);
        }
        return samplePb;
    }
}
