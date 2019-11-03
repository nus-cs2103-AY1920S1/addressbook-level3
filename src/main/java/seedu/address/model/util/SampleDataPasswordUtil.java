package seedu.address.model.util;

import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.Date;

import seedu.address.model.PasswordBook;
import seedu.address.model.ReadOnlyPasswordBook;
import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordModifiedAt;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.Website;

/**
 * Contains utility methods for populating {@code PasswordBook} with sample data.
 */
public class SampleDataPasswordUtil {
    public static Password[] getSamplePasswords() {
        return new Password[] {
            new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                        new PasswordValue("password"), new PasswordModifiedAt(new Date()),
                        new Website("www.gmail.com"), getTagSet("SocialMedia")),
            new Password(new PasswordDescription("gmail"), new Username("Randomguy1"),
                        new PasswordValue("password1"), new PasswordModifiedAt(new Date()),
                        new Website("www.gmail.com"), getTagSet("Work")),
            new Password(new PasswordDescription("Gmail"), new Username("Randomguy2"),
                        new PasswordValue("password2"), new PasswordModifiedAt(new Date()),
                        new Website("www.gmail.com"), getTagSet("SocialMedia")),
            new Password(new PasswordDescription("Gmail"), new Username("Randomguy3"),
                        new PasswordValue("password3"), new PasswordModifiedAt(new Date()),
                        new Website("www.gmail.com"), getTagSet("Game")),
            new Password(new PasswordDescription("Gmail"), new Username("Randomguy4"),
                        new PasswordValue("password4"), new PasswordModifiedAt(new Date()),
                        new Website("www.gmail.com"), getTagSet("Help")),
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
