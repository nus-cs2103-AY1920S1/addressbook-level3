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

public abstract class AnnotationCommand extends Command {

    public static final String MESSAGE_NO_CACHE_AVAILABLE = "No cache is available to annotate.\n"
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

    public OfflineDocument getRequiredDoc(Model model) throws CommandException {
        requireNonNull(model);
        List<Bookmark> lastShownList = model.getFilteredBookmarkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
        }

        Bookmark bookmarkOwnerOfCache = lastShownList.get(index.getZeroBased());
        List<CachedCopy> caches = bookmarkOwnerOfCache.getCachedCopies();
        if (caches.isEmpty()) {
            //TODO: maybe - create cache if it dne so they can annotate immediately. (then need to change ug and dg too)
            throw new CommandException(MESSAGE_NO_CACHE_AVAILABLE);
        }

        //TODO: pick out the appropriate cache. (change ug; default version on newwest cache; but can access others too)
        CachedCopy c = caches.get(0);

        return c.getAnnotations();
    }

    public Index getBookmarkIndex() {
        return index;
    }
    public ParagraphIdentifier getPid() {
        return pid;
    }

}
