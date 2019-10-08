package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.help.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;


import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static String SHOWING_HELP_MESSAGE = "Opened help window.";
    private Type type;
    private SecondaryCommand command;

    public HelpCommand() {
    }

    public HelpCommand(SecondaryCommand secondaryCommand, Type typeOfHelp) {
        command = requireNonNull(secondaryCommand);
        type = requireNonNull(typeOfHelp);
        SHOWING_HELP_MESSAGE = "Opened help window for the " + command.toString() +
                " command. Type of help requested: " + type.toString();
    }

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions. " +
            "Parameters: cmd/COMMAND type/TYPE\n" +
            "Example: " + COMMAND_WORD + " cmd/add type/guide       " +
            "For more details, press F1 for list of available Commands and Types";


    /**
     * Resets the variables for Type and SecondaryCommand.
     */

    private void ResetCommandAndTypeValues(){
        type = null;
        command = null;
    }

    @Override
    public CommandResult execute(Model model) throws IOException {

        if (isNull(type)&&isNull(command)) {
            return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        } else {
            switch (type.toString()) {
            case "guide":
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(requireNonNull(WebLinks.getLink(command))));
                break;
            case "brief":
                String briefDescription = BriefDescriptions.getDescription(command);
                ResetCommandAndTypeValues();
                return new CommandResult(briefDescription, false, false);
            case "api":
                File f = new File(ApiLinks.getLink(command));
                Desktop.getDesktop().browse(f.toURI());
                break;
            }
            ResetCommandAndTypeValues();
            return new CommandResult(SHOWING_HELP_MESSAGE, false, false);
        }
    }
}
