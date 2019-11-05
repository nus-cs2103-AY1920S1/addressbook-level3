package seedu.address.logic.commands;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.logic.commands.util.EditDistanceComparator;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.AddressBookParser.BASIC_COMMAND_FORMAT;

/**
 * Implements one main function suggest: to return a suggestion string
 * to be displayed at the {@link seedu.address.ui.ResultDisplay} given user's
 * current text input for AutoSuggestion effect.
 */
public final class GuiltTripCommandSuggester {

    public static final int COMMAND_RECOMMENDATION_COUNT = 5;

    /**
     * Takes in textInput from the {@link seedu.address.ui.CommandBox} and returns
     * a {@link String} to be displayed in the {@link seedu.address.ui.ResultDisplay}.
     *
     * @param textInput String to be processed
     * @return Appropriate helping string.
     */
    public static String suggest(String textInput) {
        String commandWord = getCommandWord(textInput);

        if (commandIsIncomplete(commandWord)) {
            return getClosestCommands(commandWord, COMMAND_RECOMMENDATION_COUNT);
        } else {
            return getCommandHelpMessage(commandWord);
        }

        // ArgumentMultimap argumentMultimap = getArgumentMultimap(textInput);
    }

    private static String getCommandWord(String textInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(textInput.trim());
        if (!matcher.matches()) {
            throw new ParserException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        return commandWord;
    }

    private static ArgumentMultimap getArgumentMultimap(String textInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(textInput.trim());
        if (!matcher.matches()) {
            throw new ParserException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String arguments = matcher.group("arguments");
        return arguments;
    }

    private static boolean commandIsIncomplete(String commandWord) {
    }

    // TODO: Save a set of all commands. Either at model or addressbook
    private static String getClosestCommands(String commandWord, int count) {
        Queue<String> commandsHeap = new PriorityQueue<>(new EditDistanceComparator(commandWord));
        commandsHeap.addAll(COMMANDS_SET);

        return IntStream.rangeClosed(1, 5).mapToObj(i -> commandsHeap.poll()).collect(Collectors.joining("\n"));
    }

    public holder

    {

    public Command parseCommand(String userInput) throws ParseException, IllegalArgumentException {
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
