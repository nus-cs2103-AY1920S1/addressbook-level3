package seedu.address.model.password.analyser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.Dictionary;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.LeetUtil;
import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.analysis.DictionaryResult;
import seedu.address.model.password.analyser.match.DictionaryMatch;

/**
 * Represents analyser object that analyses passwords in password book for common dictionary string.
 */
public class DictionaryAnalyser implements Analyser {

    private static final String MESSAGE_HEADER = "Analysing passwords for commonly used passwords:\n";
    private Dictionary dictionary;
    private ArrayList<DictionaryResult> analysisObjects;

    public DictionaryAnalyser(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public void analyse(List<Password> passwordList) {
        ArrayList<DictionaryResult> analysisObjects = new ArrayList<>();
        for (Password acc : passwordList) {
            List<DictionaryMatch> matches = getAllMatches(acc.getPasswordValue().value);
            if (matches.isEmpty()) {
                analysisObjects.add(new DictionaryResult(acc, DESC_PASS, matches));
            } else {
                analysisObjects.add(new DictionaryResult(acc, DESC_FAIL, matches));
            }
        }
        this.analysisObjects = analysisObjects;
    }

    private List<DictionaryMatch> getAllMatches(String password) {
        List<DictionaryMatch> matches = new ArrayList<>();

        // Create all possible sub-sequences of the password
        for (int start = 0; start < password.length(); start++) {
            for (int end = start + 1; end <= password.length(); end++) {
                String splitPassword = password.substring(start, end);

                // Match on lower
                String lowerPart = splitPassword.toLowerCase();
                Integer lowerRank = dictionary.getDictionary().get(lowerPart);
                if (lowerRank != null) {
                    matches.add(new DictionaryMatch(start, end - 1, lowerPart, lowerRank));
                    continue;
                }

                //Match on leet
                List<String> unleetList = LeetUtil.translateLeet(lowerPart);
                for (final String unleetPart : unleetList) {
                    Integer unleetRank = dictionary.getDictionary().get(unleetPart);
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
    public String outputSummaryReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(MESSAGE_HEADER);
        reportBuilder.append(MESSAGE_COLUMNS);
        for (DictionaryResult o : analysisObjects) {
            reportBuilder.append(o);
        }
        return reportBuilder.toString();
    }

    @Override
    public String outputDetailedReport(Index index) {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(MESSAGE_INIT);
        reportBuilder.append(MESSAGE_HEADER);
        DictionaryResult target = analysisObjects.get(index.getZeroBased());
        reportBuilder.append(target.getGreaterDetail());
        return reportBuilder.toString();
    }
}
