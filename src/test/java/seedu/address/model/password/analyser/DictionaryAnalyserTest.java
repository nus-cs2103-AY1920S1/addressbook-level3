package seedu.address.model.password.analyser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.getTagSet;
import static seedu.address.testutil.Assert.assertThrows;

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
import seedu.address.model.password.analyser.match.DictionaryMatch;
import seedu.address.model.password.analyser.resources.Dictionary;
import seedu.address.model.password.analyser.resources.DictionaryStub;
import seedu.address.model.password.analyser.result.DictionaryResult;
import seedu.address.model.password.analyser.result.Result;
import seedu.address.model.password.analyser.result.ResultOutcome;

class DictionaryAnalyserTest {
    @Test
    public void constructor_nullDictionary_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DictionaryAnalyser(null));
    }

    @Test
    public void getAllMatches_dictionaryStub_foundMatches() {
        Dictionary dictionaryStub = new DictionaryStub("dummy", new HashMap<>());
        Password password = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("qwerty"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        DictionaryAnalyser a = new DictionaryAnalyser(dictionaryStub);
        List<DictionaryMatch> actualMatches = a.getAllMatches(password.getPasswordValue().value);
        List<DictionaryMatch> expectedMatches = new ArrayList<>();
        expectedMatches.add(new DictionaryMatch(0, 3, "qwer", 1));
        expectedMatches.add(new DictionaryMatch(0, 5, "qwerty", 2));
        expectedMatches.add(new DictionaryMatch(0, 4, "qwert", 3));

        for (int i = 0; i < expectedMatches.size(); i++) {
            assertEquals(actualMatches.get(i), expectedMatches.get(i));
        }

    }

    @Test
    public void getAllMatches_dictionaryStub_noFoundMatches() {
        Dictionary dictionaryStub = new DictionaryStub("dummy", new HashMap<>());
        Password password = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("asdf"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        DictionaryAnalyser a = new DictionaryAnalyser(dictionaryStub);
        List<DictionaryMatch> actualMatches = a.getAllMatches(password.getPasswordValue().value);

        assertTrue(actualMatches.isEmpty());

    }

    @Test
    public void analyse_dictionaryStub() {
        Dictionary dictionaryStub = new DictionaryStub("dummy", new HashMap<>());
        Password password1 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("qwerty"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        Password password2 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("asdf"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        ArrayList<Password> passwordArrayList = new ArrayList<>();
        passwordArrayList.add(password1);
        passwordArrayList.add(password2);

        List<DictionaryMatch> expectedMatches1 = new ArrayList<>();
        expectedMatches1.add(new DictionaryMatch(0, 3, "qwer", 1));
        expectedMatches1.add(new DictionaryMatch(0, 5, "qwerty", 2));
        expectedMatches1.add(new DictionaryMatch(0, 4, "qwert", 3));

        List<DictionaryMatch> expectedMatches2 = new ArrayList<>();

        DictionaryAnalyser a = new DictionaryAnalyser(dictionaryStub);
        List<Result> actualResults = a.analyse(passwordArrayList);
        List<Result> expectedResults = new ArrayList<>();
        expectedResults.add(new DictionaryResult(password1, ResultOutcome.FAIL, expectedMatches1));
        expectedResults.add(new DictionaryResult(password2, ResultOutcome.PASS, expectedMatches2));

        for (int i = 0; i < expectedResults.size(); i++) {
            assertEquals(actualResults.get(i), expectedResults.get(i));
        }
    }

}
