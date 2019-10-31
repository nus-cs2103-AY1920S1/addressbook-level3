package seedu.address.logic.commands.conditioncommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminders.conditions.KeyWordsCondition;

/**
 * Creates a KeyWordsCondition when executed.
 */
public class AddKeyWordsConditionCommand extends Command {

    public static final String COMMAND_WORD = "addKeyWordsCondition";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a KeyWordsCondition to the conditions list. \n"
            + "Parameters: "
            + "KEYWORD...(No space between keywords, only a comma)\n"
            + "Example: "
            + COMMAND_WORD
            + " Mala,Deck\n";

    public static final String MESSAGE_SUCCESS = "New KeyWordsCondition added: %1$s";

    private List<String> keywords;

    public AddKeyWordsConditionCommand(List<String> keywords) {
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
                || (other instanceof AddKeyWordsConditionCommand // instanceof handles nulls
                && keywords.equals(((AddKeyWordsConditionCommand) other).keywords));
    }
}
