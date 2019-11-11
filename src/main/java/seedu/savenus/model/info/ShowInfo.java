package seedu.savenus.model.info;

import seedu.savenus.logic.commands.ShowCommand;

//@@author robytanama
/**
 * Contains information on Withdraw command.
 */
public class ShowInfo {

    public static final String COMMAND_WORD = ShowCommand.COMMAND_WORD;

    public static final String INFORMATION = "Show command allows you to modify the Savings History tab.\n\n"
            + "The show command will depend on the following factor:\n"
            + "Type of savings interaction you want to filter\n\n"
            + "You are able to either only view savings, withdrawals, or both.\n\n";

    public static final String USAGE = "show savings";

    public static final String OUTPUT = "The Savings History tab will now only show savings.\n"
            + "Withdrawals will be filtered out.";
}
