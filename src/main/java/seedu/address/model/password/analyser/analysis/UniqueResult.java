package seedu.address.model.password.analyser.analysis;

import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.UniqueMatch;

/**
 * Represents a result produced from unique analyser.
 */
public class UniqueResult extends BaseResult {
    private List<UniqueMatch> matches;

    public UniqueResult(Password password, String description, List<UniqueMatch> matches) {
        super(password, description);
        this.matches = matches;
    }

    @Override
    public String getGreaterDetail() {
        StringBuilder report = new StringBuilder("Result : " + description + "\n");
        if (matches.isEmpty()) {
            return report.append("No accounts with same passwords were found\n").toString();
        }
        report.append("The following accounts share the same password: \n");
        for (UniqueMatch m : matches) {
            report.append(m);
        }
        return report.toString();
    }
}
