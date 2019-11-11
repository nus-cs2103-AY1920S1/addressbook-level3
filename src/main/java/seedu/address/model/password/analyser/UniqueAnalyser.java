package seedu.address.model.password.analyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.UniqueMatch;
import seedu.address.model.password.analyser.result.Result;
import seedu.address.model.password.analyser.result.ResultOutcome;
import seedu.address.model.password.analyser.result.UniqueResult;

/**
 * Represents an {@code Analyser} that analyses passwords in password book for unique passwords.
 */
public class UniqueAnalyser implements Analyser {
    private static final String MESSAGE_HEADER = "Analysing passwords to check unique: \n";


    @Override
    public List<Result> analyse(List<Password> accountList) {
        ArrayList<Result> results = new ArrayList<>();
        HashMap<String, ArrayList<Password>> passwordToAccounts = initHash(accountList);
        for (Password acc : accountList) {
            List<UniqueMatch> matches = new ArrayList<>();
            String password = acc.getPasswordValue().value;
            ArrayList<Password> arrList = passwordToAccounts.get(password);
            if (arrList.size() > 1) {
                matches = getAllMatches(acc, arrList);
                results.add(new UniqueResult(acc, ResultOutcome.FAIL, matches));
            } else {
                results.add(new UniqueResult(acc, ResultOutcome.PASS, matches));
            }
        }
        return results;
    }

    /**
     * Creates a hashmap of password String to the Password objects.
     *
     * @param accountList is the list of passwords in the password book.
     * @return the hashmap of unique passwords to password objects.
     */
    HashMap<String, ArrayList<Password>> initHash(List<Password> accountList) {
        HashMap<String, ArrayList<Password>> passwordToAccounts = new HashMap<>();
        for (Password acc : accountList) {
            String password = acc.getPasswordValue().value;
            if (passwordToAccounts.containsKey(password)) {
                ArrayList<Password> arrList = passwordToAccounts.get(password);
                arrList.add(acc);
            } else {
                passwordToAccounts.put(password, new ArrayList<>());
                passwordToAccounts.get(password).add(acc);
            }
        }
        return passwordToAccounts;
    }

    List<UniqueMatch> getAllMatches(Password acc, ArrayList<Password> arrList) {
        ArrayList<UniqueMatch> matches = new ArrayList<>();
        for (Password p : arrList) {
            if (p == acc) {
                continue;
            }
            matches.add(new UniqueMatch(0, acc.getPasswordValue().value.length() - 1,
                    acc.getPasswordValue().value, p));
        }
        return matches;
    }

    @Override
    public String getHeader() {
        return MESSAGE_HEADER;
    }

}
