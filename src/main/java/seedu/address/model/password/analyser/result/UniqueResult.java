package seedu.address.model.password.analyser.result;

import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.UniqueMatch;

/**
 * Represents a {@code Result} produced from an {@code UniqueAnalyser}.
 */
public class UniqueResult extends Result {
    private static final String MESSAGE_UNIQUE_PASSWORD =
            "No accounts were found to have the same password as this account\n";
    private static final String MESSAGE_EXPLANATION = "The following accounts share the same password:\n";
    private List<UniqueMatch> matches;

    public UniqueResult(Password password, ResultOutcome description, List<UniqueMatch> matches) {
        super(password, description);
        this.matches = matches;
    }

    @Override
    public String getGreaterDetail() {
        StringBuilder report = new StringBuilder("Result : " + description + "\n");
        if (matches.isEmpty()) {
            return report.append(MESSAGE_UNIQUE_PASSWORD).toString();
        }
        report.append(MESSAGE_EXPLANATION);
        for (UniqueMatch m : matches) {
            report.append(m);
        }
        return report.toString();
    }
}
