package seedu.savenus.model.info;

import seedu.savenus.logic.commands.SaveCommand;

//@@author fatclarence
/**
 * Contains information on <code>Save</code> command.
 */
public class SaveInfo {

    public static final String COMMAND_WORD = SaveCommand.COMMAND_WORD;

    public static final String INFORMATION = "Save command allows the user to save a specified "
            + "amount of money from his wallet into his savings account.\n\n";

    public static final String USAGE = "save AMOUNT";

    public static final String OUTPUT = "Savings will be added into the user's savings"
            + " account and reflected in the savings history. Click the tab \"Savings\"";
}
