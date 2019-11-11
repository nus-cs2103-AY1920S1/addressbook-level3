package seedu.address.model.util;

import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.PasswordBook;
import seedu.address.model.ReadOnlyPasswordBook;
import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordModifiedAt;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.Website;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code PasswordBook} with sample data.
 */
public class SampleDataPasswordUtil {
    public static Password[] getSamplePasswords() {
        return new Password[] {
            new Password(new PasswordDescription("DropBox"), new Username("SecureIT@secureIT.com"),
                        new PasswordValue("password1234"), new PasswordModifiedAt(new Date()),
                        new Website("www.dropbox.com"), getTagSet("Work")),
            new Password(new PasswordDescription("Facebook"), new Username("SecureIT@secureIT.com"),
                        new PasswordValue("password1234"), new PasswordModifiedAt(new Date()),
                        new Website("www.facebook.com"), getTagSet("Marketing")),
            new Password(new PasswordDescription("GitHub"), new Username("JohnDoe"),
                        new PasswordValue("password3"), new PasswordModifiedAt(new Date()),
                        new Website("https://github.com/login"), getTagSet("Work")),
            new Password(new PasswordDescription("Gmail"), new Username("SecureIT@secureIT.com"),
                        new PasswordValue("password1"), new PasswordModifiedAt(new Date()),
                            new Website("https://accounts.google.com/signin/v2/identifier?hl=en&p"
                                    + "assive=true&continue=https%3A%2F%2Fwww.google.com%2Fsearch%3Fq"
                                    + "%3Dgmail%2Bsign%2Bin%26oq%3Dgmail%2Bsign%2Bin%26aqs%3Dchrome..69"
                                    + "i57.1886j0j1%26sourceid%3Dchrome%26ie%3DUTF-8&flowName=GlifWebSig"
                                    + "nIn&flowEntry=ServiceLogin"), getTagSet("Work")),
            new Password(new PasswordDescription("Gmail"), new Username("JohnDoe@gmail.com"),
                        new PasswordValue("password2"), new PasswordModifiedAt(new Date()),
                        new Website("https://accounts.google.com/signin/v2/identifier?hl=en&p"
                                + "assive=true&continue=https%3A%2F%2Fwww.google.com%2Fsearch%3Fq"
                                + "%3Dgmail%2Bsign%2Bin%26oq%3Dgmail%2Bsign%2Bin%26aqs%3Dchrome..69"
                                + "i57.1886j0j1%26sourceid%3Dchrome%26ie%3DUTF-8&flowName=GlifWebSig"
                                + "nIn&flowEntry=ServiceLogin"), getTagSet("Personal")),
            new Password(new PasswordDescription("Slack"), new Username("JohnDoe"),
                        new PasswordValue("DRCTFYG$%^&*"),
                        new PasswordModifiedAt(new Date(new Date().getTime() - 1000L * 60L * 60L * 24L * 335L)),
                        new Website("https://slack.com/signin"), getTagSet("Work")),
            new Password(new PasswordDescription("Instagram"), new Username("SecureIT"),
                        new PasswordValue("pascnj%^&"),
                        new PasswordModifiedAt(new Date(new Date().getTime() - 1000L * 60L * 60L * 24L * 366L)),
                        new Website("https://slack.com/signin"), getTagSet("Work")),
        };
    }

    public static ReadOnlyPasswordBook getSamplePasswordBook() {
        PasswordBook samplePb = new PasswordBook();
        for (Password samplePassword : getSamplePasswords()) {
            samplePb.addPassword(samplePassword);
        }
        return samplePb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
