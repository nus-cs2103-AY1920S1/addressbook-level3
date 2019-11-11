package seedu.address.model.password.analyser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.util.LeetUtil;
import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.DictionaryMatch;
import seedu.address.model.password.analyser.resources.Dictionary;
import seedu.address.model.password.analyser.result.DictionaryResult;
import seedu.address.model.password.analyser.result.Result;
import seedu.address.model.password.analyser.result.ResultOutcome;

/**
 * Represents an {@code Analyser} that analyses passwords in password book for common dictionary string.
 */
public class DictionaryAnalyser implements Analyser {

    private static final String MESSAGE_HEADER = "Analysing passwords for commonly used passwords:\n";
    private Dictionary dictionary;

    public DictionaryAnalyser(Dictionary dictionary) {
        requireNonNull(dictionary);
        this.dictionary = dictionary;
    }

    @Override
    public List<Result> analyse(List<Password> passwordList) {
        ArrayList<Result> results = new ArrayList<>();
        for (Password acc : passwordList) {
            List<DictionaryMatch> matches = getAllMatches(acc.getPasswordValue().value);
            if (matches.isEmpty()) {
                results.add(new DictionaryResult(acc, ResultOutcome.PASS, matches));
            } else {
                results.add(new DictionaryResult(acc, ResultOutcome.FAIL, matches));
            }
        }
        return results;
    }

    List<DictionaryMatch> getAllMatches(String password) {
        List<DictionaryMatch> matches = new ArrayList<>();

        // Create all possible sub-sequences of the password
        for (int start = 0; start < password.length(); start++) {
            for (int end = start + 1; end <= password.length(); end++) {
                String splitPassword = password.substring(start, end);

                // Match on lower
                String lowerPart = splitPassword.toLowerCase();
                Integer lowerRank = dictionary.getRank(lowerPart);
                if (lowerRank != null) {
                    matches.add(new DictionaryMatch(start, end - 1, lowerPart, lowerRank));
                    continue;
                }

                //Match on leet
                List<String> unleetList = LeetUtil.generateUnleetList(lowerPart);
                for (final String unleetPart : unleetList) {
                    Integer unleetRank = dictionary.getRank(unleetPart);
                    if (unleetRank != null) {
                        matches.add(new DictionaryMatch(start, end - 1, unleetPart, unleetRank));
                        continue;
                    }
                }
            }
        }
        Collections.sort(matches);
        return matches;
    }

    @Override
    public String getHeader() {
        return MESSAGE_HEADER;
    }
}
