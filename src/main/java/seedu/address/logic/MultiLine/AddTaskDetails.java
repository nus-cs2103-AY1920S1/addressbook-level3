package seedu.address.logic.MultiLine;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.time.LocalDateTime;
import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddDCommand;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SetDeadlineCommand;
import seedu.address.logic.commands.YesCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.member.MemberId;

public class AddTaskDetails {
    public static CommandResult manageOne(CommandResult commandResult, Command command,
                                          Model model, ArrayList<Command> commands) throws CommandException, ParseException {
        String commandWord = commandResult.getFeedbackToUser().split("/",2)[0].trim();
        switch (commandWord) {
            case "Type-3":
                String TaskName = commandResult.getFeedbackToUser().split("/",2)[1].trim();
                commands.add(command);
                return new CommandResult(TaskName + "\n" + "Would you like to add deadline or member? (yes/no)");
            case "halt":
                commands.clear();
                return new CommandResult("Deadline/ Member not added");
            case "continue":
                commands.add(command);
                return new CommandResult("Please add the the deadline and member in the following format: " +
                        "\n" + "add-d at/[DEADLINE] mi/[MEMBER ID]");
            case "final2":
                if(!commands.get(commands.size()-1).equals(new YesCommand())) {
                    throw new CommandException("Invalid Command for this situation");
                }
                commands.add(command);
                CommandResult commandNew = processCommand(command, model, commands);
                commands.clear();
                return commandNew;
            default:
                throw new ParseException("Command invalid in this situation, multiline chain broken");
        }
    }

    public static boolean isMultiLine(CommandResult commandResult) {
        String commandWord = commandResult.getFeedbackToUser().trim();
        switch (commandWord) {
            case "continue":
            case "Type-3":
                return true;
            default:
                return false;
        }
    }

    private static CommandResult processCommand(Command command, Model model, ArrayList<Command>commands) throws CommandException {
        int length = model.getTasksLength();
        LocalDateTime deadline = ((AddDCommand)command).getDeadline();
        MemberId memId = ((AddDCommand)command).getMemId();

        AssignCommand command1 = new AssignCommand(new Index(length-1), memId);
        CommandResult commandResult1 = command1.execute(model);

        SetDeadlineCommand command2 = new SetDeadlineCommand(new Index(length-1) , deadline);
        CommandResult commandResult2 = command2.execute(model);

        return new CommandResult(commandResult1.getFeedbackToUser() + "\n"
                                                    + commandResult2.getFeedbackToUser());
    }
}
