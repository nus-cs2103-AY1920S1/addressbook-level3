package seedu.address.model.password.analyser.report;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.Analyser;
import seedu.address.model.password.analyser.result.Result;

/**
 * Represents the detailed Analysis Report
 * formed from the {@code List} of {@code Result} from the each {@code Anlayser}.

 */
public class StrongAnalysisReport extends AnalysisReport {

    private static final String MESSAGE_PASSWORD_HEADER = "Analysing password for following account: \n";
    private Password password;

    public StrongAnalysisReport(Password password) {
        super();
        this.password = password;
        writeIntroduciton();
    }

    /**
     * Writes introductory section of for the report.
     */
    public void writeIntroduciton() {
        reportBuilder.append(MESSAGE_PASSWORD_HEADER);
        reportBuilder.append(password);
        reportBuilder.append(MESSAGE_DIVIDER);
    }

    /**
     * Writes further in-depth information about a specific result.
     */
    public void write(Analyser analyser, Result result) {
        super.reportBuilder.append(analyser.getHeader());
        super.reportBuilder.append(result.getGreaterDetail());
        super.reportBuilder.append(super.MESSAGE_DIVIDER);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
