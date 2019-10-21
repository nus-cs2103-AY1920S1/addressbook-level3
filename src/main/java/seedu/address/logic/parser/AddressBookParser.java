package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEarningsCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.commands.UnknownCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commands.CommandObject;


/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private HashMap<String, String> commandList;

    public AddressBookParser(ObservableList<CommandObject> commands) {
        this.commandList = new HashMap<>();
        this.initialiseBasicCommands();
        for (int i = 0; i < commands.size(); i++) {
            this.commandList.put(commands.get(i).getCommandWord().word, commands.get(i).getCommandAction().action);
        }
    }

    public HashMap<String, String> getCommandList() {
        return commandList;
    }

    public void setCommandList(HashMap<String, String> commandList) {
        this.commandList = commandList;
    }

    private void initialiseBasicCommands() {
        this.commandList.put("add", "add");
        this.commandList.put("edit", "edit");
        this.commandList.put("clear", "clear");
        this.commandList.put("delete", "delete");
        this.commandList.put("list", "list");
        this.commandList.put("find", "find");
        this.commandList.put("help", "help");
        this.commandList.put("exit", "exit");
        this.commandList.put("addEarnings", "addEarnings");
    }

    public Command checkCommand(String userInput, String prevUnknownCommand) {
        if (this.commandList.containsKey(userInput)) {
            this.commandList.put(prevUnknownCommand, userInput);
            return new NewCommand(userInput);
        } else {
            return new UnknownCommand(userInput);
        }
    }
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }


        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (commandList.containsKey(commandWord)) {
            switch (commandList.get(commandWord)) {
            case AddCommand.COMMAND_WORD:
                return new AddCommandParser().parse(arguments);

            case EditCommand.COMMAND_WORD:
                return new EditCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);

            case ClearCommand.COMMAND_WORD:
                this.commandList.clear();
                this.initialiseBasicCommands();
                return new ClearCommand();

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);

            case ListCommand.COMMAND_WORD:
                return new ListCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case AddEarningsCommand.COMMAND_WORD:
                return new AddEarningsCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else {
            return new UnknownCommand(commandWord);
        }

    }

}
