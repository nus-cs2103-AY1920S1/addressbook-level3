package seedu.savenus.model.info;

import seedu.savenus.logic.commands.WithdrawCommand;

//@@author robytanama
/**
 * Contains information on Withdraw command.
 */
public class WithdrawInfo {

    public static final String COMMAND_WORD = WithdrawCommand.COMMAND_WORD;

    public static final String INFORMATION = "Withdraw command allows you to withdraw a certain amount of money "
            + "from your savings account.\n\n"
            + "The withdrawn amount will depend on the following factor:\n"
            + "The amount specified\n\n";

    public static final String USAGE = "withdraw 100";

    public static final String OUTPUT = "$100 will be withdrawn from your savings account into your wallet";
}
