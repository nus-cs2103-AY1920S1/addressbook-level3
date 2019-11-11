package seedu.address.model.password.analyser.result;

import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.SimilarityMatch;

/**
 * Represents a {@code Result} produced from an {@code SimilarityAnalyser}.
 */
public class SimilarityResult extends Result {

    private static final String MESSAGE_NO_SIMILAR_ACC_FOUND = "No accounts with similar passwords were found\n";
    private static final String MESSAGE_EXPLANATION = "The following accounts share similar password:\n"
            + "(Accounts are sorted by their similarity)\n";
    private List<SimilarityMatch> matches;

    public SimilarityResult(Password password, ResultOutcome description, List<SimilarityMatch> matches) {
        super(password, description);
        this.matches = matches;
    }

    @Override
    public String getGreaterDetail() {
        StringBuilder report = new StringBuilder("Result : " + description + "\n");
        if (matches.isEmpty()) {
            return report.append(MESSAGE_NO_SIMILAR_ACC_FOUND).toString();
        }
        report.append(MESSAGE_EXPLANATION);
        for (SimilarityMatch m : matches) {
            report.append(m);
        }
        return report.toString();
    }
}
