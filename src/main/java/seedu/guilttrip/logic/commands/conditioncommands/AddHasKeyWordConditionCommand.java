package seedu.guilttrip.logic.commands.conditioncommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.reminders.conditions.KeyWordsCondition;

/**
 * Creates a KeyWordsCondition when executed.
 */
public class AddHasKeyWordConditionCommand extends Command {

    public static final String COMMAND_WORD = "addHasKeyWordCondition";

    public static final String ONE_LINER_DESC = COMMAND_WORD
            + ": Adds a KeyWordsCondition to the conditions list. \n";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: "
            + "KEYWORD...(No space between keywords, only a comma)\n"
            + "Example: "
            + COMMAND_WORD
            + " Mala,Deck\n";

    public static final String MESSAGE_SUCCESS = "New KeyWordsCondition added: %1$s";

    private List<String> keywords;

    public AddHasKeyWordConditionCommand(List<String> keywords) {
        this.keywords = keywords;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        KeyWordsCondition condition = new KeyWordsCondition(keywords);
        model.addCondition(condition);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, condition));
        commandResult.showConditionPanel();
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddHasKeyWordConditionCommand // instanceof handles nulls
                && keywords.equals(((AddHasKeyWordConditionCommand) other).keywords));
    }
}
