package seedu.address.model.password.analyser.analysis;

import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.SimilarityMatch;

/**
 * Represents a result produced from similarity analyser.
 */
public class SimilarityResult extends BaseResult {

    private List<SimilarityMatch> matches;

    public SimilarityResult(Password password, String description, List<SimilarityMatch> matches) {
        super(password, description);
        this.matches = matches;
    }

    @Override
    public String getGreaterDetail() {
        StringBuilder report = new StringBuilder("Result : " + description + "\n");
        if (matches.isEmpty()) {
            return report.append("No accounts with similar passwords were found\n").toString();
        }
        for (SimilarityMatch m : matches) {
            report.append(m); //TODO implement AccountMatches
        }
        return report.toString();
    }
}
