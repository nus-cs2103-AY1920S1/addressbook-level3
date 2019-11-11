package seedu.pluswork.logic.MultiLine;


import java.util.ArrayList;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.multiline.AddICommand;
import seedu.pluswork.logic.commands.inventory.AddInventoryCommand;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.task.DoneTaskCommand;
import seedu.pluswork.logic.commands.multiline.YesCommand;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.inventory.InvName;
import seedu.pluswork.model.inventory.Price;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.Task;

class DoneTaskMl extends MultiLine {

    CommandResult manageOne(CommandResult commandResult, Command command,
                            Model model, ArrayList<Command> commands) throws CommandException, ParseException {
        String commandWord = commandResult.getFeedbackToUser().split("/", 2)[0].trim();
        switch (commandWord) {
            case "Type-1":
                commands.add(command);
                return new CommandResult("Would you like to add this as a Inventory? (yes/no)");
            case "halt":
                commands.clear();
                return new CommandResult("Inventory not added");
            case "continue":
                commands.add(command);
                return new CommandResult("Please add the the price and member in the following format: " +
                        "\n" + "add-i p/[PRICE] mi/[MEMBER ID]");
            case "final":
                if (!commands.get(commands.size() - 1).equals(new YesCommand())) {
                    throw new CommandException("Invalid Command for this situation");
                }
                commands.add(command);
                Command commandNew = createCommandOne(command, model, commands);
                commands.clear();
                return commandNew.execute(model);
            default:
                throw new ParseException("Command invalid in this situation, multiline chain broken");
        }
    }

    boolean isMultiLine(CommandResult commandResult) {
        String commandWord = commandResult.getFeedbackToUser().split("/", 2)[0].trim();
        switch (commandWord) {
            case "continue":
            case "Type-2":
                return true;
            default:
                return false;
        }
    }

    private static AddInventoryCommand createCommandOne(Command command, Model model, ArrayList<Command> commands) throws ParseException {
        try {
            Index index;
            Name name;
            Command firstCommand = commands.get(0);
            if (firstCommand instanceof DoneTaskCommand) {
                index = new Index(((DoneTaskCommand) (commands.get(0))).getIndex());
                Task taskToAdd = model.getFilteredTasksList().get(index.getZeroBased());
                String nameRaw = taskToAdd.getName().toString();

                String[] splitNameRaw = nameRaw.split(" ", 2);
                if (splitNameRaw[0].equals("buy") || splitNameRaw[0].equals("get")) {
                    name = new Name(splitNameRaw[1]);
                } else {
                    name = new Name(nameRaw);
                }
            } else {
                throw new ParseException("Invalid Command");
            }

            if (command instanceof AddICommand) {
                Price price = ((AddICommand) command).getPrice();
                MemberId memId = ((AddICommand) command).getMemId();
                InvName nameInv = new InvName(name.toString());
                return new AddInventoryCommand(index, nameInv, price, memId);
            } else {
                throw new ParseException("Invalid Command");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Index out of bounds at MultiLIneManager, line 120");
        }
    }
}

