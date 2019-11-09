package seedu.savenus.model.info;

import seedu.savenus.logic.commands.WithdrawCommand;

//@@author fatclarence
/**
 * Contains information on Withdraw command.
 */
public class WithdrawInfo {

    public static final String COMMAND_WORD = WithdrawCommand.COMMAND_WORD;

    public static final String INFORMATION = "withdraw command allows you to\n"
            + "withdraw a sum of money from your savings account,\n"
            + "and transfer it into your budget wallet.\n"
            + "Limit of withdrawal is a maximum uneditable amount $1,000,000.\n\n";

    public static final String USAGE = "withdraw 100 OR withdraw 100.00";

    public static final String OUTPUT = "$100 will be deducted from your wallet and is\n"
            + "added into your wallet.";
}
