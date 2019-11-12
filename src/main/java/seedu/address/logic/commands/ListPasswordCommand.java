package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PASSWORDS;

import seedu.address.model.Model;

/**
 * Lists all password in the password book to the user.
 */
public class ListPasswordCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all passwords";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPasswordList(PREDICATE_SHOW_ALL_PASSWORDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
