package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.help.BriefDescriptions;
import seedu.address.model.help.SecondaryCommand;
import seedu.address.model.help.Type;
import seedu.address.model.help.WebLinks;
import java.io.IOException;


import static java.util.Objects.requireNonNull;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static String SHOWING_HELP_MESSAGE = "Opened help window.";
    private Type type;
    private SecondaryCommand command;

    public HelpCommand(SecondaryCommand secondaryCommand, Type typeOfHelp) {
        command = requireNonNull(secondaryCommand);
        type = requireNonNull(typeOfHelp);
        SHOWING_HELP_MESSAGE = "Opened help window for the " + command.toString() +
                " command. Type of help requested: " + type.toString();
    }

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions. " +
            "Parameters: cmd/COMMAND type/TYPE\n"
            + "Example: " + COMMAND_WORD + " cmd/add type/guide";

    @Override
    public CommandResult execute(Model model) throws IOException {

        if (type.toString().equals("guide")){
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(requireNonNull(WebLinks.getLink(command))));
        }
        else if (type.toString().equals("brief")){
            String briefDescription = BriefDescriptions.getDescription(command);
            return new CommandResult(briefDescription, false, false);
        }
        return new CommandResult(SHOWING_HELP_MESSAGE, false, false);
    }
}
