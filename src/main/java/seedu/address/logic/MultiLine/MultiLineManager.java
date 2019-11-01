package seedu.address.logic.MultiLine;

import java.util.ArrayList;
import java.util.logging.Logger;


import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

public class MultiLineManager {
    private final Logger logger = LogsCenter.getLogger(MultiLineManager.class);

    private Model model;
    private static boolean isMultiLine = false;
    private static ArrayList<Command>commands = new ArrayList<>();
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
        if(isMultiLine) {
            switch (currentType) {
                case 1:
                    isMultiLine = DoneTaskInvTag.isMultiLine(commandResult);
                    return DoneTaskInvTag.manageOne(commandResult,command, model, commands);
                case 2:
                    isMultiLine = ClearQuestion.isMultiLine(commandResult);
                    return ClearQuestion.manageOne(commandResult,command, model, commands);
                case 3:
                    isMultiLine = AddTaskDetails.isMultiLine(commandResult);
                    return AddTaskDetails.manageOne(commandResult,command, model, commands);
                default:
                    throw new ParseException("Invalid Command");
            }
        } else {
            String commandWord = commandResult.getFeedbackToUser().split("/",2)[0].trim();
            switch(commandWord) {
                case "Type-1":
                    currentType = 1;
                    isMultiLine = true;
                    return DoneTaskInvTag.manageOne(commandResult,command, model, commands);
                case "Type-2":
                    currentType = 2;
                    isMultiLine = true;
                    return ClearQuestion.manageOne(commandResult,command, model, commands);
                case "Type-3":
                    currentType = 3;
                    isMultiLine = true;
                    return AddTaskDetails.manageOne(commandResult,command, model, commands);
                case "halt":
                case "continue":
                case "final":
                case "final2":
                    throw new ParseException("Command invalid in this situation");
                default:
                    return new CommandResult("No MultiLine");
            }
        }
    }
}
