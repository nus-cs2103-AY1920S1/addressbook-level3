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

    public static final String MESSAGE_CONSTRAINTS =
            "Paragraph identifiers should coincide with the paragraph labels on the left of each paragraph (e.g. 'P3').";
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

    public CachedCopy getRequiredCache(Model model) throws CommandException {
        requireNonNull(model);

        Bookmark bookmarkOwnerOfCache = getRequiredBookmark(model);
        List<CachedCopy> caches = bookmarkOwnerOfCache.getCachedCopies();
        if (caches.isEmpty()) {
            //TODO: maybe - create cache if it dne so they can annotate immediately. (then need to change ug and dg too)
            throw new CommandException(MESSAGE_NO_CACHE_AVAILABLE);
        }

        //TODO: pick out the appropriate cache. (change ug; default version on newwest cache; but can access others too)
        return caches.get(0);
    }

    public OfflineDocument getRequiredDoc(Model model) throws CommandException {
        requireNonNull(model);
        CachedCopy c = getRequiredCache(model);

        return c.getAnnotations();
    }

    public Index getBookmarkIndex() {
        return index;
    }
    public ParagraphIdentifier getPid() {
        return pid;
    }

}
