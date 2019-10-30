package seedu.savenus.model.info;

import seedu.savenus.logic.commands.CollapseCommand;

/**
 * Contains information on Collapse command.
 */
public class CollapseInfo {

    public static final String COMMAND_WORD = CollapseCommand.COMMAND_WORD;

    public static final String INFORMATION = "Collapse command allows you to make the FoodCard look more compact.\n\n";

    public static final String USAGE = "collapse";

    public static final String OUTPUT = "The FoodCard will now have a more compact look.";
}
