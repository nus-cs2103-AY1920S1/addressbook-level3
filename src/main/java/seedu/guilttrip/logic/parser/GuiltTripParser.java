package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.guilttrip.logic.parser.ParserUtil.mapOfMessageUsages;
import static seedu.guilttrip.logic.parser.ParserUtil.mapOfOneLinerDescs;
import static seedu.guilttrip.logic.parser.ParserUtil.setOfCommandWords;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.guilttrip.logic.commands.ClearCommand;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.ExitCommand;
import seedu.guilttrip.logic.commands.HelpCommand;
import seedu.guilttrip.logic.commands.HistoryCommand;
import seedu.guilttrip.logic.commands.ListBudgetCommand;
import seedu.guilttrip.logic.commands.ListCategoriesCommand;
import seedu.guilttrip.logic.commands.ListCommand;
import seedu.guilttrip.logic.commands.ListWishCommand;
import seedu.guilttrip.logic.commands.RedoCommand;
import seedu.guilttrip.logic.commands.SetCurrDateCommand;
import seedu.guilttrip.logic.commands.UndoCommand;
import seedu.guilttrip.logic.commands.addcommands.AddAutoExpenseCommand;
import seedu.guilttrip.logic.commands.addcommands.AddBudgetCommand;
import seedu.guilttrip.logic.commands.addcommands.AddCategoryCommand;
import seedu.guilttrip.logic.commands.addcommands.AddExpenseCommand;
import seedu.guilttrip.logic.commands.addcommands.AddIncomeCommand;
import seedu.guilttrip.logic.commands.addcommands.AddWishCommand;
import seedu.guilttrip.logic.commands.deletecommands.DeleteAutoExpenseCommand;
import seedu.guilttrip.logic.commands.deletecommands.DeleteBudgetCommand;
import seedu.guilttrip.logic.commands.deletecommands.DeleteCategoryCommand;
import seedu.guilttrip.logic.commands.deletecommands.DeleteExpenseCommand;
import seedu.guilttrip.logic.commands.deletecommands.DeleteIncomeCommand;
import seedu.guilttrip.logic.commands.deletecommands.DeleteWishCommand;
import seedu.guilttrip.logic.commands.editcommands.EditAutoExpenseCommand;
import seedu.guilttrip.logic.commands.editcommands.EditBudgetCommand;
import seedu.guilttrip.logic.commands.editcommands.EditCategoryCommand;
import seedu.guilttrip.logic.commands.editcommands.EditExpenseCommand;
import seedu.guilttrip.logic.commands.editcommands.EditIncomeCommand;
import seedu.guilttrip.logic.commands.editcommands.EditWishCommand;
import seedu.guilttrip.logic.commands.findcommands.FindAutoExpenseCommand;
import seedu.guilttrip.logic.commands.findcommands.FindBudgetCommand;
import seedu.guilttrip.logic.commands.findcommands.FindExpenseCommand;
import seedu.guilttrip.logic.commands.findcommands.FindIncomeCommand;
import seedu.guilttrip.logic.commands.findcommands.FindWishCommand;
import seedu.guilttrip.logic.commands.remindercommands.AddGeneralReminderCommand;
import seedu.guilttrip.logic.commands.remindercommands.DeleteReminderCommand;
import seedu.guilttrip.logic.commands.remindercommands.DisplayPopupCommand;
import seedu.guilttrip.logic.commands.remindercommands.EditReminderCommand;
import seedu.guilttrip.logic.commands.remindercommands.ListAllRemindersCommand;
import seedu.guilttrip.logic.commands.remindercommands.RemoveConditionFromReminderCommand;
import seedu.guilttrip.logic.commands.remindercommands.SelectReminderCommand;
import seedu.guilttrip.logic.commands.remindercommands.SetReminderCommand;
import seedu.guilttrip.logic.commands.remindercommands.TogglePopUpCommand;
import seedu.guilttrip.logic.commands.sortcommands.SortAutoExpenseCommand;
import seedu.guilttrip.logic.commands.sortcommands.SortBudgetCommand;
import seedu.guilttrip.logic.commands.sortcommands.SortExpenseCommand;
import seedu.guilttrip.logic.commands.sortcommands.SortIncomeCommand;
import seedu.guilttrip.logic.commands.sortcommands.SortWishCommand;
import seedu.guilttrip.logic.commands.statisticscommands.ViewBarChartCommand;
import seedu.guilttrip.logic.commands.statisticscommands.ViewEntryCommand;
import seedu.guilttrip.logic.commands.statisticscommands.ViewPieChartCommand;
import seedu.guilttrip.logic.commands.statisticscommands.ViewTableCommand;
import seedu.guilttrip.logic.commands.uicommands.ChangeFontCommand;
import seedu.guilttrip.logic.commands.uicommands.SetDarkThemeCommand;
import seedu.guilttrip.logic.commands.uicommands.SetLightThemeCommand;
import seedu.guilttrip.logic.commands.uicommands.TogglePanelCommand;
import seedu.guilttrip.logic.parser.addcommandparsers.AddAutoExpenseCommandParser;
import seedu.guilttrip.logic.parser.addcommandparsers.AddBudgetCommandParser;
import seedu.guilttrip.logic.parser.addcommandparsers.AddCategoryCommandParser;
import seedu.guilttrip.logic.parser.addcommandparsers.AddExpenseCommandParser;
import seedu.guilttrip.logic.parser.addcommandparsers.AddIncomeCommandParser;
import seedu.guilttrip.logic.parser.addcommandparsers.AddWishCommandParser;
import seedu.guilttrip.logic.parser.deletecommandparsers.DeleteAutoExpenseCommandParser;
import seedu.guilttrip.logic.parser.deletecommandparsers.DeleteBudgetCommandParser;
import seedu.guilttrip.logic.parser.deletecommandparsers.DeleteCategoryCommandParser;
import seedu.guilttrip.logic.parser.deletecommandparsers.DeleteExpenseCommandParser;
import seedu.guilttrip.logic.parser.deletecommandparsers.DeleteIncomeCommandParser;
import seedu.guilttrip.logic.parser.deletecommandparsers.DeleteWishCommandParser;
import seedu.guilttrip.logic.parser.editcommandparsers.EditAutoExpenseCommandParser;
import seedu.guilttrip.logic.parser.editcommandparsers.EditBudgetCommandParser;
import seedu.guilttrip.logic.parser.editcommandparsers.EditCategoryCommandParser;
import seedu.guilttrip.logic.parser.editcommandparsers.EditExpenseCommandParser;
import seedu.guilttrip.logic.parser.editcommandparsers.EditIncomeCommandParser;
import seedu.guilttrip.logic.parser.editcommandparsers.EditWishCommandParser;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.logic.parser.findcommandparsers.FindAutoExpenseCommandParser;
import seedu.guilttrip.logic.parser.findcommandparsers.FindBudgetCommandParser;
import seedu.guilttrip.logic.parser.findcommandparsers.FindExpenseCommandParser;
import seedu.guilttrip.logic.parser.findcommandparsers.FindIncomeCommandParser;
import seedu.guilttrip.logic.parser.findcommandparsers.FindWishCommandParser;
import seedu.guilttrip.logic.parser.remindercommandparsers.AddGeneralReminderCommandParser;
import seedu.guilttrip.logic.parser.remindercommandparsers.DeleteReminderCommandParser;
import seedu.guilttrip.logic.parser.remindercommandparsers.EditReminderCommandParser;
import seedu.guilttrip.logic.parser.remindercommandparsers.RemoveConditionFromReminderCommandParser;
import seedu.guilttrip.logic.parser.remindercommandparsers.SelectReminderCommandParser;
import seedu.guilttrip.logic.parser.remindercommandparsers.SetReminderCommandParser;
import seedu.guilttrip.logic.parser.remindercommandparsers.TogglePopUpCommandParser;
import seedu.guilttrip.logic.parser.sortcommandparsers.SortAutoExpenseCommandParser;
import seedu.guilttrip.logic.parser.sortcommandparsers.SortBudgetCommandParser;
import seedu.guilttrip.logic.parser.sortcommandparsers.SortExpenseCommandParser;
import seedu.guilttrip.logic.parser.sortcommandparsers.SortIncomeCommandParser;
import seedu.guilttrip.logic.parser.sortcommandparsers.SortWishCommandParser;
import seedu.guilttrip.logic.parser.statscommandparsers.ViewBarChartCommandParser;
import seedu.guilttrip.logic.parser.statscommandparsers.ViewPieChartCommandParser;
import seedu.guilttrip.logic.parser.statscommandparsers.ViewTableCommandParser;
import seedu.guilttrip.logic.parser.uicommandparsers.ChangeFontCommandParser;
import seedu.guilttrip.logic.parser.uicommandparsers.TogglePanelCommandParser;

/**
 * Parses user input.
 */
public class GuiltTripParser {

    public static final Set<String> COMMANDS_SET = setOfCommandWords();
    public static final Map<String, String> ONE_LINER_DESC_MAP = mapOfOneLinerDescs();
    public static final Map<String, String> MESSAGE_USAGE_MAP = mapOfMessageUsages();
    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException, IllegalArgumentException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddExpenseCommand.COMMAND_WORD:
            // Fallthrough
        case AddExpenseCommand.COMMAND_WORD_SHORT:
            return new AddExpenseCommandParser().parse(arguments);
        case SetCurrDateCommand.COMMAND_WORD:
            return new SetCurrDateCommandParser().parse(arguments);
        case AddIncomeCommand.COMMAND_WORD:
            return new AddIncomeCommandParser().parse(arguments);

        case AddBudgetCommand.COMMAND_WORD:
            return new AddBudgetCommandParser().parse(arguments);

        case AddCategoryCommand.COMMAND_WORD:
            return new AddCategoryCommandParser().parse(arguments);

        case AddWishCommand.COMMAND_WORD:
            return new AddWishCommandParser().parse(arguments);

        case EditExpenseCommand.COMMAND_WORD:
            // Fallthrough
        case EditExpenseCommand.COMMAND_WORD_SHORT:
            return new EditExpenseCommandParser().parse(arguments);

        case EditIncomeCommand.COMMAND_WORD:
            return new EditIncomeCommandParser().parse(arguments);

        case EditCategoryCommand.COMMAND_WORD:
            return new EditCategoryCommandParser().parse(arguments);

        case EditWishCommand.COMMAND_WORD:
            return new EditWishCommandParser().parse(arguments);

        case EditBudgetCommand.COMMAND_WORD:
            return new EditBudgetCommandParser().parse(arguments);

        case DeleteExpenseCommand.COMMAND_WORD:
            // Fallthrough
        case DeleteExpenseCommand.COMMAND_WORD_SHORT:
            return new DeleteExpenseCommandParser().parse(arguments);

        case DeleteIncomeCommand.COMMAND_WORD:
            return new DeleteIncomeCommandParser().parse(arguments);

        case DeleteCategoryCommand.COMMAND_WORD:
            return new DeleteCategoryCommandParser().parse(arguments);

        case DeleteWishCommand.COMMAND_WORD:
            return new DeleteWishCommandParser().parse(arguments);

        case DeleteBudgetCommand.COMMAND_WORD:
            return new DeleteBudgetCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindExpenseCommand.COMMAND_WORD:
            return new FindExpenseCommandParser().parse(arguments);

        case FindAutoExpenseCommand.COMMAND_WORD:
            return new FindAutoExpenseCommandParser().parse(arguments);

        case FindIncomeCommand.COMMAND_WORD:
            return new FindIncomeCommandParser().parse(arguments);

        case FindWishCommand.COMMAND_WORD:
            return new FindWishCommandParser().parse(arguments);

        case FindBudgetCommand.COMMAND_WORD:
            return new FindBudgetCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListCategoriesCommand.COMMAND_WORD:
            return new ListCategoriesCommand();

        case ListWishCommand.COMMAND_WORD:
            return new ListWishCommand();

        case ListBudgetCommand.COMMAND_WORD:
            return new ListBudgetCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case SortExpenseCommand.COMMAND_WORD:
            return new SortExpenseCommandParser().parse(arguments);

        case SortIncomeCommand.COMMAND_WORD:
            return new SortIncomeCommandParser().parse(arguments);

        case SortBudgetCommand.COMMAND_WORD:
            return new SortBudgetCommandParser().parse(arguments);

        case SortAutoExpenseCommand.COMMAND_WORD:
            return new SortAutoExpenseCommandParser().parse(arguments);

        case SortWishCommand.COMMAND_WORD:
            return new SortWishCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SelectReminderCommand.COMMAND_WORD:
            return new SelectReminderCommandParser().parse(arguments);

        case TogglePopUpCommand.COMMAND_WORD:
            return new TogglePopUpCommandParser().parse(arguments);

        case DisplayPopupCommand.COMMAND_WORD:
            return new DisplayPopupCommand();

        case AddGeneralReminderCommand.COMMAND_WORD:
            return new AddGeneralReminderCommandParser().parse(arguments);

        case SetReminderCommand.COMMAND_WORD:
            return new SetReminderCommandParser().parse(arguments);

        case EditReminderCommand.COMMAND_WORD:
            return new EditReminderCommandParser().parse(arguments);

        case DeleteReminderCommand.COMMAND_WORD:
            return new DeleteReminderCommandParser().parse(arguments);

        case RemoveConditionFromReminderCommand.COMMAND_WORD:
            return new RemoveConditionFromReminderCommandParser().parse(arguments);

        case ListAllRemindersCommand.COMMAND_WORD:
            return new ListAllRemindersCommand();

        case AddAutoExpenseCommand.COMMAND_WORD:
            return new AddAutoExpenseCommandParser().parse(arguments);

        case EditAutoExpenseCommand.COMMAND_WORD:
            return new EditAutoExpenseCommandParser().parse(arguments);

        case DeleteAutoExpenseCommand.COMMAND_WORD:
            return new DeleteAutoExpenseCommandParser().parse(arguments);

        case ViewBarChartCommand.COMMAND_WORD:
            return new ViewBarChartCommandParser().parse(arguments);

        case ViewTableCommand.COMMAND_WORD:
            return new ViewTableCommandParser().parse(arguments);

        case ViewPieChartCommand.COMMAND_WORD:
            return new ViewPieChartCommandParser().parse(arguments);

        case ViewEntryCommand.COMMAND_WORD:
            return new ViewEntryCommand();

        case TogglePanelCommand.COMMAND_WORD:
            return new TogglePanelCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ChangeFontCommand.COMMAND_WORD:
            return new ChangeFontCommandParser().parse(arguments);

        case SetLightThemeCommand.COMMAND_WORD:
            return new SetLightThemeCommand();

        case SetDarkThemeCommand.COMMAND_WORD:
            return new SetDarkThemeCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
