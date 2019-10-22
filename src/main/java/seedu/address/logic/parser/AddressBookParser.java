package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.*;
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

    private static TreeMap<String, String> commandList;

    public AddressBookParser(ObservableList<CommandObject> commands) {
        AddressBookParser.commandList = new TreeMap<>();
        initialiseBasicCommands();
        for (int i = 0; i < commands.size(); i++) {
            AddressBookParser.commandList.put(commands.get(i).getCommandWord().word, commands.get(i).getCommandAction().action);
        }
    }

    //add a delete command to delete custom command

    public static TreeMap<String, String> getCommandList() {
        return AddressBookParser.commandList;
    }


    private void initialiseBasicCommands() {
        AddressBookParser.commandList.put("add", "add");
        AddressBookParser.commandList.put("edit", "edit");
        AddressBookParser.commandList.put("clear", "clear");
        AddressBookParser.commandList.put("delete", "delete");
        AddressBookParser.commandList.put("list", "list");
        AddressBookParser.commandList.put("find", "find");
        AddressBookParser.commandList.put("help", "help");
        AddressBookParser.commandList.put("exit", "exit");
        AddressBookParser.commandList.put("addEarnings", "addEarnings");
        AddressBookParser.commandList.put("deleteCustomCommand", "deleteCustomCommand");
    }

    public Command checkCommand(String userInput, String prevUnknownCommand) {
        if (AddressBookParser.commandList.containsKey(userInput)) {
            AddressBookParser.commandList.put(prevUnknownCommand, AddressBookParser.commandList.get(userInput));
            return new NewCommand(AddressBookParser.commandList.get(userInput), prevUnknownCommand);
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
                AddressBookParser.commandList.clear();
                initialiseBasicCommands();
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

            case DeleteCustomCommand.COMMAND_WORD:
                return new DeleteCustomCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else {
            return new UnknownCommand(commandWord);
        }

    }

}
