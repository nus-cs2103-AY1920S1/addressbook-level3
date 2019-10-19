package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

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
    public static final String MESSAGE_EMAIL_INTERVIEWEE_SUCCESS = "Emailed interviewee: %1$s";

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
        // TODO: Need to filter for Interviewees only
        List<Person> lastShownList = model.getFilteredPersonList();
        // Interviewee intervieweeToEmail = new Interviewee.IntervieweeBuilder(lastShownList.get(1)).build();
        Interviewee intervieweeToEmail = null;

        for (Person person : lastShownList) {
            if (person instanceof Interviewee && person.getName().equals(this.intervieweeName)) {
                intervieweeToEmail = (Interviewee) person;
                break;
            }
        }

        if (intervieweeToEmail == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NAME);
        }

        // TODO: Implement email feature
        try {
            model.emailInterviewee(intervieweeToEmail);
        } catch (IOException ioe) {
            // Happens when the mail client cannot be launched, should prompt the user to open a URL instead
            throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
        }
        return new CommandResult(String.format(MESSAGE_EMAIL_INTERVIEWEE_SUCCESS, intervieweeToEmail));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && intervieweeName.equals(((EmailCommand) other).intervieweeName)); // state check
    }
}
