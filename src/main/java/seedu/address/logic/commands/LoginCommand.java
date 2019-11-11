package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.model.earnings.Earnings;
import seedu.address.storage.AccountStorage;
import seedu.address.storage.JsonAccountStorage;
import seedu.address.ui.UiManager;

/**
 * Logs the user in with a username and password provided by user.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Log in with your username and password " + "\n"

            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD \n"

            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "username1 "
            + PREFIX_PASSWORD + "pa5sw0rD";

    public static final String MESSAGE_SUCCESS = "Welcome back!";
    public static final String MESSAGE_FAILURE =
            "Wrong username or password. Please check login details and try again.";

    private final Account account;

    public LoginCommand(Account acc) {
        requireAllNonNull(acc);

        this.account = acc;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Earnings> lastShownList = model.getFilteredEarningsList();
        Earnings.setList(lastShownList);
        HashMap<String, ArrayList<Earnings>> list = model.getMap();
        Earnings.setHashMap(list);
        AccountStorage accountStorage = new JsonAccountStorage();

        if (Earnings.getTotalEarnings().equals("0.00")) {
            for (Earnings e : lastShownList) {
                Earnings.addToTotalEarnings(e.getAmount());
            }
        }

        try {
            if (accountStorage.getAccountsList().get().sameCredentials(account.getUsername(), account.getPassword())) {
                model.isLoggedIn();
                UiManager.startStudentProfile();
                UiManager.startReminderWindow();
                return new CommandResult(String.format(MESSAGE_SUCCESS, account));
            } else {
                throw new CommandException("Please login again.");
            }
        } catch (IOException | DataConversionException | IllegalArgumentException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && account.equals(((LoginCommand) other).account));
    }
}
