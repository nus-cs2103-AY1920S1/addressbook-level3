package seedu.pluswork.logic.MultiLine;

import java.time.LocalDateTime;
import java.util.ArrayList;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.commands.member.AssignCommand;
import seedu.pluswork.logic.commands.multiline.AddDCommand;
import seedu.pluswork.logic.commands.multiline.YesCommand;
import seedu.pluswork.logic.commands.task.SetDeadlineCommand;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.member.MemberId;

class AddTaskMl extends MultiLine {
    CommandResult manageOne(CommandResult commandResult, Command command,
                            Model model, ArrayList<Command> commands) throws CommandException, ParseException {
        String commandWord = commandResult.getFeedbackToUser().split("/", 2)[0].trim();
        switch (commandWord) {
            case "Type-3":
                String TaskName = commandResult.getFeedbackToUser().split("/", 2)[1].trim();
                commands.add(command);
                return new CommandResult(TaskName + "\n" + "Would you like to add deadline or member? (yes/no)");
            case "halt":
                commands.clear();
                return new CommandResult("Deadline/ Member not added");
            case "continue":
                commands.add(command);
                return new CommandResult("Please add the the deadline and member in the following format: " +
                        "\n" + "add-d at/[dd-mm-yyyy hh:mm] mi/[MEMBER ID]");
            case "final2":
                if (!commands.get(commands.size() - 1).equals(new YesCommand())) {
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

    boolean isMultiLine(CommandResult commandResult) {
        String commandWord = commandResult.getFeedbackToUser().trim();
        switch (commandWord) {
            case "continue":
            case "Type-3":
                return true;
            default:
                return false;
        }
    }

    private static CommandResult processCommand(Command command, Model model, ArrayList<Command> commands) throws CommandException {
        int length = model.getTasksLength();
        LocalDateTime deadline = ((AddDCommand) command).getDeadline();
        MemberId memId = ((AddDCommand) command).getMemId();

        CommandResult commandResult1 = null;
        CommandResult commandResult2 = null;
        if (!(memId.equals(new MemberId("NIL")))) {
            AssignCommand command1 = new AssignCommand(new Index(length - 1), memId);
            commandResult1 = command1.execute(model);
        }
        if (!(deadline == LocalDateTime.MIN)) {
            SetDeadlineCommand command2 = new SetDeadlineCommand(new Index(length - 1), deadline);
            commandResult2 = command2.execute(model);
        }

        if ((commandResult1 != null) && (commandResult2 != null)) {
            return new CommandResult(commandResult1.getFeedbackToUser() + "\n"
                    + commandResult2.getFeedbackToUser());
        } else if (commandResult1 == null && commandResult2 != null) {
            return new CommandResult(commandResult2.getFeedbackToUser());
        } else if (commandResult2 == null && commandResult1 != null) {
            return new CommandResult(commandResult1.getFeedbackToUser());
        } else {
            throw new CommandException("No deadline or member found");
        }
    }
}
