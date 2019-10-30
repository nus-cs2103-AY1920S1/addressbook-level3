package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.storage.Storage;

/**
 * Adds an automatic tagger to Mark that tags bookmarks according to specific
 * conditions.
 */
public class AutotagCommand extends Command {

    public static final String COMMAND_WORD = "autotag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates an automatic tagger that tags bookmarks which match the given conditions. At least one "
            + "condition must be specified.\n"
            + "Parameters: TAG_NAME [n/NAME_KEYWORD]... [u/URL_KEYWORD]... "
            + "[f/FOLDER]... [nn/NOT_NAME_KEYWORD]... [nu/NOT_URL_KEYWORD]... [nf/NOT_FOLDER]...\n"
            + "Example: " + COMMAND_WORD + " Quiz u/luminus.nus.edu.sg u/quiz nu/attempt f/AY1920";

    public static final String MESSAGE_AUTOTAG_ADDED = "An autotag was added successfully: %1$s";
    public static final String MESSAGE_AUTOTAG_EXISTS = "An autotag with this name already exists: %1$s";
    public static final String MESSAGE_NO_CONDITION_SPECIFIED = "At least one name, URL, or folder condition "
            + "must be specified";
    public static final String MESSAGE_CONDITION_EMPTY = "Conditions cannot be blank. E.g. the empty condition "
            + "u/ is invalid";

    private final SelectiveBookmarkTagger tagger;

    public AutotagCommand(SelectiveBookmarkTagger tagger) {
        requireNonNull(tagger);
        this.tagger = tagger;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        if (model.hasTagger(tagger)) {
            throw new CommandException(String.format(MESSAGE_AUTOTAG_EXISTS, tagger));
        }

        model.addTagger(tagger);
        model.applyAllTaggers();
        // TODO: Don't save Mark if no taggers were actually applied
        String resultMessage = String.format(MESSAGE_AUTOTAG_ADDED, tagger);
        model.saveMark(resultMessage);
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AutotagCommand // instanceof handles nulls
                && tagger.equals(((AutotagCommand) other).tagger)); // state check
    }

}
