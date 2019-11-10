package seedu.savenus.model.info;

import seedu.savenus.logic.commands.TopUpCommand;

//@@author robytanama
/**
 * Contains information on the TopUpCommand.
 */
public class TopUpInfo {

    public static final String COMMAND_WORD = TopUpCommand.COMMAND_WORD;

    public static final String INFORMATION = "TopUp command allows you to add money into your wallet\n\n"
            + "The top up will depend on the following factor:\n"
            + "Amount of money specified";

    public static final String USAGE = TopUpCommand.COMMAND_WORD + " 100";

    public static final String OUTPUT = "$100 will be added into your wallet.";
}
