package seedu.savenus.model.info;

import seedu.savenus.logic.commands.CollapseCommand;

//@@author fatclarence
/**
 * Contains information on <code>Filter</code> command.
 */
public class CollapseInfo {

    public static final String COMMAND_WORD = CollapseCommand.COMMAND_WORD;

    public static final String INFORMATION = "Collapse command allows you to enjoy a compacted view\n"
            + " of the description of the food item \n"
            + "a list of foods that you want to see.\n\n";

    public static final String USAGE = "collapse\n";

    public static final String OUTPUT = "The app will display only a compacted view of the\n"
            + "attributes of the food \n"
            + "have indicated in your command.";
}