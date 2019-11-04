package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_PERSON_NAME;

import java.io.IOException;
import java.util.List;

import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Name;

/**
 * Handles the sending of emails to interviewees using their associated email addresses.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String TIMESLOT_COMMAND_WORD = "timeslot";
    public static final String ALL_TIMESLOTS_COMMAND_WORD = "alltimeslot";
    public static final String STATUS_COMMAND_WORD = "status";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": All commands related to emails.\n\n"
            + COMMAND_WORD + " ct/" + TIMESLOT_COMMAND_WORD + ": Opens an email dialog containing the "
            + "interviewee's allocated interview time slot and other details pre-populated.\n"
            + "Parameters: " + COMMAND_WORD + " ct/" + TIMESLOT_COMMAND_WORD + " n/INTERVIEWEE NAME\n"
            + "Example: " + COMMAND_WORD + " ct/" + TIMESLOT_COMMAND_WORD + " n/John Doe\n\n"
            + COMMAND_WORD + " ct/" + ALL_TIMESLOTS_COMMAND_WORD + ": Automatically opens the email dialog "
            + "for sending the interviewee's allocated interview time slot and other details pre-populated, "
            + "for all interviewees in the database.\n"
            + "Example: " + COMMAND_WORD + " ct/" + ALL_TIMESLOTS_COMMAND_WORD + "\n\n"
            + COMMAND_WORD + " ct/" + STATUS_COMMAND_WORD + ": Get the current status of interviewees with "
            + "emails sent.\n"
            + "Example: " + COMMAND_WORD + " ct/" + STATUS_COMMAND_WORD;

    public static final String MESSAGE_EMAIL_CLIENT_ERROR = "Unable to open your computer's email client. "
            + "Please manually email the interviewee instead.";
    public static final String MESSAGE_NO_SLOTS_ALLOCATED = "No slots are currently allocated to this "
            + "interviewee, have you run the schedule command?";
    public static final String MESSAGE_EMAIL_INTERVIEWEE_SUCCESS = "Opening email window to email interviewee %1$s";
    public static final String MESSAGE_EMAIL_ALL_SUCCESS = "Successfully emailed %1$s interviewee(s). %2$s "
            + "were skipped as they were emailed before, %3$s had no emails stored in the database and %4$s "
            + "had no slots allocated to them. There are a total of %5$s interviewee(s).";
    public static final String MESSAGE_STATUS = "According to records, emails were sent to %1$s interviewee(s) "
            + "out of a total of %2$s interviewee(s).";

    public static final String EMAIL_MESSAGE_BODY = "Dear %1$s,\n\n"
            + "Thank you for applying for %2$s! Below are the details of the interview slot allocated to you:\n\n"
            + "Interview slots: %3$s\n"
            + "Location: %4$s\n\n"
            + "%5$s";

    private final String commandType;
    private final Name intervieweeName;

    /**
     * Constructor for the EmailCommand class with no other variables provided, except for the commandType.
     * @param commandType The email subcommand to execute.
     */
    public EmailCommand(String commandType) {
        this.commandType = commandType;
        this.intervieweeName = null;
    }

    /**
     * Constructor for the EmailCommand class with an intervieweeName specified.
     * @param commandType The email subcommand to execute.
     * @param intervieweeName The name of the {@code Interviewee}.
     */
    public EmailCommand(String commandType, Name intervieweeName) {
        this.commandType = commandType;
        this.intervieweeName = intervieweeName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.commandType.equals(TIMESLOT_COMMAND_WORD)) {
            return this.executeTimeslotCommand(model);
        } else if (this.commandType.equals(ALL_TIMESLOTS_COMMAND_WORD)) {
            return this.executeAllTimeslotsCommand(model);
        } else if (this.commandType.equals(STATUS_COMMAND_WORD)) {
            return this.executeStatusCommand(model);
        } else {
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
    }

    /**
     * This method attempts to email the Interviewee with the name given in {@code intervieweeName}.
     */
    private CommandResult executeTimeslotCommand(Model model) throws CommandException {
        requireNonNull(model);
        List<Interviewee> intervieweeList = model.getUnfilteredIntervieweeList();
        Interviewee toEmail = null;

        for (Interviewee interviewee : intervieweeList) {
            if (interviewee.getName().equals(this.intervieweeName)) {
                toEmail = interviewee;
                break;
            }
        }

        if (toEmail == null) {
            throw new CommandException(MESSAGE_INVALID_PERSON_NAME);
        }

        if (model.getInterviewSlot(toEmail.getName().toString()).isEmpty()) {
            throw new CommandException(MESSAGE_NO_SLOTS_ALLOCATED);
        }

        try {
            model.emailInterviewee(toEmail);
            toEmail.setEmailSent(true);
        } catch (IOException ioe) {
            // Happens when the mail client cannot be launched, should prompt the user to open a URL instead
            throw new CommandException(MESSAGE_EMAIL_CLIENT_ERROR);
        }

        return new CommandResult(String.format(MESSAGE_EMAIL_INTERVIEWEE_SUCCESS, toEmail.getName()));
    }

    /**
     * This method attempts to email all Interviewees and provides a summary result at the end of a successful
     * execution.
     * Note: Interviewees without allocated slots and Interviewees that were emailed before are skipped.
     */
    private CommandResult executeAllTimeslotsCommand(Model model) throws CommandException {
        requireNonNull(model);
        List<Interviewee> intervieweeList = model.getUnfilteredIntervieweeList();
        int noSlots = 0;
        int skipped = 0;
        int noEmail = 0;
        int sent = 0;

        for (Interviewee interviewee : intervieweeList) {
            if (model.getInterviewSlot(interviewee.getName().toString()).isEmpty()) {
                // No slots are currently allocated to this interviewee
                noSlots++;
                continue;
            }

            if (interviewee.getEmailSent()) {
                skipped++;
                continue;
            }

            if (interviewee.getEmails().getAllEmails().isEmpty()) {
                noEmail++;
                continue;
            }

            try {
                model.emailInterviewee(interviewee);
                interviewee.setEmailSent(true);
                sent++;
            } catch (IOException ioe) {
                throw new CommandException(MESSAGE_EMAIL_CLIENT_ERROR);
            }
        }

        return new CommandResult(String.format(MESSAGE_EMAIL_ALL_SUCCESS, sent, skipped, noEmail, noSlots,
                intervieweeList.size()));
    }

    /**
     * This method provides a summary report of the number of Interviewees that has been emailed before and
     * the total number of Interviewees currently stored in the database.
     */
    private CommandResult executeStatusCommand(Model model) {
        requireNonNull(model);
        List<Interviewee> intervieweeList = model.getUnfilteredIntervieweeList();
        int sent = 0;

        for (Interviewee interviewee : intervieweeList) {
            if (interviewee.getEmailSent()) {
                sent++;
            }
        }

        return new CommandResult(String.format(MESSAGE_STATUS, sent, intervieweeList.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && intervieweeName.equals(((EmailCommand) other).intervieweeName)); // state check
    }
}
