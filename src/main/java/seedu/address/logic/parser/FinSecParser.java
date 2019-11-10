package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_UNNECESSARY_BUDGET_PARAMETERS;
import static seedu.address.logic.commands.HelpCommand.MESSAGE_USAGE;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.AddClaimCommand;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddIncomeCommand;
import seedu.address.logic.commands.ApproveClaimCommand;
import seedu.address.logic.commands.BudgetCommand;
import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CreateShortCutCommand;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.commands.DeleteIncomeCommand;
import seedu.address.logic.commands.DeleteShortcutCommand;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.EditIncomeCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GotoCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.NoShortCutCommand;
import seedu.address.logic.commands.RejectClaimCommand;
import seedu.address.logic.commands.ShortCutRequestCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortReverseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commanditem.CommandItem;

/**
 * Parses user input.
 */
public class FinSecParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * TreeMap of commands.
     */
    private static TreeMap<String, String> commandList;

    private static HashSet<CommandItem> shortcuts;


    public FinSecParser(ObservableList<CommandItem> commands) {
        FinSecParser.commandList = new TreeMap<>();
        FinSecParser.shortcuts = new HashSet<>();
        initialiseDefaultCommands();
        for (CommandItem commandItem : commands) {
            FinSecParser.commandList.put(commandItem.getCommandWord().word, commandItem.getCommandTask().task);
            if (!(commandItem.getCommandWord().word == commandItem.getCommandTask().task)) {
                shortcuts.add(commandItem);
            }
        }
    }

    public FinSecParser() {
        FinSecParser.commandList = new TreeMap<>();
        FinSecParser.shortcuts = new HashSet<>();
        initialiseDefaultCommands();
    }

    //@@author{joshuaseetss}
    public static TreeMap<String, String> getCommandList() {
        return FinSecParser.commandList;
    }

    //@@author{joshuaseetss}
    public static HashSet<CommandItem> getShortcutList() {
        return FinSecParser.shortcuts;
    }


    //@@author{joshuaseetss}
    /**
     * Used to check if the task the user wants to make a shortcut to exists.
     * Returns a {@code CreateShortCutCommand} if the task exists or an {@code ShortCutRequestCommand} if it does not.
     */
    public Command checkCommand(String currentInput, String prevInput) {
        if (FinSecParser.commandList.containsValue(currentInput)) {
            FinSecParser.commandList.put(prevInput, FinSecParser.commandList.get(currentInput));
            return new CreateShortCutCommand(FinSecParser.commandList.get(currentInput), prevInput);
        } else {
            return new ShortCutRequestCommand(currentInput);
        }
    }


    //@@author{joshuaseetss}
    /**
     * Initialises FinSec's default commands into the treemap of commands.
     */
    private void initialiseDefaultCommands() {
        FinSecParser.commandList.put(AddContactCommand.COMMAND_WORD, AddContactCommand.COMMAND_WORD);
        FinSecParser.commandList.put(EditContactCommand.COMMAND_WORD, EditContactCommand.COMMAND_WORD);
        FinSecParser.commandList.put(DeleteContactCommand.COMMAND_WORD, DeleteContactCommand.COMMAND_WORD);
        FinSecParser.commandList.put(AddClaimCommand.COMMAND_WORD, AddClaimCommand.COMMAND_WORD);
        FinSecParser.commandList.put(AddIncomeCommand.COMMAND_WORD, AddIncomeCommand.COMMAND_WORD);
        FinSecParser.commandList.put(EditIncomeCommand.COMMAND_WORD, EditIncomeCommand.COMMAND_WORD);
        FinSecParser.commandList.put(DeleteIncomeCommand.COMMAND_WORD, DeleteIncomeCommand.COMMAND_WORD);
        FinSecParser.commandList.put(ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD);
        FinSecParser.commandList.put(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD);
        FinSecParser.commandList.put(ApproveClaimCommand.COMMAND_WORD, ApproveClaimCommand.COMMAND_WORD);
        FinSecParser.commandList.put(BudgetCommand.COMMAND_WORD, BudgetCommand.COMMAND_WORD);
        FinSecParser.commandList.put(SortCommand.COMMAND_WORD, SortCommand.COMMAND_WORD);
        FinSecParser.commandList.put(SortReverseCommand.COMMAND_WORD, SortReverseCommand.COMMAND_WORD);
        FinSecParser.commandList.put(CheckCommand.COMMAND_WORD, CheckCommand.COMMAND_WORD);
        FinSecParser.commandList.put(ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD);
        FinSecParser.commandList.put(GotoCommand.COMMAND_WORD, GotoCommand.COMMAND_WORD);
        FinSecParser.commandList.put(RejectClaimCommand.COMMAND_WORD, RejectClaimCommand.COMMAND_WORD);
        FinSecParser.commandList.put(DeleteShortcutCommand.COMMAND_WORD, DeleteShortcutCommand.COMMAND_WORD);
        FinSecParser.commandList.put(NoShortCutCommand.COMMAND_WORD, NoShortCutCommand.COMMAND_WORD);
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (commandList.containsKey(commandWord)) {
            switch (commandList.get(commandWord)) {

            case AddContactCommand.COMMAND_WORD:
                return new AddContactCommandParser().parse(arguments);

            case EditContactCommand.COMMAND_WORD:
                return new EditContactCommandParser().parse(arguments);

            case AddClaimCommand.COMMAND_WORD:
                return new AddClaimCommandParser().parse(arguments);

            case AddIncomeCommand.COMMAND_WORD:
                return new AddIncomeCommandParser().parse(arguments);

            case EditIncomeCommand.COMMAND_WORD:
                return new EditIncomeCommandParser().parse(arguments);

            case DeleteContactCommand.COMMAND_WORD:
                return new DeleteContactCommandParser().parse(arguments);

            case DeleteIncomeCommand.COMMAND_WORD:
                return new DeleteIncomeCommandParser().parse(arguments);

            case DeleteShortcutCommand.COMMAND_WORD:
                return new DeleteShortcutParser().parse(arguments);

                //@@author{lawncegoh}
            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

                //@@author{lawncegoh}
            case GotoCommand.COMMAND_WORD:
                return new GotoCommandParser().parse(arguments);

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommandParser().parse(arguments);

                //@@author{lawncegoh}
            case CheckCommand.COMMAND_WORD:
                return new CheckCommandParser().parse(arguments);

                //@@author{lawncegoh}
            case SortCommand.COMMAND_WORD:
                return new SortCommandParser().parse(arguments);

                //@@author{lawncegoh}
            case SortReverseCommand.COMMAND_WORD:
                return new SortReverseCommandParser().parse(arguments);

                //@@author{lawncegoh}
            case ApproveClaimCommand.COMMAND_WORD:
                return new ApproveClaimCommandParser().parse(arguments);

            case RejectClaimCommand.COMMAND_WORD:
                return new RejectClaimCommandParser().parse(arguments);

            case BudgetCommand.COMMAND_WORD:
                if (arguments.isEmpty()) {
                    return new BudgetCommand();
                } else {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_UNNECESSARY_BUDGET_PARAMETERS));
                }

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else {
            //@@author{joshuaseetss}
            return new ShortCutRequestCommand(commandWord);
        }
    }

}
