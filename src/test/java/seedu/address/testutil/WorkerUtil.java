package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_WORKER_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.entity.worker.Worker;

//@@author shaoyi1997
/**
 * A utility class for the Worker class to be used in tests.
 */
public class WorkerUtil {

    /**
     * Returns an add command string for adding the {@code worker}.
     */
    public static String getAddCommand(Worker worker) {
        return AddCommand.COMMAND_WORD + VALID_WORKER_FLAG + " " + getWorkerDetails(worker);
    }

    /**
     * Returns the part of command string for the given {@code worker}'s details.
     */
    public static String getWorkerDetails(Worker worker) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + " " + worker.getName().toString() + " ");
        sb.append(PREFIX_SEX + " " + worker.getSex().toString() + " ");
        sb.append(PREFIX_DATE_JOINED + " " + convertDateToStringPattern(worker.getDateJoined()) + " ");
        if (worker.getDateOfBirth().isPresent()) {
            sb.append(PREFIX_DATE_OF_BIRTH + " " + convertDateToStringPattern(worker.getDateOfBirth().get()) + " ");
        }
        if (worker.getDesignation().isPresent()) {
            sb.append(PREFIX_DESIGNATION + " " + worker.getDesignation().get() + " ");
        }
        if (worker.getPhone().isPresent()) {
            sb.append(PREFIX_PHONE_NUMBER + " " + worker.getPhone().get().toString() + " ");
        }
        if (worker.getEmploymentStatus().isPresent()) {
            sb.append(PREFIX_EMPLOYMENT_STATUS + " " + worker.getEmploymentStatus().get() + " ");
        }
        return sb.toString();
    }

    private static String convertDateToStringPattern(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
//@@author
