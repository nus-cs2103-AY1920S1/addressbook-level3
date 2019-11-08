package seedu.address.model.password.analyser;

import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordModifiedAt;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.Website;
import seedu.address.model.password.analyser.match.KeyboardMatch;

class KeyboardAnalyserTest {

    @Test
    void getAllMatches() {
        Password p = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("qazxswedcvfr"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        KeyboardAnalyser a = new KeyboardAnalyser();
        List<KeyboardMatch> actualMatches = a.getAllMatches(p.getPasswordValue().value);

        System.out.println(actualMatches.toString());

        Password p1 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("!@#$%^"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        List<KeyboardMatch> actualMatches2 = a.getAllMatches(p1.getPasswordValue().value);

        System.out.println(actualMatches2.toString());
    }
}
