package seedu.savenus.model.info;

import seedu.savenus.logic.commands.FilterCommand;

//@@author robytanama
/**
 * Contains information on Filter command.
 */
public class FilterInfo {

    public static final String COMMAND_WORD = FilterCommand.COMMAND_WORD;

    public static final String INFORMATION = "Filter command allows you to obtain a new filtered list\n\n"
            + "The new filtered list will depend on the following factors:\n"
            + "The field specified\n"
            + "The quantifier specified\n"
            + "The value specified\n\n";

    public static final String USAGE = "filter PRICE LESS_THAN 5.00";

    public static final String OUTPUT = "New list will be shown containing Food with PRICE less than 5.00";
}
