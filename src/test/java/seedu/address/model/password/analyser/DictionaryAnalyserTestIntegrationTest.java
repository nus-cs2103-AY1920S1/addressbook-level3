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
import seedu.address.model.password.analyser.match.DictionaryMatch;
import seedu.address.model.password.analyser.resources.Dictionary;
import seedu.address.model.password.analyser.result.DictionaryResult;
import seedu.address.model.password.analyser.result.Result;
import seedu.address.model.password.analyser.result.ResultOutcome;

class DictionaryAnalyserTestIntegrationTest {

    @Test
    public void analyse_passwordWithoutLeet() {
        ArrayList<Password> passwordArrayList = new ArrayList<>();
        Dictionary d = Dictionary.build("passwords.txt");
        //matches will be found
        Password password = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("qwerty"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        //matches will not be found
        Password password2 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("~sad"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        passwordArrayList.add(password);
        passwordArrayList.add(password2);

        DictionaryAnalyser a = new DictionaryAnalyser(d);

        List<Result> actualResults = a.analyse(passwordArrayList);

        List<DictionaryMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new DictionaryMatch(0, 5, "qwerty", 4));
        expectedMatches.add(new DictionaryMatch(0, 4, "qwert", 420));
        expectedMatches.add(new DictionaryMatch(0, 3, "qwer", 2322));
        expectedMatches.add(new DictionaryMatch(1, 4, "wert", 7620));
        expectedMatches.add(new DictionaryMatch(1, 5, "werty", 8340));
        expectedMatches.add(new DictionaryMatch(2, 5, "erty", 53112));

        ArrayList<Result> expectedResults = new ArrayList<>();
        expectedResults.add(new DictionaryResult(password, ResultOutcome.FAIL, expectedMatches));
        expectedResults.add(new DictionaryResult(password2, ResultOutcome.PASS, new ArrayList<>()));

        for (int i = 0; i < expectedResults.size(); i++) {
            assertEquals(actualResults.get(i), expectedResults.get(i));
        }
    }

    @Test
    public void analyse_passwordWithLeet() {
        ArrayList<Password> passwordArrayList = new ArrayList<>();
        Dictionary d = Dictionary.build("passwords.txt");
        //matches will be found
        Password password = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("qw3r+y"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        //matches will not be found
        Password password2 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("~54D"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        passwordArrayList.add(password);
        passwordArrayList.add(password2);

        DictionaryAnalyser a = new DictionaryAnalyser(d);

        List<Result> actualResults = a.analyse(passwordArrayList);

        List<DictionaryMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new DictionaryMatch(0, 5, "qwerty", 4));
        expectedMatches.add(new DictionaryMatch(0, 4, "qwert", 420));
        expectedMatches.add(new DictionaryMatch(0, 3, "qwer", 2322));
        expectedMatches.add(new DictionaryMatch(1, 4, "wert", 7620));
        expectedMatches.add(new DictionaryMatch(1, 5, "werty", 8340));
        expectedMatches.add(new DictionaryMatch(2, 5, "erty", 53112));

        ArrayList<Result> expectedResults = new ArrayList<>();
        expectedResults.add(new DictionaryResult(password, ResultOutcome.FAIL, expectedMatches));
        expectedResults.add(new DictionaryResult(password2, ResultOutcome.PASS, new ArrayList<>()));

        for (int i = 0; i < expectedResults.size(); i++) {
            assertEquals(actualResults.get(i), expectedResults.get(i));
        }
    }

}
