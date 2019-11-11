package seedu.address.model.password.analyser.report;

import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.password.analyser.Analyser;
import seedu.address.model.password.analyser.result.Result;

/**
 * Represents an Analysis Report that compiles the {@code List} of {@code Result} from the each {@code Analyser}.
 */
public class AnalysisReport {
    protected static final String MESSAGE_DIVIDER = "-------------------------------------------\n";
    protected static final String MESSAGE_LOGO =
            "  ____                           ___ _____  \n"
                    + " / ___|  ___  ___ _   _ _ __ ___|_ _|_   _| \n"
                    + " \\___ \\ / _ \\/ __| | | | '__/ _ \\| |  | |   \n"
                    + "  ___) |  __/ (__| |_| | | |  __/| |  | |   \n"
                    + " |____/ \\___|\\___|\\__,_|_|  \\___|___| |_|   \n"
                    + "                                            \n"
                    + "------------ Password analysis ------------\n"
                    + "\n";
    private static final String MESSAGE_UNDERSCORE = "--------------------";
    private static final String COLUMN1 = String.format("%-20s %-5s %-20s %-5s %-20s %-5s %-20s",
            MESSAGE_UNDERSCORE, ":", MESSAGE_UNDERSCORE, ":", MESSAGE_UNDERSCORE, ":", MESSAGE_UNDERSCORE) + "\n";
    private static final String COLUMN2 = String.format("%-20s %-5s %-20s %-5s %-20s %-5s %-20s",
            "Description", ":", "Username", ":", "Password", ":", "Result") + "\n";
    private static final String MESSAGE_COLUMNS = COLUMN1 + COLUMN2 + COLUMN1;

    protected StringBuilder reportBuilder;
    protected HashMap<Analyser, List<Result>> reports;
    /**
     * Constructs an Analysis Report.
     */
    public AnalysisReport() {
        this.reportBuilder = new StringBuilder();
        this.reports = new HashMap<>();
        reportBuilder.append(MESSAGE_LOGO);
    }

    /**
     * Writes summary information about analysis for all passwords.
     */
    public void write(Analyser analyser, List<Result> analysisResults) {
        reports.put(analyser, analysisResults);
        reportBuilder.append(analyser.getHeader());
        reportBuilder.append(MESSAGE_COLUMNS);
        for (Result o : analysisResults) {
            reportBuilder.append(o);
        }
        reportBuilder.append("\n");
    }

    /**
     * Returns a {@code ObservableList} of {@code Result}.
     *
     * @param results the list of {@code Result} produced from {@code Analyser}.
     */
    public ObservableList<Result> getObservableResults(List<Result> results) {
        ObservableList<Result> resultObservableList = FXCollections.observableArrayList();
        resultObservableList.addAll(results);
        return resultObservableList;
    }

    public HashMap<Analyser, List<Result>> getReports() {
        return this.reports;
    }

    @Override
    public String toString() {
        return reportBuilder.toString();
    }

}
