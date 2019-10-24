package seedu.savenus.model.info;

import seedu.savenus.logic.commands.CustomSortCommand;

/**
 * Contains information on CustomSort command.
 */
public class CustomSortInfo {
    public static final String COMMAND_WORD = CustomSortCommand.COMMAND_WORD;

    public static final String INFORMATION =
            "Sort Using your own custom comparator, which you have creating from MakeSort.";
    public static final String USAGE =
            "customsort";

    public static final String OUTPUT =
            "You have successfully sorted the food items!!";
}
