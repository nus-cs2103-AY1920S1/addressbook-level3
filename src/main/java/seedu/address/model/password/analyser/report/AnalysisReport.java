package seedu.address.model.password.analyser.report;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.password.analyser.result.Result;

/**
 * Represents the Analysis Report formed from the {@code Result} objects from the various {@code Anlayser} Objects
 */
public class AnalysisReport {
    protected static final String MESSAGE_DIVIDER = "----------------------------------------\n";
    protected static final String MESSAGE_INIT =
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

    protected StringBuilder reportBuilder;
    protected List<List<Result>> results;
    protected List<String> resultsHeader;
    /**
     * Constructs an Analysis Report.
     */
    public AnalysisReport() {
        this.reportBuilder = new StringBuilder();
        this.results = new ArrayList<>();
        this.resultsHeader = new ArrayList<>();
        reportBuilder.append(MESSAGE_INIT);
    }

    /**
     * Writes summary information about analysis for all passwords.
     */
    public void write(List<Result> results) {
        this.results.add(results);
        reportBuilder.append(MESSAGE_COLUMNS);
        for (Result o : results) {
            reportBuilder.append(o);
        }
        reportBuilder.append("\n");
        //TODO good way to write title
    }

    /**
     * Writes header message for the various analysers.
     *
     * @param header
     */
    public void writeHeading(String header) {
        this.resultsHeader.add(header);
        reportBuilder.append(header);
    }

    /**
     * Returns the results list of {@code Result} specified by index.
     *
     * @param index the index to in list to retrieve from.
     * @return the specified type of Result.
     */
    public ObservableList<Result> getTargetResults(int index) {
        List<Result> targetList = this.results.get(index);
        ObservableList<Result> resultObservableList = FXCollections.observableArrayList();
        resultObservableList.addAll(targetList);
        return resultObservableList;
    }

    /**
     * Returns the header for the type of {@code Result} specified by index.
     *
     * @param index the index to in list to retrieve from.
     * @return the specified header of the type of Result.
     */
    public String getTargetHeader(int index) {
        return this.resultsHeader.get(index);
    }

    public List<List<Result>> getResults() {
        return this.results;
    }

    @Override
    public String toString() {
        return reportBuilder.toString();
    }

}
