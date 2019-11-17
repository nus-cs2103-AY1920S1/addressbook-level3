package seedu.mark.logic.commands;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.OfflineCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.storage.Storage;

/**
 * Deletes all annotations and resets the offline document to a clean slate.
 */
public class DeleteAnnotationClearAllCommand extends DeleteAnnotationCommand {

    public static final String MESSAGE_SUCCESS = "All annotations are successfully cleared!";

    private static final ParagraphIdentifier DUMMY_PID = ParagraphIdentifier.makeExistId(Index.fromOneBased(1));

    public DeleteAnnotationClearAllCommand(Index bkmarkIndex) {
        super(bkmarkIndex, DUMMY_PID, false, false);
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        Bookmark oldBkmark = getRequiredBookmark(model);

        Bookmark newBkmark = new Bookmark(oldBkmark.getName(),
                oldBkmark.getUrl(), oldBkmark.getRemark(), oldBkmark.getFolder(),
                oldBkmark.getTags(), oldBkmark.getCachedCopies());

        if (newBkmark.getCachedCopies().size() == 0) {
            throw new CommandException(MESSAGE_NO_CACHE_AVAILABLE);
        }

        newBkmark.resetCachedCopy();
        saveState(model, oldBkmark, newBkmark, getRequiredDoc(newBkmark), MESSAGE_SUCCESS);

        return new OfflineCommandResult(MESSAGE_SUCCESS);
    }
}
