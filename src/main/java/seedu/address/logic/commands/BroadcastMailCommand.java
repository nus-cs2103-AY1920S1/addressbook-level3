package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Mailer;
import seedu.address.model.Model;
import seedu.address.model.OwnerAccount;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_CHECKED_OUT;


public class BroadcastMailCommand extends Command {

    public static final String COMMAND_WORD = "broadcastMail";

    public static final String MESSAGE_SUCCESS = "Mails has been sent successfully";

    public static final String MESSAGE_SIGN_IN = "Please sign in using your email account before sending Email";

    public static final String MESSAGE_FAILURE = "Please check your internet connection and ensure that the following has been modified to your account security settings:\n"
            + "  - Enable Less secure app access\n"
            + "  - Disable the 2-Step Verification when Signing into your Google account\n"
            + "  - Please ensure the recipients' email address is correct";

    public static final String MESSAGE_EMPTY_MEMBERS = "No members have been added to the project.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": broadcasts mail to project members.\n"
            + "Parameters: "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_MESSAGE + "EMAIL BODY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBJECT + "sending email"
            + PREFIX_MESSAGE + "Hello World!";

    private String subject;
    private String message;

    public BroadcastMailCommand(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);
        OwnerAccount ownerAccount = model.getOwnerAccount();

        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }

        Project project = model.getWorkingProject().get();

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

        if (!model.isSignedIn()) {
            throw new CommandException(MESSAGE_SIGN_IN);
        }

        String unsentMails = "";
        String listOfUnsentMails = "Mails could not be sent to the following members:\n";

        for (Person person: recipientsList) {
            String recipientEmail = person.getEmail().value;
            try {
                Mailer.sendEmail(ownerAccount.getEmail().value, ownerAccount.getPassword(), recipientEmail, this.subject, this.message);
            } catch (Exception e) {
                if (!unsentMails.equals("")) {
                    unsentMails = unsentMails + "\n" + "  - " + person.getName().toString();
                } else {
                    unsentMails = "  - " + person.getName().toString();
                }
                continue;
            }
        }

        if (recipientsList.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_MEMBERS, COMMAND_WORD);
        }

        if (unsentMails.equals("")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS), COMMAND_WORD);
        } else {
            return new CommandResult((listOfUnsentMails + unsentMails + "\n" + MESSAGE_FAILURE), COMMAND_WORD);
        }
    }
}
