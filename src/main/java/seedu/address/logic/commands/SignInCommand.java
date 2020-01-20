package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.OwnerAccount;

import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

public class SignInCommand extends Command {
    private OwnerAccount ownerAccount;

    public static final String COMMAND_WORD = "signIn";

    public static final String MESSAGE_SUCCESS = "Signed In Successfully";

    public static final String MESSAGE_SIGNED_IN = "You have signed in, please log out to sign in to another account";

    public static final String MESSAGE_SIGN_IN_ISSUE = "Unable to sign in. Please check that: \n"
            + "  - You have correctly input the email address and password.\n"
            + "  - You are connected to the Internet.\n"
            + "  - You have enabled Less secure app access in your Google Account Setting\n"
            + "  - You have disabled the 2-Step Verification in your Google Account Setting";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sign In to your Email Account. \n"
            + "Parameters: "
            + PREFIX_ACCOUNT + "EMAIL_ACCOUNT "
            + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ACCOUNT + "alice@gmail.com "
            + PREFIX_PASSWORD + "12345678";

    public SignInCommand(OwnerAccount ownerAccount) {
        requireNonNull(ownerAccount);
        this.ownerAccount = ownerAccount;
    }

    final Logger logger = LogsCenter.getLogger(SendMailCommand.class);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isSignedIn() && this.ownerAccount.equals(model.getOwnerAccount())) {
            throw new CommandException("You are signed in with the Account");
        } else if (model.isSignedIn()) {
            throw new CommandException(MESSAGE_SIGNED_IN);
        }

        //Can try to use other legit method, current method is to send dummy email to cs2103t17@gmail.com and see if email can be sent.
        try {
            model.signIn(ownerAccount);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_SIGN_IN_ISSUE);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS), COMMAND_WORD);
    }

}
