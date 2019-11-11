package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.Model;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.CachedCopy;

/**
 * Represents all commands that handle annotation requests.
 * Supported annotation requests: add, delete, edit.
 */
public abstract class AnnotationCommand extends Command {

    public static final String MESSAGE_CONSTRAINTS = "Paragraph identifiers should coincide with the paragraph labels"
            + " on the left of each paragraph (e.g. 'P3').";
    public static final String MESSAGE_NO_CACHE_AVAILABLE = "No offline copy is available to annotate.\n"
            //+ "A cache has been created for you. Kindly press Enter to confirm to add the annotation to this cache."
            + "Download a cache using the " + CacheCommand.COMMAND_WORD + " command to start annotating.";

    private final Index index;
    private final ParagraphIdentifier pid;

    public AnnotationCommand(Index index, ParagraphIdentifier pid) {
        requireNonNull(index);
        requireNonNull(pid);

        this.index = index;
        this.pid = pid;
    }

    public Bookmark getRequiredBookmark(Model model) throws CommandException {
        requireNonNull(model);
        List<Bookmark> lastShownList = model.getFilteredBookmarkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
        }

        return lastShownList.get(index.getZeroBased());
    }

    public OfflineDocument getRequiredDoc(Bookmark bkmark) throws CommandException {
        requireNonNull(bkmark);
        CachedCopy c = getRequiredCache(bkmark);

        return c.getAnnotations();
    }

    public OfflineDocument getRequiredDocCopy(Bookmark bkmark) throws CommandException {
        return getRequiredDoc(bkmark).copy();
    }

    private CachedCopy getRequiredCache(Bookmark bkmark) throws CommandException {
        requireNonNull(bkmark);

        List<CachedCopy> caches = bkmark.getCachedCopies();
        if (caches.isEmpty()) {
            //TODO: maybe - create cache if it dne so they can annotate immediately. (then need to change ug and dg too)
            throw new CommandException(MESSAGE_NO_CACHE_AVAILABLE);
        }

        //TODO: pick out the appropriate cache. (change ug; default version on newwest cache; but can access others too)
        return caches.get(0);
    }


    /**
     * Saves that current state of Mark after annotation.
     * @param model The model of Mark
     * @param oldBkmark The bookmark to replace before this annotation
     * @param newBkmark The bookmark to use after this annotation
     * @param doc The current offline document involved
     * @param savedMsg The message to show user
     */
    public void saveState(Model model, Bookmark oldBkmark, Bookmark newBkmark, OfflineDocument doc, String savedMsg) {
        model.updateDocument(doc);
        model.setOfflineDocNameCurrentlyShowing(oldBkmark.getName().value);

        newBkmark.updateCachedCopy(doc);
        model.setBookmark(oldBkmark, newBkmark);

        model.saveMark(savedMsg);
    }


    public Index getBookmarkIndex() {
        return index;
    }
    public ParagraphIdentifier getPid() {
        return pid;
    }

}

/**
 * This is a dummy pid for a non-existent paragraph, null.
 */
class DummyParagraphIdentifier extends ParagraphIdentifier {
    public DummyParagraphIdentifier() {
        super(Index.fromOneBased(1), ParagraphType.STRAY);
    }

    @Override
    public String toString() {
        return "NULL (i.e. your note has been added to the general notes section)";
    }
}
