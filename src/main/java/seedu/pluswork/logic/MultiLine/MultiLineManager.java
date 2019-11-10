package seedu.pluswork.logic.MultiLine;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.pluswork.commons.core.LogsCenter;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.Model;

public class MultiLineManager {
    private final Logger logger = LogsCenter.getLogger(MultiLineManager.class);

    private Model model;
    private static boolean isMultiLine = false;
    private static ArrayList<Command> commands = new ArrayList<>();
    private static int currentType = 0;

    //Constructor
    public MultiLineManager(Model model) {
        this.model = model;
    }

    //commands methods
    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void add(Command s) {
        commands.add(s);
    }

    //isMultiLine methods
    public boolean getBoolean() {
        return isMultiLine;
    }

    public void change(boolean x) {
        isMultiLine = x;
    }

    //Manager methods

    public CommandResult manage(CommandResult commandResult, Command command) throws CommandException, ParseException {
        MultiLine commandToExec;
        if (isMultiLine) {
            switch (currentType) {
                case 1:
                    commandToExec = new DoneTaskMl();
                    break;
                case 2:
                    commandToExec = new ClearMl();
                    break;
                case 3:
                    commandToExec = new AddTaskMl();
                    break;
                default:
                    throw new ParseException("Invalid Command");
            }
            isMultiLine = commandToExec.isMultiLine(commandResult);
            return commandToExec.manageOne(commandResult, command, model, commands);
        } else {
            String commandWord = commandResult.getFeedbackToUser().split("/", 2)[0].trim();
            switch (commandWord) {
                case "Type-1":
                    currentType = 1;
                    isMultiLine = true;
                    commandToExec = new DoneTaskMl();
                    break;
                case "Type-2":
                    currentType = 2;
                    isMultiLine = true;
                    commandToExec = new ClearMl();
                    break;
                case "Type-3":
                    currentType = 3;
                    isMultiLine = true;
                    commandToExec = new AddTaskMl();
                    break;
                case "halt":
                case "continue":
                case "final":
                case "final2":
                    throw new ParseException("Command invalid in this situation");
                default:
                    currentType = 0;
                    return new CommandResult("No MultiLine");
            }
            return commandToExec.manageOne(commandResult, command, model, commands);
        }
    }
}
