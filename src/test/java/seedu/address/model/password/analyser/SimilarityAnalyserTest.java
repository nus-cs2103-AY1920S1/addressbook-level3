package seedu.address.model.password.analyser;

import org.junit.jupiter.api.Test;
import seedu.address.model.password.Description;
import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.analyser.match.SimilarityMatch;
import seedu.address.model.password.analyser.result.SimilarityResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

class SimilarityAnalyserTest {
    @Test
    void getAllMatches_withoutSimilarPasswords() {
        Password p1 = new Password(new Description("Gmail"), new Username("Randomguy"),
                new PasswordValue("password"), getTagSet("SocialMedia"));

        Password p2 = new Password(new Description("Gmail"), new Username("Randomguy"),
                new PasswordValue("asd"), getTagSet("SocialMedia"));

        Password p3 = new Password(new Description("Gmail"), new Username("Randomguy"),
                new PasswordValue("fgh"), getTagSet("SocialMedia"));

        Password p4 = new Password(new Description("Gmail"), new Username("Randomguy"),
                new PasswordValue("jkl"), getTagSet("SocialMedia"));

        ArrayList<Password> list = new ArrayList<>();
        list.add(p2);
        list.add(p3);
        list.add(p4);
        SimilarityAnalyser a = new SimilarityAnalyser();
        List<SimilarityMatch> actualMatches = a.getAllMatches(p1, list);

        assertTrue(actualMatches.isEmpty());
    }

    @Test
    void getAllMatches_withSimilarPasswords() {
        Password p1 = new Password(new Description("Gmail"), new Username("Randomguy"),
                new PasswordValue("password"), getTagSet("SocialMedia"));

        Password p2 = new Password(new Description("Gmail"), new Username("Randomguy"),
                new PasswordValue("p@ssw0rd"), getTagSet("SocialMedia"));

        Password p3 = new Password(new Description("Gmail"), new Username("Randomguy"),
                new PasswordValue("password123"), getTagSet("SocialMedia"));

        Password p4 = new Password(new Description("Gmail"), new Username("Randomguy"),
                new PasswordValue("ppaassword"), getTagSet("SocialMedia"));

        ArrayList<Password> list = new ArrayList<>();
        list.add(p2);
        list.add(p3);
        list.add(p4);
        SimilarityAnalyser a = new SimilarityAnalyser();
        List<SimilarityMatch> actualMatches = a.getAllMatches(p1, list);

        List<SimilarityMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new SimilarityMatch(0, 7, "p@ssw0rd", p2, 0.75));
        expectedMatches.add(new SimilarityMatch(0, 7, "password123", p3, 0.7272727272727273));
        expectedMatches.add(new SimilarityMatch(0, 7, "password123", p4, 0.8));

        for ( int i = 0 ; i < expectedMatches.size(); i++) {
            assertEquals(actualMatches.get(i), expectedMatches.get(i));
        }
    }
}