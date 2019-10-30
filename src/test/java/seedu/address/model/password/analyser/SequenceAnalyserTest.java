package seedu.address.model.password.analyser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.password.Description;
import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.analyser.match.SequenceMatch;

class SequenceAnalyserTest {

    @Test
    void analyse_passwordWithForwardNumericalSequence() {

        Password p = new Password(new Description("Gmail"), new Username("Randomguy"),
                new PasswordValue("123pass456word123456"), getTagSet("SocialMedia"));

        SequenceAnalyser a = new SequenceAnalyser();
        List<SequenceMatch> actualMatches = a.getAllMatches(p.getPasswordValue().value);

        ArrayList<SequenceMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new SequenceMatch(0, 2, "123"));
        expectedMatches.add(new SequenceMatch(7, 9, "456"));
        expectedMatches.add(new SequenceMatch(14, 19, "123456"));

        for (int i = 0; i < actualMatches.size(); i++) {
            assertEquals(actualMatches.get(i), expectedMatches.get(i));
        }

    }

    @Test
    void analyse_passwordWithBackwardNumericalSequence() {

        Password p = new Password(new Description("Gmail"), new Username("Randomguy"),
                new PasswordValue("321pass654word654321"), getTagSet("SocialMedia"));

        SequenceAnalyser a = new SequenceAnalyser();
        List<SequenceMatch> actualMatches = a.getAllMatches(p.getPasswordValue().value);

        ArrayList<SequenceMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new SequenceMatch(0, 2, "321"));
        expectedMatches.add(new SequenceMatch(7, 9, "654"));
        expectedMatches.add(new SequenceMatch(14, 19, "654321"));

        for (int i = 0; i < actualMatches.size(); i++) {
            assertEquals(actualMatches.get(i), expectedMatches.get(i));
        }
    }

    @Test
    void analyse_passwordWithForwardAlphaSequence() {
        Password p = new Password(new Description("Gmail"), new Username("Randomguy"),
                new PasswordValue("ABCpassXYZwordABCXYZ"), getTagSet("SocialMedia"));

        SequenceAnalyser a = new SequenceAnalyser();
        List<SequenceMatch> actualMatches = a.getAllMatches(p.getPasswordValue().value);

        ArrayList<SequenceMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new SequenceMatch(0, 2, "ABC"));
        expectedMatches.add(new SequenceMatch(7, 9, "XYZ"));
        expectedMatches.add(new SequenceMatch(14, 16, "ABC"));
        expectedMatches.add(new SequenceMatch(17, 19, "XYZ"));

        for (int i = 0; i < actualMatches.size(); i++) {
            assertEquals(actualMatches.get(i), expectedMatches.get(i));
        }
    }

    @Test
    void analyse_passwordWithBackwardAlphaSequence() {
        Password p = new Password(new Description("Gmail"), new Username("Randomguy"),
                new PasswordValue("CBApassZYXwordCBAZYX"), getTagSet("SocialMedia"));

        SequenceAnalyser a = new SequenceAnalyser();
        List<SequenceMatch> actualMatches = a.getAllMatches(p.getPasswordValue().value);

        ArrayList<SequenceMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new SequenceMatch(0, 2, "CBA"));
        expectedMatches.add(new SequenceMatch(7, 9, "ZYX"));
        expectedMatches.add(new SequenceMatch(14, 16, "CBA"));
        expectedMatches.add(new SequenceMatch(17, 19, "ZYX"));

        for (int i = 0; i < actualMatches.size(); i++) {
            assertEquals(actualMatches.get(i), expectedMatches.get(i));
        }
    }
}
