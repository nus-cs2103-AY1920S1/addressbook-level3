package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Handles the sending of emails to interviewers/interviewees based on the email addresses associated with
 * the object.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Handles the sending of emails to "
            + "interviewers/interviewees using their email addresses.\n\n"
            + COMMAND_WORD + " timeslots\n"
            + "Sends an email containing the interviewee's allocated interview time slot to every "
            + "interviewee, including other details such as the interviewer, time, date and location.";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Email command not implemented yet";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
