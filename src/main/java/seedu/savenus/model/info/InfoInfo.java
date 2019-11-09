package seedu.savenus.model.info;

import seedu.savenus.logic.commands.InfoCommand;

//@@author robytanama
/**
 * Contains information on Info command.
 */
public class InfoInfo {

    public static final String COMMAND_WORD = InfoCommand.COMMAND_WORD;

    public static final String INFORMATION = "Info command allows you to know more about a certain command.\n\n"
            + "The information displayed will depend on the following factor:\n"
            + "Specified command\n\n";

    public static final String USAGE = "info add";

    public static final String OUTPUT = "Information about the Add command will be displayed.";
}
