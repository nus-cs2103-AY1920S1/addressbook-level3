package seedu.address.reimbursement.commands;

import static seedu.address.reimbursement.model.Reimbursement.DATE_TIME_FORMATTER;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.reimbursement.model.exception.InvalidDeadlineException;
import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 * Represents a command to add a deadline.
 */
public class DeadlineCommand extends Command {
    public static final String COMMAND_WORD = "deadline";

    private Person person;
    private String date;
    private final Logger logger = LogsCenter.getLogger(getClass());

    public DeadlineCommand(Person person, String deadline) {
        this.person = person;
        this.date = deadline;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception {
        ReimbursementMessages reimbursementMessages = new ReimbursementMessages();
        try{
            LocalDate deadline = LocalDate.parse(date, DATE_TIME_FORMATTER);
            Reimbursement rmb = model.addDeadline(person, deadline);
            logger.info(rmb.toString());
            return new CommandResult(reimbursementMessages.addDeadline(rmb));

        } catch(DateTimeParseException e) {
            throw new InvalidDeadlineException();
        }
    }
}
