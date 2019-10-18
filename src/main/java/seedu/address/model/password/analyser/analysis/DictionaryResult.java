package seedu.address.model.password.analyser.analysis;

import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.DictionaryMatch;
import seedu.address.model.password.analyser.match.Match;

/**
 * Represents a result produced from dictionary analyser.
 */
public class DictionaryResult extends BaseResult {
    private List<DictionaryMatch> matches;


    public DictionaryResult(Password password, String description, List<DictionaryMatch> matches) {
        super(password, description);
        this.matches = matches;
    }

    @Override
    public String getGreaterDetail() {
        StringBuilder report = new StringBuilder("Result : " + description + "\n");
        if (matches.isEmpty()) {
            report.append("No passwords were found to have contained common passwords");
            return report.toString();
        }
        for (Match m : matches) {
            report.append(m);
        }
        return report.toString();
    }
}
