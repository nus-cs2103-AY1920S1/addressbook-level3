package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddAutoExpenseCommand;
import seedu.address.logic.commands.AddBudgetCommand;
import seedu.address.logic.commands.AddCategoryCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BudgetListCommand;
import seedu.address.logic.commands.ChangeFontCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAutoExpenseCommand;
import seedu.address.logic.commands.DeleteBudgetCommand;
import seedu.address.logic.commands.DeleteCategoryCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteWishCommand;
import seedu.address.logic.commands.EditAutoExpenseCommand;
import seedu.address.logic.commands.EditBudgetCommand;
import seedu.address.logic.commands.EditCategoryCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditWishCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindBudgetCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindWishCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCategoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListFontCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.StatisticsCommand;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.SwitchStatisticsCommand;
import seedu.address.logic.commands.TogglePanelCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.WishListCommand;
import seedu.address.logic.commands.conditioncommands.AddClassConditionCommand;
import seedu.address.logic.commands.conditioncommands.AddDateConditionCommand;
import seedu.address.logic.commands.conditioncommands.AddKeyWordsConditionCommand;
import seedu.address.logic.commands.conditioncommands.AddQuotaConditionCommand;
import seedu.address.logic.commands.conditioncommands.AddTagsConditionCommand;
import seedu.address.logic.commands.conditioncommands.DeleteConditionCommand;
import seedu.address.logic.commands.conditioncommands.ReplaceConditionCommand;
import seedu.address.logic.commands.conditioncommands.ShowConditionListCommand;
import seedu.address.logic.commands.remindercommands.AddConditionToReminderCommand;
import seedu.address.logic.commands.remindercommands.AddReminderCommand;
import seedu.address.logic.commands.remindercommands.DeleteReminderCommand;
import seedu.address.logic.commands.remindercommands.EditReminderCommand;
import seedu.address.logic.parser.conditioncommandparsers.AddClassConditionCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.AddDateConditionCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.AddKeyWordsConditionCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.AddQuotaConditionCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.AddTagsConditionCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.DeleteConditionCommandParser;
import seedu.address.logic.parser.conditioncommandparsers.ReplaceConditionCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.remindercommandparsers.AddConditionToReminderCommandParser;
import seedu.address.logic.parser.remindercommandparsers.AddReminderCommandParser;
import seedu.address.logic.parser.remindercommandparsers.DeleteReminderCommandParser;
import seedu.address.logic.parser.remindercommandparsers.EditReminderCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

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

        case AddCategoryCommand.COMMAND_WORD:
            return new AddCategoryCommandParser().parse(arguments);

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

        case ListCategoryCommand.COMMAND_WORD:
            return new ListCategoryCommand();

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

        case SwitchCommand.COMMAND_WORD:
            return new SwitchCommandParser().parse(arguments);

        case SwitchStatisticsCommand.COMMAND_WORD:
            return new SwitchStatisticsCommand();

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

        case AddClassConditionCommand.COMMAND_WORD:
            return new AddClassConditionCommandParser().parse(arguments);

        case AddDateConditionCommand.COMMAND_WORD:
            return new AddDateConditionCommandParser().parse(arguments);

        case AddKeyWordsConditionCommand.COMMAND_WORD:
            return new AddKeyWordsConditionCommandParser().parse(arguments);

        case AddQuotaConditionCommand.COMMAND_WORD:
            return new AddQuotaConditionCommandParser().parse(arguments);

        case AddTagsConditionCommand.COMMAND_WORD:
            return new AddTagsConditionCommandParser().parse(arguments);

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

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommandParser().parse(arguments);

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

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
