package seedu.address.model.password.analyser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Dictionary;
import seedu.address.commons.exceptions.DictionaryException;
import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordModifiedAt;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.Website;
import seedu.address.model.password.analyser.match.DictionaryMatch;

class DictionaryAnalyserTest {

    @Test
    void getAllMatches_passwordWithoutLeet() {
        try {
            Dictionary d = Dictionary.build("passwords.txt");
            Password p1 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                    new PasswordValue("qwerty"), new PasswordModifiedAt(new Date()),
                    new Website("NIL"), getTagSet("SocialMedia"));
            DictionaryAnalyser a = new DictionaryAnalyser(d);
            List<DictionaryMatch> actualMatches = a.getAllMatches(p1.getPasswordValue().value);
            System.out.println(actualMatches.toString());
            List<DictionaryMatch> expectedMatches = new ArrayList<>();
            expectedMatches.add(new DictionaryMatch(0, 5, "qwerty", 4));
            expectedMatches.add(new DictionaryMatch(0, 4, "qwert", 420));
            expectedMatches.add(new DictionaryMatch(0, 3, "qwer", 2322));
            expectedMatches.add(new DictionaryMatch(1, 4, "wert", 7620));
            expectedMatches.add(new DictionaryMatch(1, 5, "werty", 8340));
            expectedMatches.add(new DictionaryMatch(2, 5, "erty", 53112));
            for (int i = 0; i < expectedMatches.size(); i++) {
                assertEquals(actualMatches.get(i), expectedMatches.get(i));
            }
        } catch (DictionaryException e) {
            System.out.println("should not happen");
            e.printStackTrace();
        }

    }

    @Test
    void getAllMatches_passwordWithLeet() {
        try {
            Dictionary d = Dictionary.build("passwords.txt");
            Password p1 = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                    new PasswordValue("qw3r+y"), new PasswordModifiedAt(new Date()),
                    new Website("NIL"), getTagSet("SocialMedia"));
            DictionaryAnalyser a = new DictionaryAnalyser(d);
            List<DictionaryMatch> actualMatches = a.getAllMatches(p1.getPasswordValue().value);
            System.out.println(actualMatches.toString());

            List<DictionaryMatch> expectedMatches = new ArrayList<>();
            expectedMatches.add(new DictionaryMatch(0, 5, "qwerty", 4));
            expectedMatches.add(new DictionaryMatch(0, 4, "qwert", 420));
            expectedMatches.add(new DictionaryMatch(0, 3, "qwer", 2322));
            expectedMatches.add(new DictionaryMatch(1, 4, "wert", 7620));
            expectedMatches.add(new DictionaryMatch(1, 5, "werty", 8340));
            expectedMatches.add(new DictionaryMatch(2, 5, "erty", 53112));

            for (int i = 0; i < expectedMatches.size(); i++) {
                assertEquals(actualMatches.get(i), expectedMatches.get(i));
            }
        } catch (DictionaryException e) {
            System.out.println("should not happen");
            e.printStackTrace();
        }

    }

}
