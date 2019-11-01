package seedu.address.logic.MultiLine;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.YesCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ProjectDashboard;

public class ClearQuestion {

    public static final String MESSAGE_SUCCESS = "Dashboard has been cleared!";

    public static CommandResult manageOne(CommandResult commandResult, Command command,
                                          Model model, ArrayList<Command> commands) throws CommandException, ParseException {
        String commandWord = commandResult.getFeedbackToUser().trim();
        switch (commandWord) {
            case "Type-2":
                commands.add(command);
                return new CommandResult("Confirm clear DashBoard? (yes/no)");
            case "halt":
                return new CommandResult("DashBoard not cleared");
            case "continue":
                commands.add(command);
                model.setProjectDashboard(new ProjectDashboard());
                commands.clear();
                return new CommandResult(MESSAGE_SUCCESS);
            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    public static boolean isMultiLine(CommandResult commandResult) {
        String commandWord = commandResult.getFeedbackToUser().trim();
        switch (commandWord) {
            case "halt":
            case "continue":
                return false;
            default:
                return true;
        }
    }
}
