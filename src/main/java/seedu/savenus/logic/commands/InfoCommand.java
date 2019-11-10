package seedu.savenus.logic.commands;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;

//@@author robytanama
/**
 * Displays information on a particular command.
 */
public class InfoCommand extends Command {

    public static final String COMMAND_WORD = "info";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": This commands allows you to obtain more information about a particular command.\n"
            + "Example: " + COMMAND_WORD + " edit";

    public static final String COMMAND_INDICATOR = "Opening information window on: ";

    public static final String ADD_INFO = COMMAND_INDICATOR + AddCommand.COMMAND_WORD;

    public static final String ALIAS_INFO = COMMAND_INDICATOR + AliasCommand.COMMAND_WORD;

    public static final String AUTO_SORT_INFO = COMMAND_INDICATOR + AutoSortCommand.COMMAND_WORD;

    public static final String BUDGET_INFO = COMMAND_INDICATOR + BudgetCommand.COMMAND_WORD;

    public static final String BUY_INFO = COMMAND_INDICATOR + BuyCommand.COMMAND_WORD;

    public static final String CLEAR_INFO = COMMAND_INDICATOR + ClearCommand.COMMAND_WORD;

    public static final String CUSTOM_SORT_INFO = COMMAND_INDICATOR + CustomSortCommand.COMMAND_WORD;

    public static final String DEFAULT_INFO = COMMAND_INDICATOR + DefaultCommand.COMMAND_WORD;

    public static final String DELETE_INFO = COMMAND_INDICATOR + DeleteCommand.COMMAND_WORD;

    public static final String DISLIKE_INFO = COMMAND_INDICATOR + DislikeCommand.COMMAND_WORD;

    public static final String EDIT_INFO = COMMAND_INDICATOR + EditCommand.COMMAND_WORD;

    public static final String EXIT_INFO = COMMAND_INDICATOR + ExitCommand.COMMAND_WORD;

    public static final String FILTER_INFO = COMMAND_INDICATOR + FilterCommand.COMMAND_WORD;

    public static final String FIND_INFO = COMMAND_INDICATOR + FindCommand.COMMAND_WORD;

    public static final String HELP_INFO = COMMAND_INDICATOR + HelpCommand.COMMAND_WORD;

    public static final String HISTORY_INFO = COMMAND_INDICATOR + HistoryCommand.COMMAND_WORD;

    public static final String INFO_INFO = COMMAND_INDICATOR + InfoCommand.COMMAND_WORD;

    public static final String LIKE_INFO = COMMAND_INDICATOR + LikeCommand.COMMAND_WORD;

    public static final String LIST_INFO = COMMAND_INDICATOR + ListCommand.COMMAND_WORD;

    public static final String MAKE_SORT_INFO = COMMAND_INDICATOR + MakeSortCommand.COMMAND_WORD;

    public static final String RECOMMEND_INFO = COMMAND_INDICATOR + RecommendCommand.COMMAND_WORD;

    public static final String REMOVEDISLIKE_INFO = COMMAND_INDICATOR + RemoveDislikeCommand.COMMAND_WORD;

    public static final String REMOVELIKE_INFO = COMMAND_INDICATOR + RemoveLikeCommand.COMMAND_WORD;

    public static final String SAVE_INFO = COMMAND_INDICATOR + SaveCommand.COMMAND_WORD;

    public static final String SORT_INFO = COMMAND_INDICATOR + SortCommand.COMMAND_WORD;

    public static final String THEME_INFO = COMMAND_INDICATOR + ThemeCommand.COMMAND_WORD;

    public static final String TOP_UP_INFO = COMMAND_INDICATOR + TopUpCommand.COMMAND_WORD;

    public static final String VIEW_SORT_INFO = COMMAND_INDICATOR + ViewSortCommand.COMMAND_WORD;

    public static final String WITHDRAW_INFO = COMMAND_INDICATOR + WithdrawCommand.COMMAND_WORD;

    public static final String SHOW_INFO = COMMAND_INDICATOR + ShowCommand.COMMAND_WORD;

    public static final String INVALID_COMMAND_ENTERED_MESSAGE = "Sorry, no information for such command exists!";

    public static final String MULTIPLE_COMMAND_ENTERED_MESSAGE =
            "This app can only display the information of one command!";

    private String input;

    public InfoCommand(String command) {
        input = command;
    }

    public String getInput() {
        return input;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        input = input.toLowerCase();
        switch(input) {
        case AddCommand.COMMAND_WORD :
            return new CommandResult(ADD_INFO);
        case AliasCommand.COMMAND_WORD :
            return new CommandResult(ALIAS_INFO);
        case AutoSortCommand.COMMAND_WORD :
            return new CommandResult(AUTO_SORT_INFO);
        case BudgetCommand.COMMAND_WORD :
            return new CommandResult(BUDGET_INFO);
        case BuyCommand.COMMAND_WORD :
            return new CommandResult(BUY_INFO);
        case ClearCommand.COMMAND_WORD :
            return new CommandResult(CLEAR_INFO);
        case CustomSortCommand.COMMAND_WORD :
            return new CommandResult(CUSTOM_SORT_INFO);
        case DefaultCommand.COMMAND_WORD :
            return new CommandResult(DEFAULT_INFO);
        case DeleteCommand.COMMAND_WORD :
            return new CommandResult(DELETE_INFO);
        case DislikeCommand.COMMAND_WORD :
            return new CommandResult(DISLIKE_INFO);
        case EditCommand.COMMAND_WORD :
            return new CommandResult(EDIT_INFO);
        case ExitCommand.COMMAND_WORD :
            return new CommandResult(EXIT_INFO);
        case FilterCommand.COMMAND_WORD:
            return new CommandResult(FILTER_INFO);
        case FindCommand.COMMAND_WORD :
            return new CommandResult(FIND_INFO);
        case HelpCommand.COMMAND_WORD :
            return new CommandResult(HELP_INFO);
        case HistoryCommand.COMMAND_WORD :
            return new CommandResult(HISTORY_INFO);
        case InfoCommand.COMMAND_WORD :
            return new CommandResult(INFO_INFO);
        case LikeCommand.COMMAND_WORD :
            return new CommandResult(LIKE_INFO);
        case ListCommand.COMMAND_WORD :
            return new CommandResult(LIST_INFO);
        case MakeSortCommand.COMMAND_WORD :
            return new CommandResult(MAKE_SORT_INFO);
        case RecommendCommand.COMMAND_WORD :
            return new CommandResult(RECOMMEND_INFO);
        case RemoveDislikeCommand.COMMAND_WORD :
            return new CommandResult(REMOVEDISLIKE_INFO);
        case RemoveLikeCommand.COMMAND_WORD :
            return new CommandResult(REMOVELIKE_INFO);
        case SaveCommand.COMMAND_WORD :
            return new CommandResult(SAVE_INFO);
        case SortCommand.COMMAND_WORD :
            return new CommandResult(SORT_INFO);
        case ThemeCommand.COMMAND_WORD :
            return new CommandResult(THEME_INFO);
        case TopUpCommand.COMMAND_WORD :
            return new CommandResult(TOP_UP_INFO);
        case ViewSortCommand.COMMAND_WORD :
            return new CommandResult(VIEW_SORT_INFO);
        case WithdrawCommand.COMMAND_WORD :
            return new CommandResult(WITHDRAW_INFO);
        case ShowCommand.COMMAND_WORD :
            return new CommandResult(SHOW_INFO);
        default :
            throw new CommandException(INVALID_COMMAND_ENTERED_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InfoCommand // instanceof handles nulls
                && getInput().equals(((InfoCommand) other).getInput()));
    }
}
