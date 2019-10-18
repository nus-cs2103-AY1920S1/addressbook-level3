package seedu.address.model.password.analyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.analysis.UniqueResult;
import seedu.address.model.password.analyser.match.UniqueMatch;

/**
 * Represents analyser object that analyses passwords in password book for unique passwords.
 */
public class UniqueAnalyser implements Analyser {
    private static final String DESC_NOT_UNIQUE = "not unique";
    private static final String DESC_UNIQUE = "unique";
    private static final String MESSAGE_HEADER = "Analysing passwords to check unique: \n";

    private ArrayList<UniqueResult> analysisObjects;

    @Override
    public void analyse(List<Password> accountList) {
        HashMap<String, ArrayList<Password>> passwordToAccounts = new HashMap<>();
        ArrayList<UniqueResult> analysisObjects = new ArrayList<>();
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

        for (Password acc : accountList) {
            List<UniqueMatch> matches = new ArrayList<>();
            String password = acc.getPasswordValue().value;
            ArrayList<Password> arrList = passwordToAccounts.get(password);
            if (arrList.size() > 1) {
                matches = getAllMatches(acc, arrList);
                analysisObjects.add(new UniqueResult(acc, DESC_NOT_UNIQUE, matches));
            } else {
                analysisObjects.add(new UniqueResult(acc, DESC_UNIQUE, matches));
            }
        }

        this.analysisObjects = analysisObjects;
    }

    private List<UniqueMatch> getAllMatches(Password acc, ArrayList<Password> arrList) {
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
        for (UniqueResult o : analysisObjects) {
            reportBuilder.append(o);
        }
        return reportBuilder.toString();
    }

    @Override
    public String outputDetailedReport(Index index) {
        StringBuilder report = new StringBuilder();
        report.append(MESSAGE_INIT);
        report.append(MESSAGE_HEADER);
        UniqueResult target = analysisObjects.get(index.getZeroBased());
        report.append(target.getGreaterDetail());
        return report.toString();
    }

}
