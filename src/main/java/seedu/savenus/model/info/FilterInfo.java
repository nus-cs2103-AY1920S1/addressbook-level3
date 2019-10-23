package seedu.savenus.model.info;

import seedu.savenus.logic.commands.FilterCommand;

//@@author fatclarence
/**
 * Contains information on <code>Filter</code> command.
 */
public class FilterInfo {

    public static final String COMMAND_WORD = FilterCommand.COMMAND_WORD;

    public static final String INFORMATION = "Filter command allows you to include in your food menu, \n"
            + "a list of foods that you want to see.\n\n";

    public static final String USAGE = "filter FIELD QUANTIFIER VALUE\n"
            + "eg. filter PRICE LESS_THAN 5\n"
            + "This returns you a list of foods that you want to see";

    public static final String OUTPUT = "The app will display only foods that you \n"
            + "have indicated in your command.";
}
