package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.dankito.readability4j.Readability4J;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.CachedCopy;
import seedu.mark.storage.Storage;

/**
 * Caches a bookmark for offline viewing.
 */
public class CacheCommand extends Command {

    public static final String COMMAND_WORD = "cache";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Caches a bookmark's contents.\n"
            + "Parameters: INDEX";

    public static final String MESSAGE_SUCCESS = "Bookmark's contents successfully cached!";
    private static final String MESSAGE_FAILURE =
            "Unable to cache bookmark's content. Check the URL and your internet connection and try again!";

    private Index index;

    /**
     * Instantiates a new Cache Command.
     *
     * @param index the index to cache
     */
    public CacheCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNonNull(model);
        List<Bookmark> lastShownList = model.getFilteredBookmarkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
        }

        Bookmark bookmarkToCache = lastShownList.get(index.getZeroBased());
        String urlString = bookmarkToCache.getUrl().value;
        List<CachedCopy> updatedCachedCopies = new ArrayList<>(bookmarkToCache.getCachedCopies());
        String content = "";
        try {
            URL url = new URL(urlString);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder output = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                output.append(inputLine);
            }
            in.close();
            content = new Readability4J(urlString, output.toString()).parse().getArticleContent().html();
        } catch (IOException e) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        CachedCopy cached = new CachedCopy(content);
        updatedCachedCopies.add(cached);
        Bookmark cachedBookmark = new Bookmark(bookmarkToCache.getName(), bookmarkToCache.getUrl(),
                bookmarkToCache.getRemark(), bookmarkToCache.getFolder(), bookmarkToCache.getTags(),
                updatedCachedCopies);

        model.setBookmark(bookmarkToCache, cachedBookmark);
        model.saveMark();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CacheCommand // instanceof handles nulls
                && index.equals(((CacheCommand) other).index));
    }
}
