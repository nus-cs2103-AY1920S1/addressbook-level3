package seedu.address.model.password.analyser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordModifiedAt;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.Website;
import seedu.address.model.password.analyser.match.SequenceMatch;
import seedu.address.model.password.analyser.result.Result;
import seedu.address.model.password.analyser.result.ResultOutcome;
import seedu.address.model.password.analyser.result.SequenceResult;

class SequenceAnalyserTest {

    @Test
    void analyse_passwordWithForwardNumericalSequence() {
        ArrayList<Password> passwordArrayList = new ArrayList<>();
        Password p = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("123pass456word123456"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        passwordArrayList.add(p);

        SequenceAnalyser a = new SequenceAnalyser();
        List<Result> actualResults = a.analyse(passwordArrayList);

        ArrayList<SequenceMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new SequenceMatch(0, 2, "123"));
        expectedMatches.add(new SequenceMatch(7, 9, "456"));
        expectedMatches.add(new SequenceMatch(14, 19, "123456"));

        List<Result> expectedResults = new ArrayList<>();
        expectedResults.add(new SequenceResult(p, ResultOutcome.FAIL, expectedMatches));
        for (int i = 0; i < actualResults.size(); i++) {
            assertEquals(actualResults.get(i), expectedResults.get(i));
        }

    }

    @Test
    void analyse_passwordWithBackwardNumericalSequence() {
        ArrayList<Password> passwordArrayList = new ArrayList<>();
        Password p = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("321pass654word654321"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        passwordArrayList.add(p);

        SequenceAnalyser a = new SequenceAnalyser();
        List<Result> actualResults = a.analyse(passwordArrayList);

        ArrayList<SequenceMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new SequenceMatch(0, 2, "321"));
        expectedMatches.add(new SequenceMatch(7, 9, "654"));
        expectedMatches.add(new SequenceMatch(14, 19, "654321"));

        List<Result> expectedResults = new ArrayList<>();
        expectedResults.add(new SequenceResult(p, ResultOutcome.FAIL, expectedMatches));
        for (int i = 0; i < actualResults.size(); i++) {
            assertEquals(actualResults.get(i), expectedResults.get(i));
        }
    }

    @Test
    void analyse_passwordWithForwardAlphaSequence() {
        ArrayList<Password> passwordArrayList = new ArrayList<>();
        Password p = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("ABCpassXYZwordABCXYZ"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        passwordArrayList.add(p);

        SequenceAnalyser a = new SequenceAnalyser();
        List<Result> actualResults = a.analyse(passwordArrayList);

        ArrayList<SequenceMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new SequenceMatch(0, 2, "ABC"));
        expectedMatches.add(new SequenceMatch(7, 9, "XYZ"));
        expectedMatches.add(new SequenceMatch(14, 16, "ABC"));
        expectedMatches.add(new SequenceMatch(17, 19, "XYZ"));

        List<Result> expectedResults = new ArrayList<>();
        expectedResults.add(new SequenceResult(p, ResultOutcome.FAIL, expectedMatches));
        for (int i = 0; i < actualResults.size(); i++) {
            assertEquals(actualResults.get(i), expectedResults.get(i));
        }
    }

    @Test
    void analyse_passwordWithBackwardAlphaSequence() {
        ArrayList<Password> passwordArrayList = new ArrayList<>();
        Password p = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("CBApassZYXwordCBAZYX"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        passwordArrayList.add(p);

        SequenceAnalyser a = new SequenceAnalyser();
        List<Result> actualResults = a.analyse(passwordArrayList);

        ArrayList<SequenceMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new SequenceMatch(0, 2, "CBA"));
        expectedMatches.add(new SequenceMatch(7, 9, "ZYX"));
        expectedMatches.add(new SequenceMatch(14, 16, "CBA"));
        expectedMatches.add(new SequenceMatch(17, 19, "ZYX"));

        List<Result> expectedResults = new ArrayList<>();
        expectedResults.add(new SequenceResult(p, ResultOutcome.FAIL, expectedMatches));
        for (int i = 0; i < actualResults.size(); i++) {
            assertEquals(actualResults.get(i), expectedResults.get(i));
        }
    }

    @Test
    void analyse_passwordWithoutSequence() {
        ArrayList<Password> passwordArrayList = new ArrayList<>();
        Password p = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("asdfgd"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        passwordArrayList.add(p);

        SequenceAnalyser a = new SequenceAnalyser();
        List<Result> actualResults = a.analyse(passwordArrayList);

        ArrayList<SequenceMatch> expectedMatches = new ArrayList<>();

        List<Result> expectedResults = new ArrayList<>();
        expectedResults.add(new SequenceResult(p, ResultOutcome.PASS, expectedMatches));
        for (int i = 0; i < actualResults.size(); i++) {
            assertEquals(actualResults.get(i), expectedResults.get(i));
        }
    }

}
