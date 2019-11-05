package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.addcommands.AddAutoExpenseCommand;
import seedu.address.logic.commands.addcommands.AddBudgetCommand;
import seedu.address.logic.commands.addcommands.AddCategoryCommand;
import seedu.address.logic.commands.addcommands.AddCommand;
import seedu.address.logic.commands.BudgetListCommand;
import seedu.address.logic.commands.addcommands.AddExpenseCommand;
import seedu.address.logic.commands.addcommands.AddIncomeCommand;
import seedu.address.logic.commands.addcommands.AddWishCommand;
import seedu.address.logic.commands.uicommands.ChangeFontCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.deletecommands.DeleteAutoExpenseCommand;
import seedu.address.logic.commands.deletecommands.DeleteBudgetCommand;
import seedu.address.logic.commands.deletecommands.DeleteCategoryCommand;
import seedu.address.logic.commands.deletecommands.DeleteCommand;
import seedu.address.logic.commands.deletecommands.DeleteWishCommand;
import seedu.address.logic.commands.editcommands.EditAutoExpenseCommand;
import seedu.address.logic.commands.editcommands.EditBudgetCommand;
import seedu.address.logic.commands.editcommands.EditCategoryCommand;
import seedu.address.logic.commands.editcommands.EditCommand;
import seedu.address.logic.commands.editcommands.EditWishCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.findcommands.FindBudgetCommand;
import seedu.address.logic.commands.findcommands.FindCommand;
import seedu.address.logic.commands.findcommands.FindWishCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCategoriesCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.uicommands.ListFontCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.uicommands.SetDarkThemeCommand;
import seedu.address.logic.commands.uicommands.SetLightThemeCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.uicommands.TogglePanelCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.WishListCommand;
import seedu.address.logic.commands.conditioncommands.AddClassConditionCommand;
import seedu.address.logic.commands.conditioncommands.AddDateConditionCommand;
import seedu.address.logic.commands.conditioncommands.AddHasKeyWordConditionCommand;
import seedu.address.logic.commands.conditioncommands.AddQuotaConditionCommand;
import seedu.address.logic.commands.conditioncommands.AddTagsConditionCommand;
import seedu.address.logic.commands.conditioncommands.DeleteConditionCommand;
import seedu.address.logic.commands.conditioncommands.ReplaceConditionCommand;
import seedu.address.logic.commands.conditioncommands.ShowConditionListCommand;
import seedu.address.logic.commands.remindercommands.AddConditionToReminderCommand;
import seedu.address.logic.commands.remindercommands.AddReminderCommand;
import seedu.address.logic.commands.remindercommands.DeleteReminderCommand;
import seedu.address.logic.commands.remindercommands.EditReminderCommand;
import seedu.address.logic.commands.remindercommands.ListActiveRemindersCommand;
import seedu.address.logic.commands.remindercommands.ListAllRemindersCommand;
import seedu.address.logic.commands.remindercommands.RemoveConditionFromReminderCommand;
import seedu.address.logic.commands.statisticscommands.ViewBarChartCommand;
import seedu.address.logic.commands.statisticscommands.ViewEntryCommand;
import seedu.address.logic.commands.statisticscommands.ViewPieChartCommand;
import seedu.address.logic.commands.statisticscommands.ViewTableCommand;
import seedu.address.logic.parser.addcommandparsers.AddAutoExpenseCommandParser;
import seedu.address.logic.parser.addcommandparsers.AddBudgetCommandParser;
import seedu.address.logic.parser.addcommandparsers.AddCategoryCommandParser;
import seedu.address.logic.parser.addcommandparsers.AddCommandParser;
import seedu.address.logic.parser.addcommandparsers.AddExpenseCommandParser;
import seedu.address.logic.parser.addcommandparsers.AddIncomeCommandParser;
import seedu.address.logic.parser.addcommandparsers.AddWishCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.AddClassConditionCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.AddDateConditionCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.AddHasKeyWordConditionCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.AddHasTagConditionCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.AddQuotaConditionCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.DeleteConditionCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.ReplaceConditionCommandParser;
import seedu.address.logic.parser.deletecommandparsers.DeleteAutoExpenseCommandParser;
import seedu.address.logic.parser.deletecommandparsers.DeleteBudgetCommandParser;
import seedu.address.logic.parser.deletecommandparsers.DeleteCategoryCommandParser;
import seedu.address.logic.parser.deletecommandparsers.DeleteCommandParser;
import seedu.address.logic.parser.deletecommandparsers.DeleteWishCommandParser;
import seedu.address.logic.parser.editcommandparsers.EditAutoExpenseCommandParser;
import seedu.address.logic.parser.editcommandparsers.EditBudgetCommandParser;
import seedu.address.logic.parser.editcommandparsers.EditCategoryCommandParser;
import seedu.address.logic.parser.editcommandparsers.EditCommandParser;
import seedu.address.logic.parser.editcommandparsers.EditWishCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.findcommandparsers.FindBudgetCommandParser;
import seedu.address.logic.parser.findcommandparsers.FindCommandParser;
import seedu.address.logic.parser.findcommandparsers.FindWishCommandParser;
import seedu.address.logic.parser.remindercommandparsers.AddConditionToReminderCommandParser;
import seedu.address.logic.parser.remindercommandparsers.AddReminderCommandParser;
import seedu.address.logic.parser.remindercommandparsers.DeleteReminderCommandParser;
import seedu.address.logic.parser.remindercommandparsers.EditReminderCommandParser;
import seedu.address.logic.parser.remindercommandparsers.RemoveConditionFromReminderCommandParser;
import seedu.address.logic.parser.statisticscommandparsers.ViewBarChartCommandParser;
import seedu.address.logic.parser.statisticscommandparsers.ViewPieChartCommandParser;
import seedu.address.logic.parser.statisticscommandparsers.ViewTableCommandParser;

/**
 * Parses user input.
 */
public class GuiltTripParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput
     *                      full user input string
     * @return the command based on the user input
     * @throws ParseException
     *                            if the user input does not conform the expected
     *                            format
     */
    public Command parseCommand(String userInput) throws ParseException, IllegalArgumentException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddBudgetCommand.COMMAND_WORD:
            return new AddBudgetCommandParser().parse(arguments);

        case AddExpenseCommand.COMMAND_WORD:
            return new AddExpenseCommandParser().parse(arguments);

        case AddCategoryCommand.COMMAND_WORD:
            return new AddCategoryCommandParser().parse(arguments);

        case AddIncomeCommand.COMMAND_WORD:
            return new AddIncomeCommandParser().parse(arguments);

        case AddWishCommand.COMMAND_WORD:
            return new AddWishCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditCategoryCommand.COMMAND_WORD:
            return new EditCategoryCommandParser().parse(arguments);

        case EditWishCommand.COMMAND_WORD:
            return new EditWishCommandParser().parse(arguments);

        case EditBudgetCommand.COMMAND_WORD:
            return new EditBudgetCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteCategoryCommand.COMMAND_WORD:
            return new DeleteCategoryCommandParser().parse(arguments);

        case DeleteWishCommand.COMMAND_WORD:
            return new DeleteWishCommandParser().parse(arguments);

        case DeleteBudgetCommand.COMMAND_WORD:
            return new DeleteBudgetCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindWishCommand.COMMAND_WORD:
            return new FindWishCommandParser().parse(arguments);

        case FindBudgetCommand.COMMAND_WORD:
            return new FindBudgetCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListCategoriesCommand.COMMAND_WORD:
            return new ListCategoriesCommand();

        case ListFontCommand.COMMAND_WORD:
            return new ListFontCommand();

        case WishListCommand.COMMAND_WORD:
            return new WishListCommand();

        case BudgetListCommand.COMMAND_WORD:
            return new BudgetListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddReminderCommand.COMMAND_WORD:
            return new AddReminderCommandParser().parse(arguments);

        case EditReminderCommand.COMMAND_WORD:
            return new EditReminderCommandParser().parse(arguments);

        case DeleteReminderCommand.COMMAND_WORD:
            return new DeleteReminderCommandParser().parse(arguments);

        case AddConditionToReminderCommand.COMMAND_WORD:
            return new AddConditionToReminderCommandParser().parse(arguments);

        case RemoveConditionFromReminderCommand.COMMAND_WORD:
            return new RemoveConditionFromReminderCommandParser().parse(arguments);

        case ListAllRemindersCommand.COMMAND_WORD:
            return new ListAllRemindersCommand();

        case ListActiveRemindersCommand.COMMAND_WORD:
            return new ListActiveRemindersCommand();

        case AddClassConditionCommand.COMMAND_WORD:
            return new AddClassConditionCommandParser().parse(arguments);

        case AddDateConditionCommand.COMMAND_WORD:
            return new AddDateConditionCommandParser().parse(arguments);

        case AddHasKeyWordConditionCommand.COMMAND_WORD:
            return new AddHasKeyWordConditionCommandParser().parse(arguments);

        case AddQuotaConditionCommand.COMMAND_WORD:
            return new AddQuotaConditionCommandParser().parse(arguments);

        case AddTagsConditionCommand.COMMAND_WORD:
            return new AddHasTagConditionCommandParser().parse(arguments);

        case DeleteConditionCommand.COMMAND_WORD:
            return new DeleteConditionCommandParser().parse(arguments);

        case ReplaceConditionCommand.COMMAND_WORD:
            return new ReplaceConditionCommandParser().parse(arguments);

        case ShowConditionListCommand.COMMAND_WORD:
            return new ShowConditionListCommand();

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
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

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
