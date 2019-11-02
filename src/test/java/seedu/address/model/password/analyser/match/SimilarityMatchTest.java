package seedu.address.model.password.analyser.match;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordModifiedAt;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.Website;

class SimilarityMatchTest {

    @Test
    void compareTo_equalSimilarity_returnZero() {
        Password p = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("password"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        SimilarityMatch instanceOne = new SimilarityMatch(0, 4, "token", p, 0.7);
        SimilarityMatch instanceTwo = new SimilarityMatch(0, 4, "nekot", p, 0.7);
        assertTrue(instanceOne.compareTo(instanceTwo) == 0);
    }

    @Test
    void compareTo_unequalRank_returnPositive() {
        Password p = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
                new PasswordValue("password"), new PasswordModifiedAt(new Date()),
                new Website("NIL"), getTagSet("SocialMedia"));
        SimilarityMatch instanceOne = new SimilarityMatch(0, 4, "lowerRank", p, 0.3);
        SimilarityMatch instanceTwo = new SimilarityMatch(0, 4, "higherRank", p, 0.5);
        assertTrue(instanceOne.compareTo(instanceTwo) > 0);
    }

}
