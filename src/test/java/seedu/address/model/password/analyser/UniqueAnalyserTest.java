package seedu.address.model.password.analyser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordModifiedAt;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.Website;
import seedu.address.model.password.analyser.match.UniqueMatch;

class UniqueAnalyserTest {

    @Test
    void getAllMatches_listContainAllUniquePassword() {

        Password p1 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("password"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        Password p2 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("p@ssw0rd"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        Password p3 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("password123"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        Password p4 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("ppaassword"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        UniqueAnalyser a = new UniqueAnalyser();
        ArrayList<Password> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);

        HashMap<String, ArrayList<Password>> expectedPasswordToAccounts = a.initHash(list);
        List<UniqueMatch> actualMatches = a.getAllMatches(p1,
                expectedPasswordToAccounts.get(p1.getPasswordValue().value));
        assertTrue(actualMatches.isEmpty());
    }

    @Test
    void getAllMatches_listContainNonUniquePassword() {

        Password p1 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("password"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        Password p2 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("password"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        Password p3 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("password"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        Password p4 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("password"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        UniqueAnalyser a = new UniqueAnalyser();
        ArrayList<Password> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);

        HashMap<String, ArrayList<Password>> expectedPasswordToAccounts = a.initHash(list);
        List<UniqueMatch> actualMatches = a.getAllMatches(p1,
                expectedPasswordToAccounts.get(p1.getPasswordValue().value));

        List<UniqueMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new UniqueMatch(0, 7, "password", p2));
        expectedMatches.add(new UniqueMatch(0, 7, "password", p3));
        expectedMatches.add(new UniqueMatch(0, 7, "password", p4));

        for (int i = 0; i < expectedMatches.size(); i++) {
            assertEquals(expectedMatches.get(i), actualMatches.get(i));
        }

    }

}
