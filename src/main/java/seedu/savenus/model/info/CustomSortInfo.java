package seedu.savenus.model.info;

import seedu.savenus.logic.commands.CustomSortCommand;

//@@author robytanama
/**
 * Contains information on CustomSort command.
 */
public class CustomSortInfo {
    public static final String COMMAND_WORD = CustomSortCommand.COMMAND_WORD;

    public static final String INFORMATION = "CustomSort command allows you to sort the Food item list "
            + "based on your own custom comparator.\n\n"
            + "The sorting will depend on the following factor:\n"
            + "The comparators you have specified in MakeSort\n\n";

    public static final String USAGE =
            "customsort";

    public static final String OUTPUT =
            "The Food item list will now be sorted based on the comparator you have specified.";
}
