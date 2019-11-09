package seedu.savenus.model.info;

import seedu.savenus.logic.commands.ShowCommand;

//@@author fatclarence
/**
 * Contains information on Withdraw command.
 */
public class ShowInfo {

    public static final String COMMAND_WORD = ShowCommand.COMMAND_WORD;

    public static final String INFORMATION = "show command allows you to\n"
            + "view your savings/withdrawals ONLY in your,\n"
            + "Savings History tab. Note that\n"
            + "this will only be shown during this command call.\n\n";

    public static final String USAGE = "show savings";

    public static final String OUTPUT = "Success message will be displayed and only savings\n"
            + "will be displayed in your Savings History tab.";
}
