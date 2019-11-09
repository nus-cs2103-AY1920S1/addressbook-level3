package seedu.savenus.model.info;

import seedu.savenus.logic.commands.DefaultCommand;

//@@author robytanama
/**
 * Contains information on default command.
 */
public class DefaultInfo {

    public static final String COMMAND_WORD = DefaultCommand.COMMAND_WORD;

    public static final String INFORMATION = "Default command allows you to return the Food list to its original order."
            + "\n\n";

    public static final String USAGE = "default";

    public static final String OUTPUT = "The Food list will be reverted back to its natural order.";
}
