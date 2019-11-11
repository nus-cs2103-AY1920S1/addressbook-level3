package seedu.address.reimbursement.logic.commands;

import static seedu.address.reimbursement.ui.ReimbursementMessages.MESSAGE_DONE_REIMBURSEMENT;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.util.CommandResult;


/**
 * Represents a command to mark as done
 */
public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Person person;

    public DoneCommand(Person person) {
        this.person = person;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel)
            throws NoSuchPersonReimbursementException {
        Reimbursement rmb = model.doneReimbursement(person);
        logger.info(rmb.toString());
        return new CommandResult(String.format(MESSAGE_DONE_REIMBURSEMENT, rmb.toString()));
    }
}

