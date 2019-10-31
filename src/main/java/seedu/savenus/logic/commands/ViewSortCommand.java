package seedu.savenus.logic.commands;

import seedu.savenus.model.Model;

/**
 * Allows the user to view the current Custom Sorter.
 */
public class ViewSortCommand extends Command {
    public static final String COMMAND_WORD = "viewsort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the current Custom Sorter.\n"
            + "Example: " + COMMAND_WORD;

    public static final String VIEW_COMMAND_SUCCESS = "Here are the fields specified for your CustomSorter: \n"
            + "%s";

    @Override
    public CommandResult execute(Model model) {
        String customSorterAsString = model.getCustomSorter().toString();
        return new CommandResult(String.format(VIEW_COMMAND_SUCCESS, customSorterAsString));
    }
}
