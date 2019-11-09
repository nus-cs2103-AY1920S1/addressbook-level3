package seedu.savenus.model.info;

import seedu.savenus.logic.commands.BuyCommand;

//@@author robytanama
/**
 * Contains information on Buy command.
 */
public class BuyInfo {

    public static final String COMMAND_WORD = BuyCommand.COMMAND_WORD;

    public static final String INFORMATION = "Buy command allows you to buy a Food item from the food list using your"
            + " wallet.\n\n"
            + "The buy information will depend on the following factor:\n"
            + "Index of food you want to buy.\n\n";

    public static final String USAGE = "buy 2";

    public static final String OUTPUT = "Money from your wallet will be deducted according to price of Food item 2.";
}
