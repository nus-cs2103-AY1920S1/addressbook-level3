package seedu.address.model.password.analyser;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.password.Password;

/**
 * Represents analyser object that analyses passwords in password book.
 */
public interface Analyser {
    final String MESSAGE_COLUMNS = "------------------------------     :    ------------------------------     :   "
            + " ------------------------------     :    ------------------------------\n"
             + "Description                         :    Username                            "
            + ":    Password                            :    Result\n"
            + "------------------------------     :    ------------------------------     :    "
            + "------------------------------     :    ------------------------------\n";
    final String MESSAGE_INIT = "----------------------------------------\n";
    final String DESC_FAIL = "failed";
    final String DESC_PASS = "passed";
    /**
     * Creates a {@code List} of {@code Analysis} object from the password.
     *
     * @param passwordList .
     */
    void analyse(List<Password> passwordList);

    /**
     * returns the output of the analyser in summary report format.
     * @return
     */
    String outputSummaryReport();

    /**
     * returns the output of the analyser in deatiled report format.
     * @return
     */
    String outputDetailedReport(Index index);


}
