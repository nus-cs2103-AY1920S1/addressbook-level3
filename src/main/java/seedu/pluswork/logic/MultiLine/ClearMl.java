package seedu.pluswork.logic.MultiLine;

import static seedu.pluswork.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;

import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ProjectDashboard;

public class ClearMl extends MultiLine {

    public static final String MESSAGE_SUCCESS = "Dashboard has been cleared!";

    CommandResult manageOne(CommandResult commandResult, Command command,
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

    boolean isMultiLine(CommandResult commandResult) {
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
