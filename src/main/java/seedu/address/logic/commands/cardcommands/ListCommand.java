// @@author chrischenhui
package seedu.address.logic.commands.cardcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all cards in the word bank to the user.
 */
public class ListCommand extends CardCommand {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " \n";
    public static final String MESSAGE_SUCCESS = "Listed all cards";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
