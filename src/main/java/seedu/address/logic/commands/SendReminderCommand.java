package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Mailer;
import seedu.address.model.Model;
import seedu.address.model.OwnerAccount;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_CHECKED_OUT;

public class SendReminderCommand extends Command {

    public static final Prefix PREFIX_DURATION = new Prefix("d/");

    public static final String COMMAND_WORD = "sendReminder";

    public static final String MESSAGE_SUCCESS = "Reminders has been sent!";

    public static final String MESSAGE_SIGN_IN = "Please sign in using your email account before sending Reminder";

    public static final String MESSAGE_FAILURE = "Please check your internet connection and ensure that the following has been modified to your account security settings:\n"
            + "  - Enable Less secure app access\n"
            + "  - Disable the 2-Step Verification when Signing into your Google account\n"
            + "  - Please ensure the recipients' email address is correct";

    public static final String MESSAGE_EMPTY_MEMBERS = "No members have been added to the project.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sends reminder to project members.\n"
            + "Parameters: "
            + PREFIX_DURATION + "DURATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DURATION + "3";

    private int durationDays;

    public SendReminderCommand(int duration) {
        this.durationDays = duration;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);
        OwnerAccount ownerAccount = model.getOwnerAccount();

        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }

        if (!model.isSignedIn()) {
            throw new CommandException(MESSAGE_SIGN_IN);
        }

        Project project = model.getWorkingProject().get();

        //getting list of meetings and tasks
        Set<Meeting> meetingSet = project.getListOfMeeting();
        List<Meeting> meetingList = new ArrayList<>(meetingSet);
        List<Task> taskList = project.getTasks();

        //obtaining List<Person> recipientsList from List<String> that is in the project and model.getMembers()
        List<Person> contactList = model.getMembers();
        HashMap<Name, Person> contactSet = new HashMap<Name, Person>();
        for (Person person: contactList) {
            contactSet.put(person.getName(), person);
        }

        List<String> recipientsListInString = project.getMemberNames();
        List<Person> recipientsList = new ArrayList<>();
        for (String name: recipientsListInString) {
            Name n = new Name(name);
            recipientsList.add(contactSet.get(n));
        }

        //get current time
        java.util.Date date = new java.util.Date();

        String meetingsReminder = "Please take note that the following meeting(s) is coming in " + durationDays + " days:\n";
        String taskReminder = "Please take note that the following task(s) is due in " + durationDays + " days:\n";

        Collections.sort(meetingList, Comparator.comparing(m -> m.getTime().getDate()));
        for (Meeting meeting: meetingList) {
            Date d = meeting.getTime().getDate();
            long startTime = date.getTime();
            long endTime = d.getTime();
            long diffTime = endTime - startTime;
            long diffDays = diffTime / (1000 * 60 * 60 * 24);
            if (diffDays <= this.durationDays) {
                meetingsReminder = meetingsReminder + "-  " + meeting.toString() + "\n";
            }
        }

        for (Task task: taskList) {
            Date d = task.getTime().getDate();
            long startTime = date.getTime();
            long endTime = d.getTime();
            long diffTime = endTime - startTime;
            long diffDays = diffTime / (1000 * 60 * 60 * 24);
            if (diffDays <= this.durationDays) {
                taskReminder = taskReminder + "-  " + task.toString() + "\n";
            }
        }

        String message = meetingsReminder + "\n" + taskReminder;

        for (Person person: recipientsList) {
            String recipientEmail = person.getEmail().value;
            try {
                Mailer.sendEmail(ownerAccount.getEmail().value, ownerAccount.getPassword(), recipientEmail, "Meeting and Task Reminder", message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (recipientsList.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_MEMBERS, COMMAND_WORD);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS), COMMAND_WORD);
    }
}
