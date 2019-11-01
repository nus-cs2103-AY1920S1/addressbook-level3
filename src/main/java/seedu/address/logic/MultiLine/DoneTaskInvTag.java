
package seedu.address.logic.MultiLine;


import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddICommand;
import seedu.address.logic.commands.AddInventoryCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DoneTaskCommand;
import seedu.address.logic.commands.YesCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.inventory.InvName;
import seedu.address.model.inventory.Price;
import seedu.address.model.member.MemberId;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;

public class DoneTaskInvTag {

    public static CommandResult manageOne(CommandResult commandResult, Command command,
                                          Model model, ArrayList<Command>commands) throws CommandException, ParseException {
        String commandWord = commandResult.getFeedbackToUser().split("/",2)[0].trim();
        switch (commandWord) {
            case "Type-1":
                commands.add(command);
                return new CommandResult("Would you like to add this as a Inventory?");
            case "halt":
                commands.clear();
                return new CommandResult("Inventory not added");
            case "continue":
                commands.add(command);
                return new CommandResult("Please add the the price and member in the following format: " +
                        "\n" + "add-i p/[PRICE] mi/[MEMBER ID]");
            case "final":
                if(!commands.get(commands.size()-1).equals(new YesCommand())) {
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

    public static boolean isMultiLine(CommandResult commandResult) {
        String commandWord = commandResult.getFeedbackToUser().split("/",2)[0].trim();
        switch (commandWord) {
            case "continue":
            case "Type-2":
                return true;
            default:
                return false;
        }
    }

    public static AddInventoryCommand createCommandOne(Command command, Model model, ArrayList<Command>commands) throws ParseException {
        try {
            Index index;
            Name name;
            Command firstCommand = commands.get(0);
            if(firstCommand instanceof DoneTaskCommand) {
                index = new Index(((DoneTaskCommand)(commands.get(0))).getIndex());
                Task taskToAdd = model.getFilteredTasksList().get(index.getZeroBased());
                name = taskToAdd.getName();
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

