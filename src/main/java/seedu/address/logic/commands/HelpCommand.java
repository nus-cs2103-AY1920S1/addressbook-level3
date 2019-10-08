package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import seedu.address.model.Model;
import seedu.address.model.help.ApiLinks;
import seedu.address.model.help.BriefDescriptions;
import seedu.address.model.help.SecondaryCommand;
import seedu.address.model.help.Type;
import seedu.address.model.help.WebLinks;


/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions. "
            + "Parameters: cmd/COMMAND type/TYPE\n"
            + "Example: " + COMMAND_WORD + " cmd/add type/guide       "
            + "For more details, press F1 for list of available Commands and Types";

    public static String helpMessage = "Opened help window.";
    
    private Type type;
    private SecondaryCommand command;

    public HelpCommand() {
    }

    public HelpCommand(SecondaryCommand secondaryCommand, Type typeOfHelp) {
        command = requireNonNull(secondaryCommand);
        type = requireNonNull(typeOfHelp);
        helpMessage = "Opened help window for the "
                + command.toString()
                + " command. Type of help requested: " + type.toString();
    }

    /**
     * Resets the variables for Type and SecondaryCommand.
     */

    private void resetCommandAndTypeValues() {
        type = null;
        command = null;
    }

    @Override
    public CommandResult execute(Model model) throws IOException {

        if (isNull(type) && isNull(command)) {
            return new CommandResult(helpMessage, true, false);
        } else {
            switch (type.toString()) {
            case "guide":
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(requireNonNull(WebLinks.getLink(command))));
                break;
            case "brief":
                String briefDescription = BriefDescriptions.getDescription(command);
                resetCommandAndTypeValues();
                return new CommandResult(briefDescription, false, false);
            case "api":
                File f = new File(ApiLinks.getLink(command));
                Desktop.getDesktop().browse(f.toURI());
                break;
            default:
                resetCommandAndTypeValues();
                return new CommandResult(helpMessage, false, false);
            }
            resetCommandAndTypeValues();
            return new CommandResult(helpMessage, false, false);
        }
    }
}
