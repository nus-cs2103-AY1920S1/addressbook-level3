package seedu.address.model.password.analyser.result;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.KeyboardMatch;
import seedu.address.model.password.analyser.match.Match;

/**
 * Represents a {@code Result} produced from {@code KeyboardAnalyser}.
 */
public class KeyboardResult extends Result {

    private static final String MESSAGE_NO_COMMON_KEYBOARD_PATTERN_FOUND =
            "No passwords were found to have contained common keyboard patterns\n";
    private static final String MESSAGE_EXPLANATION = "The following tokens in your password were "
            + "found to be common keyboard patterns:\n(Tokens are sorted by the keyboard pattern complexity)\n";
    private List<KeyboardMatch> matches;

    public KeyboardResult(Password password, ResultOutcome description, List<KeyboardMatch> matches) {
        super(password, description);
        this.matches = matches;
    }

    @Override
    public String getGreaterDetail() {
        StringBuilder report = new StringBuilder("Result : " + description + "\n");
        if (matches.isEmpty()) {
            report.append(MESSAGE_NO_COMMON_KEYBOARD_PATTERN_FOUND);
            return report.toString();
        }
        report.append(MESSAGE_EXPLANATION);
        for (Match m : matches) {
            report.append(m);
        }
        return report.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyboardResult that = (KeyboardResult) o;
        return password.equals(that.password)
                && description.equals(that.description)
                && Arrays.equals(matches.toArray(), that.matches.toArray());
    }
}
