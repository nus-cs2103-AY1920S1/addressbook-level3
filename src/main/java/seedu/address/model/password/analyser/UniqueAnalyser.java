package seedu.address.model.password.analyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.match.UniqueMatch;
import seedu.address.model.password.analyser.result.UniqueResult;

/**
 * Represents analyser object that analyses passwords in password book for unique passwords.
 */
public class UniqueAnalyser implements Analyser {
    private static final String DESC_NOT_UNIQUE = "not unique";
    private static final String DESC_UNIQUE = "unique";
    private static final String MESSAGE_HEADER = "Analysing passwords to check unique: \n";

    private ArrayList<UniqueResult> results;

    @Override
    public void analyse(List<Password> accountList) {
        ArrayList<UniqueResult> results = new ArrayList<>();
        HashMap<String, ArrayList<Password>> passwordToAccounts = initHash(accountList);
        for (Password acc : accountList) {
            List<UniqueMatch> matches = new ArrayList<>();
            String password = acc.getPasswordValue().value;
            ArrayList<Password> arrList = passwordToAccounts.get(password);
            if (arrList.size() > 1) {
                matches = getAllMatches(acc, arrList);
                results.add(new UniqueResult(acc, DESC_NOT_UNIQUE, matches));
            } else {
                results.add(new UniqueResult(acc, DESC_UNIQUE, matches));
            }
        }

        this.results = results;
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
            matches.add(new UniqueMatch(0, acc.getPasswordValue().value.length(),
                    acc.getPasswordValue().value, acc));
        }
        return matches;
    }

    @Override
    public String outputSummaryReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(MESSAGE_HEADER);
        reportBuilder.append(MESSAGE_COLUMNS);
        for (UniqueResult o : results) {
            reportBuilder.append(o);
        }
        return reportBuilder.toString();
    }

    @Override
    public String outputDetailedReport(Index index) {
        StringBuilder report = new StringBuilder();
        report.append(MESSAGE_INIT);
        report.append(MESSAGE_HEADER);
        UniqueResult target = results.get(index.getZeroBased());
        report.append(target.getGreaterDetail());
        return report.toString();
    }

}
