package seedu.pluswork.logic.MultiLine;

import java.util.ArrayList;

import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.Model;

public abstract class MultiLine {
    CommandResult manageOne(CommandResult commandResult, Command command,
                                   Model model, ArrayList<Command> commands) throws CommandException, ParseException {
        return null;
    }

    boolean isMultiLine(CommandResult commandResult) {
        return false;
    }
}
