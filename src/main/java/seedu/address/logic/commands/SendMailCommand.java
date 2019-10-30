package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Mailer;
import seedu.address.model.Model;
import seedu.address.model.OwnerAccount;

import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;

import static java.util.Objects.requireNonNull;

public class SendMailCommand extends Command {

    public static final String COMMAND_WORD = "sendMail";

    public static final String MESSAGE_SUCCESS = "Mail has been sent successfully";

    public static final String MESSAGE_SIGN_IN = "Please sign in using your email account before sending Email";

    public static final String MESSAGE_FAILURE = "Failed to send email.\n"
            + "Please check your internet connection and ensure that the following has been modified to your account security settings:\n"
            + "  - Enable Less secure app access\n"
            + "  - Disable the 2-Step Verification when Signing into your Google account\n"
            + "  - Please ensure the recipient's email address is correct";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sends Mail to recipient. \n"
            + "Parameters: "
            + PREFIX_RECIPIENT + "RECIPIENT EMAIL ADDRESS "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_MESSAGE + "EMAIL BODY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_RECIPIENT + "bob@gmail.com "
            + PREFIX_SUBJECT + "sending email "
            + PREFIX_MESSAGE + "Hello World!";

    private String recipient;
    private String subject;
    private String message;

    public SendMailCommand(String recipient, String subject, String message) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);
        OwnerAccount ownerAccount = model.getOwnerAccount();

        if (!model.isSignedIn()) {
            throw new CommandException(MESSAGE_SIGN_IN);
        }

        try {
            Mailer.sendEmail(ownerAccount.getEmail().value, ownerAccount.getPassword(), this.recipient, this.subject, this.message);
            return new CommandResult(String.format(MESSAGE_SUCCESS), COMMAND_WORD);
        } catch (Exception e) {
            return new CommandResult(MESSAGE_FAILURE, COMMAND_WORD);
        }
    }
}
