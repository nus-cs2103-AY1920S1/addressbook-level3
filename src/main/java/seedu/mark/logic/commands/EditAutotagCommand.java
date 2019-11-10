package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.storage.Storage;

/**
 * Deletes an automatic tagger from Mark.
 */
public class EditAutotagCommand extends Command {

    public static final String COMMAND_WORD = "autotag-edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits an existing automatic tagger by modifying its tag name or conditions. "
            + "At least one parameter should be specified.\n"
            + "Parameters: TAG_NAME [t/NEW_TAG_NAME] [n/NAME_KEYWORD]... [u/URL_KEYWORD]... "
            + "[f/FOLDER]... [nn/NOT_NAME_KEYWORD]... [nu/NOT_URL_KEYWORD]... [nf/NOT_FOLDER]...\n"
            + "Example: " + COMMAND_WORD + " Quiz u/luminus.nus.edu.sg u/quiz nu/attempt f/AY1920";

    public static final String MESSAGE_AUTOTAG_EDITED = "Autotag successfully edited: %1$s";
    public static final String MESSAGE_MULTIPLE_TAG_NAMES = "Only one new tag name can be specified";
    public static final String MESSAGE_AUTOTAG_DOES_NOT_EXIST = "No autotag with this tag name was found: %1$s";

    private final String oldTaggerName;
    private final SelectiveBookmarkTagger newTagger;

    public EditAutotagCommand(String oldTaggerName, SelectiveBookmarkTagger newTagger) {
        requireNonNull(oldTaggerName);
        this.oldTaggerName = oldTaggerName;
        this.newTagger = newTagger;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        Optional<SelectiveBookmarkTagger> oldTagger = model.removeTagger(oldTaggerName);
        if (oldTagger.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_AUTOTAG_DOES_NOT_EXIST, oldTaggerName));
        }

        assert !model.hasTagger(newTagger); // tagger should have been removed by this point

        SelectiveBookmarkTagger taggerToAdd = newTagger;
        if (newTagger.hasEmptyPredicate()) {
            taggerToAdd = new SelectiveBookmarkTagger(newTagger.getTagToApply(), oldTagger.get().getPredicate());
        }
        model.addTagger(taggerToAdd);
        model.applyAllTaggers();

        String resultMessage = String.format(MESSAGE_AUTOTAG_EDITED, taggerToAdd);
        model.saveMark(resultMessage);
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditAutotagCommand // instanceof handles nulls
                && oldTaggerName.equals(((EditAutotagCommand) other).oldTaggerName)
                && newTagger.equals(((EditAutotagCommand) other).newTagger)); // state check
    }

}
