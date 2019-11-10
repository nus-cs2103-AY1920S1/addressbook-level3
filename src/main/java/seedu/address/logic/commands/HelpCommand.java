package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;

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
            + "Example: " + COMMAND_WORD + " cmd/add_claim type/guide       "
            + "For more details, press F1 for list of available Commands and Types";

    private static String helpMessage = "Opened help window.";

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

    /**
     * Gets the help message
     */

    public static String getHelpMessage() {
        return helpMessage;
    }

    @Override
    public CommandResult execute(Model model) throws IOException, URISyntaxException {

        if (isNull(type) && isNull(command)) {
            return new CommandResult(helpMessage, true, false, false, false);
        } else {
            switch (type.toString()) {
            case "guide":
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(requireNonNull(WebLinks.getLink(command))));
                break;
            case "brief":
                String briefDescription = BriefDescriptions.getDescription(command);
                resetCommandAndTypeValues();
                return new CommandResult(briefDescription, false, false, false, false);
            case "api":
                File htmlFile = new File("API.html");
                if (htmlFile.exists()) {
                    htmlFile.delete();
                }
                    InputStream link = (getClass().getResourceAsStream(ApiLinks.getLink(command)));
                    Files.copy(link, htmlFile.getAbsoluteFile().toPath());
                Desktop.getDesktop().browse(htmlFile.toURI());
                break;
            default:
                resetCommandAndTypeValues();
                return new CommandResult(helpMessage, false, false, false, false);
            }
            resetCommandAndTypeValues();
            return new CommandResult(helpMessage, false, false, false, false);
        }
    }
}
