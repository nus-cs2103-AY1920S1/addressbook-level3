package seedu.savenus.model.info;

import seedu.savenus.logic.commands.BudgetCommand;

//@@author robytanama
/**
 * Contains information on Budget command.
 */
public class BudgetInfo {

    public static final String COMMAND_WORD = BudgetCommand.COMMAND_WORD;

    public static final String INFORMATION = "Budget command allows you to set your current allowance for"
            + " a specified period of time.\n\n"
            + "The added Budget information will depend on the following factors:\n"
            + "Amount of money\n"
            + "Period of time\n\n";

    public static final String USAGE = "Budget 100 23";

    public static final String OUTPUT = "Amount of $100.00 will be added to your wallet for 23 days.";

}
