package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_PERSON_NAME;

import java.io.IOException;
import java.util.List;

import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Name;

/**
 * Handles the sending of emails to interviewers/interviewees based on the email addresses associated with
 * the object.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String TIMESLOT_COMMAND_WORD = "timeslot";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Handles the sending of emails to "
            + "interviewers/interviewees using their email addresses.\n\n"
            + COMMAND_WORD + " " + TIMESLOT_COMMAND_WORD + " <interviewee-name> [email-address-type]\n"
            + "Sends an email containing the interviewee's allocated interview time slot to the specified "
            + "interviewee, including other details such as the interviewer, time, date and location.";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Email command not implemented yet";
    public static final String MESSAGE_NO_SLOTS_ALLOCATED = "No slots are currently allocated to this "
        + "interviewee, have you run the schedule command?";
    public static final String MESSAGE_EMAIL_INTERVIEWEE_SUCCESS = "Opening email window to email interviewee %1$s";
    public static final String EMAIL_MESSAGE_BODY = "Dear %1$s,\n\n"
            + "Thank you for applying for %2$s! Below are the details of the interview slot allocated to you:\n\n"
            + "Interview slots: %3$s\n"
            + "Location: %4$s\n\n"
            + "%5$s";

    private final Name intervieweeName;

    /**
     * Constructor for the EmailCommand class where no email type is specified.
     * @param intervieweeName The name of the {@code Interviewee}.
     */
    public EmailCommand(Name intervieweeName) {
        this.intervieweeName = intervieweeName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Interviewee> intervieweeList = model.getUnfilteredIntervieweeList();
        Interviewee intervieweeToEmail = null;

        for (Interviewee interviewee : intervieweeList) {
            if (interviewee.getName().equals(this.intervieweeName)) {
                intervieweeToEmail = interviewee;
                break;
            }
        }

        if (intervieweeToEmail == null) {
            throw new CommandException(MESSAGE_INVALID_PERSON_NAME);
        }

        if (model.getInterviewSlots(intervieweeToEmail.getName().toString()).size() == 0) {
            throw new CommandException(MESSAGE_NO_SLOTS_ALLOCATED);
        }

        try {
            model.emailInterviewee(intervieweeToEmail);
        } catch (IOException ioe) {
            // Happens when the mail client cannot be launched, should prompt the user to open a URL instead
            throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
        }
        return new CommandResult(String.format(MESSAGE_EMAIL_INTERVIEWEE_SUCCESS, intervieweeToEmail.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && intervieweeName.equals(((EmailCommand) other).intervieweeName)); // state check
    }
}
