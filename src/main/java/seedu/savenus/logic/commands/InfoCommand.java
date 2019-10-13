package seedu.savenus.logic.commands;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;

public class InfoCommand extends Command {

    public static final String COMMAND_WORD = "info";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": This commands allows you to obtain more information about a particular command.\n"
            + "Example: " + COMMAND_WORD + " edit";

    public static final String COMMAND_INDICATOR = "Opening information window on: ";

    public static final String ADD_INFO = COMMAND_INDICATOR + AddCommand.COMMAND_WORD;

    public static final String BUDGET_INFO = COMMAND_INDICATOR + BudgetCommand.COMMAND_WORD;

    public static final String CLEAR_INFO = COMMAND_INDICATOR + ClearCommand.COMMAND_WORD;

    public static final String DELETE_INFO = COMMAND_INDICATOR + DeleteCommand.COMMAND_WORD;

    public static final String EDIT_INFO = COMMAND_INDICATOR + EditCommand.COMMAND_WORD;

    public static final String EXIT_INFO = COMMAND_INDICATOR + ExitCommand.COMMAND_WORD;

    public static final String FIND_INFO = COMMAND_INDICATOR + FindCommand.COMMAND_WORD;

    public static final String HELP_INFO = COMMAND_INDICATOR + HelpCommand.COMMAND_WORD;

    public static final String LIST_INFO = COMMAND_INDICATOR + ListCommand.COMMAND_WORD;

    public static final String RECOMMEND_INFO = COMMAND_INDICATOR + RecommendCommand.COMMAND_WORD;

    public static final String SORT_INFO = COMMAND_INDICATOR + SortCommand.COMMAND_WORD;

    public static final String INVALID_COMMAND_ENTERED_MESSAGE = "Sorry, no information for such command exists!";

    private String input;

    public InfoCommand(String command) {
        input = command;
    }

    public String getInput() {
        return input;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        switch(input) {
            case AddCommand.COMMAND_WORD :
                return new CommandResult(ADD_INFO);
            case BudgetCommand.COMMAND_WORD :
                return new CommandResult(BUDGET_INFO);
            case ClearCommand.COMMAND_WORD :
                return new CommandResult(CLEAR_INFO);
            case DeleteCommand.COMMAND_WORD :
                return new CommandResult(DELETE_INFO);
            case EditCommand.COMMAND_WORD :
                return new CommandResult(EDIT_INFO);
            case ExitCommand.COMMAND_WORD :
                return new CommandResult(EXIT_INFO);
            case FindCommand.COMMAND_WORD :
                return new CommandResult(FIND_INFO);
            case HelpCommand.COMMAND_WORD :
                return new CommandResult(HELP_INFO);
            case ListCommand.COMMAND_WORD :
                return new CommandResult(LIST_INFO);
            case RecommendCommand.COMMAND_WORD :
                return new CommandResult(RECOMMEND_INFO);
            case SortCommand.COMMAND_WORD :
                return new CommandResult(SORT_INFO);
            default :
                throw new CommandException(INVALID_COMMAND_ENTERED_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InfoCommand // instanceof handles nulls
                &&  getInput().equals(((InfoCommand) other).getInput()));
    }
}
