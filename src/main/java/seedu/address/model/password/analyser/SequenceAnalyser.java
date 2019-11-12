package seedu.address.model.password.analyser;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.SequenceMatch;
import seedu.address.model.password.analyser.result.Result;
import seedu.address.model.password.analyser.result.ResultOutcome;
import seedu.address.model.password.analyser.result.SequenceResult;

/**
 * Represents an {@code Analyser} that analyses passwords in password book for common sequence string.
 */
public class SequenceAnalyser implements Analyser {

    private static final String MESSAGE_HEADER = "Analysing passwords for common sequences :\n";

    private ArrayList<SequenceResult> results;

    public ArrayList<SequenceResult> getResults() {
        return results;
    }


    @Override
    public List<Result> analyse(List<Password> passwordList) {
        ArrayList<Result> results = new ArrayList<>();
        for (Password acc : passwordList) {
            String password = acc.getPasswordValue().value;
            List<SequenceMatch> matches = getAllMatches(password);
            if (matches.isEmpty()) {
                results.add(new SequenceResult(acc, ResultOutcome.PASS, matches));
            } else {
                results.add(new SequenceResult(acc, ResultOutcome.FAIL, matches));
            }
        }
        return results;
    }

    @Override
    public String getHeader() {
        return MESSAGE_HEADER;
    }

    List<SequenceMatch> getAllMatches(String password) {
        ArrayList<SequenceMatch> matches = new ArrayList<>();
        getAllForwardSubseq(password, matches);
        getAllBackwardSubseq(password, matches);
        return matches;
    }

    private static void getAllForwardSubseq(String password, ArrayList<SequenceMatch> matches) {
        char[] characters = password.toCharArray();
        if (password.length() <= 2) {
            return; //dont bother
        }
        int start = 0;
        while (start < characters.length - 1) { //while start not at the end
            StringBuilder seq = new StringBuilder();
            Character curr = characters[start];
            seq.append(curr);
            int end = start + 1;
            Character next = characters[end];
            while (next == curr + 1 && end <= characters.length - 1 && inSameRange(curr, next)) {
                seq.append(next);
                end++;
                if (end == characters.length) {
                    break;
                }
                curr = next;
                next = characters[end];
            }
            if (seq.length() >= 3) {
                matches.add(new SequenceMatch(start, end - 1, seq.toString()));
            }
            start = end;
        }
        return;
    }

    private static void getAllBackwardSubseq(String password, ArrayList<SequenceMatch> matches) {
        char[] characters = password.toCharArray();
        if (password.length() <= 2) {
            return; //dont bother
        }
        int start = 0;
        while (start < characters.length - 1) { //while start not at the end
            StringBuilder seq = new StringBuilder();
            Character curr = characters[start];
            seq.append(curr);
            int end = start + 1;
            Character next = characters[end];
            while (next == curr - 1 && end <= characters.length - 1 && inSameRange(curr, next)) {
                seq.append(next);
                end++;
                if (end == characters.length) {
                    break;
                }
                curr = next;
                next = characters[end];
            }
            if (seq.length() >= 3) {
                matches.add(new SequenceMatch(start, end - 1, seq.toString()));
            }
            start = end;
        }
        return;
    }

    /**
     * Returns true if both current and next character are within the same ASCII range type.
     * @param curr the current character
     * @param next the next character
     */
    private static boolean inSameRange(Character curr, Character next) {
        if (curr >= 65 && curr <= 90) { //ALPHA_UPPER
            return (next >= 65 && next <= 90);
        } else if (curr >= 97 && curr <= 122) { //ALPHA_LOWER:
            return (next >= 97 && next <= 122);
        } else if (curr >= 48 && curr <= 57) { //NUMERICAL:
            return (next >= 48 && next <= 57);
        } else {
            return false;
        }
    }

}
