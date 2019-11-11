package seedu.address.model.password.analyser.result;

import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.Match;
import seedu.address.model.password.analyser.match.SequenceMatch;

/**
 * Represents a {@code Result} produced from {@code SequenceAnalyser}.
 */
public class SequenceResult extends Result {
    private static final String MESSAGE_NO_COMMON_SEQ_FOUND =
            "No passwords were found to have contained common sequences\n";
    private static final String MESSAGE_EXPLANATION = "The following tokens in your password were "
            + "found to be common sequences:\n";
    private List<SequenceMatch> matches;

    public SequenceResult(Password password, ResultOutcome description, List<SequenceMatch> matches) {
        super(password, description);
        this.matches = matches;
    }

    @Override
    public String getGreaterDetail() {
        StringBuilder report = new StringBuilder("Result : " + description + "\n");
        if (matches.isEmpty()) {
            report.append(MESSAGE_NO_COMMON_SEQ_FOUND);
            return report.toString();
        }
        report.append(MESSAGE_EXPLANATION);
        for (Match m : matches) {
            report.append(m);
        }
        return report.toString();
    }
}
