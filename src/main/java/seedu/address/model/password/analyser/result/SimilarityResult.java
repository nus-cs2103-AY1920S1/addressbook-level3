package seedu.address.model.password.analyser.result;

import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.SimilarityMatch;

/**
 * Represents a result produced from similarity analyser.
 */
public class SimilarityResult extends Result {

    private static final String MESSAGE_NO_SIMILAR_ACC_FOUND = "No accounts with similar passwords were found\n";
    private List<SimilarityMatch> matches;

    public SimilarityResult(Password password, String description, List<SimilarityMatch> matches) {
        super(password, description);
        this.matches = matches;
    }

    @Override
    public String getGreaterDetail() {
        StringBuilder report = new StringBuilder("Result : " + description + "\n");
        if (matches.isEmpty()) {
            return report.append(MESSAGE_NO_SIMILAR_ACC_FOUND).toString();
        }
        report.append("The following accounts share similar password: \n");
        for (SimilarityMatch m : matches) {
            report.append(m);
        }
        return report.toString();
    }
}
