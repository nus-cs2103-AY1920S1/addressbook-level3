package seedu.address.reimbursement.logic.commands;

import static seedu.address.reimbursement.ui.ReimbursementMessages.MESSAGE_FIND_REIMBURSEMENT;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.reimbursement.ui.ReimbursementMessages;
import seedu.address.util.CommandResult;


/**
 * Represents a command to find a reimbursement.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Person person;

    public FindCommand(Person person) {
        this.person = person;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel)
            throws NoSuchPersonReimbursementException {
        ReimbursementMessages reimbursementMessages = new ReimbursementMessages();
        Reimbursement rmb = model.findReimbursement(person);
        logger.info(rmb.toString());
        return new CommandResult(String.format(MESSAGE_FIND_REIMBURSEMENT,
                rmb.getPerson().getName().toString(), rmb.toString()));
    }
}
