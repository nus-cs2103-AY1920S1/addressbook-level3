package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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

public class MultiLineManager {
    private final Logger logger = LogsCenter.getLogger(MultiLineManager.class);

    private Model model;
    private static boolean isMultiLine = false;
    private static ArrayList<Command>commands = new ArrayList<>();

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
            return manageOne(commandResult, command);
        } else {
            String commandWord = commandResult.getFeedbackToUser().split("/",2)[0].trim();
            if(commandWord.equals("Type-1")) {
                return manageOne(commandResult, command);
            }
            else if(commandResult.equals(new CommandResult("halt")) ||
                    commandResult.equals(new CommandResult("continue")) ||
                    commandResult.equals(new CommandResult("final"))) {
                throw new ParseException("Command invalid in this situation");
            }
            else {
                return new CommandResult("");
            }
        }
    }

    //DONE TASK METHODS
    public CommandResult manageOne(CommandResult commandResult, Command command) throws CommandException, ParseException {
        if (isMultiLine) {
            if (commandResult.equals(new CommandResult("halt"))) {
                this.change(false);
                return new CommandResult("Inventory not added");
            } else if (commandResult.equals(new CommandResult("continue"))) {
                this.add(command);
                logger.info(this.getCommands().toString());
                return new CommandResult("Please add the the price and member in the following format: " +
                        "\n" + "add-i p/[PRICE] mi/[MEMBER ID]");
            } else if (commandResult.equals(new CommandResult("final"))) {
                if(!commands.get(commands.size()-1).equals(new YesCommand())) {
                    throw new CommandException("Invalid Command for this situation");
                }
                this.add(command);
                logger.info(this.getCommands().toString());
                Command commandNew = this.createCommandOne(command);
                this.change(false);
                return commandNew.execute(model);
            } else {
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else {
                this.change(true);
                this.add(command);
                logger.info(this.getCommands().toString());
                return new CommandResult("Would you like to add this as a Inventory?");
        }
    }

    public AddInventoryCommand createCommandOne(Command command) throws ParseException {
        try {
            Index index = new Index(((DoneTaskCommand) commands.get(0)).getIndex());
            logger.info("Creating Inventory for done-task");
            Task taskToAdd = model.getFilteredTasksList().get(index.getZeroBased());
            Name name = taskToAdd.getName();

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
