package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.elisa.commons.core.Messages;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.item.VisualizeList;


/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final String[] searchString;

    private VisualizeList beforeFilter;

    public FindCommand(String[] searchString) {
        this.searchString = searchString;
    }

    @Override
    public CommandResult execute(ItemModel model) {
        requireNonNull(model);
        beforeFilter = model.getVisualList().deepCopy();
        model.findItem(searchString);
        if (!isExecuted()) {
            model.getElisaCommandHistory().clearRedo();
            setExecuted(true);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_ITEM_LISTED_OVERVIEW, model.getVisualList().size()));
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        model.setVisualizeList(beforeFilter);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && searchString.equals(((FindCommand) other).searchString)); // state check
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
