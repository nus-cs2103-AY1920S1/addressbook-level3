package seedu.savenus.model.info;

import seedu.savenus.logic.commands.MakeSortCommand;

//@@author robytanama
/**
 * Contains information on MakeSort command.
 */
public class MakeSortInfo {
    public static final String COMMAND_WORD = MakeSortCommand.COMMAND_WORD;

    public static final String INFORMATION = "MakeSort command allows you to define your own custom comparator to be "
            + "used during sorting.\n\n"
            + "The sorting comparator depends on the following factors:\n"
            + "The field such as PRICE\n"
            + "The comparator\n\n";

    public static final String USAGE =
            "makesort PRICE ASC";

    public static final String OUTPUT =
            "When customsort is called, the comparator will be based on PRICE and the list will be in ascending order.";
}
