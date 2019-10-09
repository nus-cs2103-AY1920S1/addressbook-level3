package seedu.address.reimbursement.commands;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 * Represents a command to add a deadline.
 */
public class DeadlineCommand extends Command {
    public static final String COMMAND_WORD = "deadline";

    private Person person;
    private String deadline;
    private final Logger logger = LogsCenter.getLogger(getClass());

    public DeadlineCommand(Person person, String deadline) {
        this.person = person;
        this.deadline = deadline;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception {
        ReimbursementMessages reimbursementMessages = new ReimbursementMessages();
        Reimbursement rmb = model.addDeadline(person, deadline);
        logger.info(rmb.toString());
        return new CommandResult(reimbursementMessages.addDeadline(rmb));
    }
}
