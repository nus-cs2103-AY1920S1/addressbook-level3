package seedu.address.model.password.analyser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordModifiedAt;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.Website;
import seedu.address.model.password.analyser.result.ResultOutcome;
import seedu.address.model.password.analyser.result.StrengthResult;


class StrengthAnalyserTest {

    @Test
    void calculateStrength_passwordWeak() {
        Password p1 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("password"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        StrengthResult actualResult1 = StrengthAnalyser.calculateStrength(p1);
        StrengthResult expectedResult1 = new StrengthResult(p1, ResultOutcome.WEAK, true,
                true, false, false, false);
        assertEquals(actualResult1, expectedResult1);

        Password p2 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("!@#"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        StrengthResult actualResult2 = StrengthAnalyser.calculateStrength(p2);
        StrengthResult expectedResult2 = new StrengthResult(p2, ResultOutcome.WEAK, false,
                false, false, false, true);
        assertEquals(actualResult2, expectedResult2);

        Password p3 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("123asddd"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        StrengthResult actualResult3 = StrengthAnalyser.calculateStrength(p3);
        System.out.println(actualResult3);
        StrengthResult expectedResult3 = new StrengthResult(p3, ResultOutcome.MODERATE, true,
                true, false, true, false);
        assertEquals(actualResult3, expectedResult3);
    }

    @Test
    void calculateStrength_passwordStrong() {
        Password p1 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("P@sSw0Rd"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        StrengthResult actualResult1 = StrengthAnalyser.calculateStrength(p1);
        StrengthResult expectedResult1 = new StrengthResult(p1, ResultOutcome.STRONG, true,
                true, true, true, true);
        assertEquals(actualResult1, expectedResult1);
    }
}
