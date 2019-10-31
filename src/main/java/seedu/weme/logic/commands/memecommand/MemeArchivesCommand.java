package seedu.weme.logic.commands.memecommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.model.Model.PREDICATE_SHOW_ALL_ARCHIVED_MEMES;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;

/**
 * Lists all archived memes in Weme to the user.
 */
public class MemeArchivesCommand extends Command {

    public static final String COMMAND_WORD = "archives";

    public static final String MESSAGE_SUCCESS = "Listed all archived memes";
    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": list all archived memes.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION;


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMemeList(PREDICATE_SHOW_ALL_ARCHIVED_MEMES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
