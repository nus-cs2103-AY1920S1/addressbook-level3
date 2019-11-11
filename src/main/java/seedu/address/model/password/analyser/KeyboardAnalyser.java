package seedu.address.model.password.analyser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.KeyboardMatch;
import seedu.address.model.password.analyser.resources.AdjacencyGraph;
import seedu.address.model.password.analyser.result.KeyboardResult;
import seedu.address.model.password.analyser.result.Result;
import seedu.address.model.password.analyser.result.ResultOutcome;

/**
 * Represents an {@code Analyser} that analyses passwords in password book for common keyboard pattern usage.
 */
public class KeyboardAnalyser implements Analyser {

    private static final String MESSAGE_HEADER = "Analysing passwords for any common keyboard patterns: \n";

    @Override
    public List<Result> analyse(List<Password> passwordList) {
        ArrayList<Result> results = new ArrayList<>();
        for (Password acc : passwordList) {
            String password = acc.getPasswordValue().value;
            List<KeyboardMatch> matches = getAllMatches(password);
            if (matches.isEmpty()) {
                results.add(new KeyboardResult(acc, ResultOutcome.PASS, matches));
            } else {
                results.add(new KeyboardResult(acc, ResultOutcome.FAIL, matches));
            }
        }
        return results;
    }

    List<KeyboardMatch> getAllMatches(String password) {
        char[] characters = password.toCharArray();
        ArrayList<KeyboardMatch> matches = new ArrayList<>();
        int start = 0;
        while (start < characters.length - 1) { //while start not at the end
            StringBuilder seq = new StringBuilder();
            Character curr = characters[start];
            seq.append(curr);
            int end = start + 1;
            Character next = characters[end];
            while (end <= characters.length - 1 && isNeighbour(curr, next)) {
                seq.append(next);
                end++;
                if (end == characters.length) {
                    break;
                }
                curr = next;
                next = characters[end];
            }
            if (seq.length() >= 3) {
                matches.add(new KeyboardMatch(start, end - 1, seq.toString(),
                        AdjacencyGraph.getDirections(seq.toString())));
            }
            start = end;
        }
        Collections.sort(matches);
        return matches;
    }

    /**
     * Returns true if curr character and next character are neighbours.
     * @param curr the current character.
     * @param next the neighbouring character.
     */
    private boolean isNeighbour(Character curr, Character next) {
        List<Character> nextNeighbours = AdjacencyGraph.getNeighbors(next);
        for (Character nextNeighbor : nextNeighbours) {
            if (nextNeighbor.equals(curr)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String getHeader() {
        return MESSAGE_HEADER;
    }
}
