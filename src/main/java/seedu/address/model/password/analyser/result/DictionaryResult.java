package seedu.address.model.password.analyser.result;

import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.DictionaryMatch;
import seedu.address.model.password.analyser.match.Match;

/**
 * Represents a {@code Result} produced from {@code DictionaryAnalyser}.
 */
public class DictionaryResult extends Result {
    private static final String MESSAGE_NO_COMMON_PASSWORD_FOUND =
            "No passwords were found to have contained commonly used passwords\n";
    private static final String MESSAGE_EXPLANATION = "The following tokens in your password were found inside our"
            + " list of commonly used passwords:\n(Tokens are sorted by their rank in our list)\n";
    private List<DictionaryMatch> matches;


    public DictionaryResult(Password password, ResultOutcome description, List<DictionaryMatch> matches) {
        super(password, description);
        this.matches = matches;
    }

    @Override
    public String getGreaterDetail() {
        StringBuilder report = new StringBuilder("Result : " + description + "\n");
        if (matches.isEmpty()) {
            report.append(MESSAGE_NO_COMMON_PASSWORD_FOUND);
            return report.toString();
        }
        report.append(MESSAGE_EXPLANATION);
        for (Match m : matches) {
            report.append(m);
        }
        return report.toString();
    }
}
