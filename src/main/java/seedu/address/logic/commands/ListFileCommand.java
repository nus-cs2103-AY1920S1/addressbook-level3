package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FILES;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListFileCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all files";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFileList(PREDICATE_SHOW_ALL_FILES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
