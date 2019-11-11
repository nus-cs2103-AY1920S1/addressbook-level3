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
import seedu.address.model.password.analyser.match.KeyboardMatch;
import seedu.address.model.password.analyser.result.KeyboardResult;
import seedu.address.model.password.analyser.result.Result;
import seedu.address.model.password.analyser.result.ResultOutcome;

class KeyboardAnalyserTest {

    @Test
    void analyse() {
        Password passwordWithMatches = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("qazxswedcvfr"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        Password passwordWithoutMatches = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("adgjl"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));

        ArrayList<Password> passwordArrayList = new ArrayList<>();
        passwordArrayList.add(passwordWithMatches);
        passwordArrayList.add(passwordWithoutMatches);
        KeyboardAnalyser a = new KeyboardAnalyser();
        List<Result> actualResults = a.analyse(passwordArrayList);

        List<KeyboardMatch> expectedMatches1 = new ArrayList<>();
        expectedMatches1.add(new KeyboardMatch(0, 11, "qazxswedcvfr", 7));
        List<KeyboardMatch> expectedMatches2 = new ArrayList<>();

        List<Result> expectedResults = new ArrayList<>();
        expectedResults.add(new KeyboardResult(passwordWithMatches, ResultOutcome.FAIL, expectedMatches1));
        expectedResults.add(new KeyboardResult(passwordWithoutMatches, ResultOutcome.PASS, expectedMatches2));

        for (int i = 0; i < expectedResults.size(); i++) {
            assertEquals(actualResults.get(i), expectedResults.get(i));
        }

    }
}
