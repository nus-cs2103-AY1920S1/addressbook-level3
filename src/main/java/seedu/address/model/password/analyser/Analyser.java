package seedu.address.model.password.analyser;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.password.Password;

/**
 * Represents {@code Analyser} object that analyses individual passwords in password book to produce an {@code Result}.
 * Interface for different analyser classes to implement analyse method.
 */
public interface Analyser {
    final String MESSAGE_UNDERSCORE = "------------------------------";
    final String COLUMN1 = String.format("%-30s %-10s %-30s %-10s %-30s %-10s %-30s", MESSAGE_UNDERSCORE, ":",
            MESSAGE_UNDERSCORE, ":", MESSAGE_UNDERSCORE, ":", MESSAGE_UNDERSCORE) + "\n";
    final String COLUMN2 = String.format("%-30s %-10s %-30s %-10s %-30s %-10s %-30s", "Description", ":",
            "Username", ":", "Password", ":", "Result") + "\n";
    final String MESSAGE_COLUMNS = COLUMN1 + COLUMN2 + COLUMN1;
    final String MESSAGE_INIT = "----------------------------------------\n";
    final String DESC_FAIL = "failed";
    final String DESC_PASS = "passed";

    /**
     * Reviews specific aspect of every password in the password book to produce a list of {@code Result} objects
     * containing information of the analysis.
     *
     * @param passwordList the list of the {@code Password} objects in the password book.
     */
    void analyse(List<Password> passwordList);

    /**
     * Provides summary information about analysis for all passwords.
     *
     * @return the summary information of all passwords in string format.
     */
    String outputSummaryReport();

    /**
     * Provides further in-depth information about a specific result.
     *
     * @return the specific details of a specific result in string format.
     */
    String outputDetailedReport(Index index);


}
