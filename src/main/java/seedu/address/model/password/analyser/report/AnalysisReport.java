package seedu.address.model.password.analyser.report;

import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.result.Result;

/**
 * Represents the Analysis Report formed from the {@code Result} objects from the various {@code Anlayser} Objects
 */
public class AnalysisReport {
    private static final String MESSAGE_DIVIDER = "----------------------------------------\n";
    private static final String MESSAGE_INIT =
            "  ____                           ___ _____  \n"
                    + " / ___|  ___  ___ _   _ _ __ ___|_ _|_   _| \n"
                    + " \\___ \\ / _ \\/ __| | | | '__/ _ \\| |  | |   \n"
                    + "  ___) |  __/ (__| |_| | | |  __/| |  | |   \n"
                    + " |____/ \\___|\\___|\\__,_|_|  \\___|___| |_|   \n"
                    + "                                            \n"
                    + "---- Password analysis ----\n"
                    + "\n";
    private static final String MESSAGE_UNDERSCORE = "--------------------";
    private static final String COLUMN1 = String.format("%-20s %-5s %-20s %-5s %-20s %-5s %-20s",
            MESSAGE_UNDERSCORE, ":", MESSAGE_UNDERSCORE, ":", MESSAGE_UNDERSCORE, ":", MESSAGE_UNDERSCORE) + "\n";
    private static final String COLUMN2 = String.format("%-20s %-5s %-20s %-5s %-20s %-5s %-20s",
            "Description", ":", "Username", ":", "Password", ":", "Result") + "\n";
    private static final String MESSAGE_COLUMNS = COLUMN1 + COLUMN2 + COLUMN1;

    private StringBuilder reportBuilder;
    /**
     * Constructs an Analysis Report.
     */
    public AnalysisReport() {
        this.reportBuilder = new StringBuilder();
        reportBuilder.append(MESSAGE_INIT);
    }

    /**
     * Writes summary information about analysis for all passwords.
     */
    public void write(List<Result> results) {
        reportBuilder.append(MESSAGE_COLUMNS);
        for (Result o : results) {
            reportBuilder.append(o);
        }
        reportBuilder.append("\n");
        //TODO good way to write title
    }
    /**
     * Writes further in-depth information about a specific result.
     */
    public void write(Result result) {
        reportBuilder.append(result.getGreaterDetail());
        reportBuilder.append(MESSAGE_DIVIDER);
    }

    /**
     * Writes header message for the various analysers.
     *
     * @param header
     */
    public void writeHeading(String header) {
        reportBuilder.append(header);
    }

    /**
     * Writes password in String format.
     *
     * @param password
     */
    public void writePassword(Password password) {
        reportBuilder.append(password);
        reportBuilder.append(MESSAGE_DIVIDER);
    }
    @Override
    public String toString() {
        return reportBuilder.toString();
    }

}
