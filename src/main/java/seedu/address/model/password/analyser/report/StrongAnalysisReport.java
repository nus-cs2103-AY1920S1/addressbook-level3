package seedu.address.model.password.analyser.report;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.result.Result;

/**
 * Represents the detailed Analysis Report
 * formed from the {@code List} of {@code Result} from the each {@code Anlayser}.

 */
public class StrongAnalysisReport extends AnalysisReport {

    private static final String MESSAGE_PASSWORDHEADER = "Analysing password for following account: \n";

    public StrongAnalysisReport() {
        super();
    }

    /**
     * Writes further in-depth information about a specific result.
     */
    public void write(Result result) {
        super.reportBuilder.append(result.getGreaterDetail());
        super.reportBuilder.append(super.MESSAGE_DIVIDER);
    }

    /**
     * Writes password in String format.
     *
     * @param password
     */
    public void writePassword(Password password) {
        super.reportBuilder.append(MESSAGE_PASSWORDHEADER);
        super.reportBuilder.append(password);
        super.reportBuilder.append(MESSAGE_DIVIDER);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
