package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Tag;


/**
 * Deletes a eatery identified using it's displayed index from the address book.
 */
public class SaveTodoCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves the eatery identified by the index number used in the displayed todo list to main list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_MODE = "Save command is not available in main mode";

    public static final String MESSAGE_REMINDER_TO_USER = "Please include the category "
        + "you wanted to add to following command:\n";

    private final Index targetIndex;

    public SaveTodoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.isMainMode()) {
            throw new CommandException(MESSAGE_INVALID_MODE);
        }
        List<Eatery> lastShownList = model.getFilteredTodoList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
        }

        Eatery eateryToSave = lastShownList.get(targetIndex.getZeroBased());
        String name = eateryToSave.getName().fullName;
        String address = eateryToSave.getAddress().value;
        StringBuilder tags = new StringBuilder();
        for (Tag tag : eateryToSave.getTags()) {
            tags.append(PREFIX_TAG + " " + tag.getName() + " ");
        }
        String pendingCommand = String.format("add %s %s %s %s %s %s",
            PREFIX_NAME, name, PREFIX_ADDRESS, address, tags.toString(), PREFIX_CATEGORY);

        model.deleteEatery(eateryToSave);
        model.toggle();

        String res = MESSAGE_REMINDER_TO_USER + pendingCommand;
        return new CommandResult(res, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaveTodoCommand // instanceof handles nulls
                && targetIndex.equals(((SaveTodoCommand) other).targetIndex)); // state check
    }
}
