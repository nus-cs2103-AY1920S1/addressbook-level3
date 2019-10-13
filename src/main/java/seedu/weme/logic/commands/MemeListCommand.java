package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.weme.model.Model.PREDICATE_SHOW_ALL_MEMES;

import seedu.weme.model.Model;

/**
 * Lists all memes in the meme book to the user.
 */
public class MemeListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all memes";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMemeList(PREDICATE_SHOW_ALL_MEMES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
