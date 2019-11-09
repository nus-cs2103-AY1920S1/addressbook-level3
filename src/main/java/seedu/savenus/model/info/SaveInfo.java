package seedu.savenus.model.info;

import seedu.savenus.logic.commands.SaveCommand;

//@@author robytanama
/**
 * Contains information on Save command.
 */
public class SaveInfo {

    public static final String COMMAND_WORD = SaveCommand.COMMAND_WORD;

    public static final String INFORMATION = "Save command allows you to save a specified "
            + "amount of money from the wallet into the savings account.\n\n"
            + "The Savings information will depend on the following factors:\n"
            + "Wallet amount\n"
            + "Amount specified\n\n";

    public static final String USAGE = "save 20";

    public static final String OUTPUT = "$20 will be added into the user's savings"
            + " account and reflected in the savings history. Click the tab \"Savings\"";
}
