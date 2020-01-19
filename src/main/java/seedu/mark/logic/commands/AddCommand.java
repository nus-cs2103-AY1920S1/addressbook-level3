package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_FOLDER_NOT_FOUND;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_URL;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.storage.Storage;

/**
 * Adds a bookmark to Mark.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a bookmark to Mark.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_URL + "URL "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_FOLDER + "FOLDER] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "NUSMods "
            + PREFIX_URL + "https://nusmods.com "
            + PREFIX_REMARK + "Module planner "
            + PREFIX_TAG + "school "
            + PREFIX_TAG + "timetable";

    public static final String MESSAGE_SUCCESS = "New bookmark added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOKMARK = "This bookmark already exists in Mark";
    public static final String MESSAGE_MISSING_URL = "Failed to obtain the URL of the current page.";

    private Bookmark toAdd;
    private boolean hasNoUrl;

    /**
     * Creates an AddCommand to add the specified {@code Bookmark}, the Url
     * of the bookmark must be present.
     */
    public AddCommand(Bookmark bookmark) {
        this(bookmark, false);
    }

    /**
     * Creates an AddCommand to add the specified {@code Bookmark}
     */
    public AddCommand(Bookmark bookmark, boolean hasNoUrl) {
        requireNonNull(bookmark);
        this.toAdd = bookmark;
        this.hasNoUrl = hasNoUrl;

    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        if (hasNoUrl) {
            Url urlToAdd = model.getCurrentUrl();
            // Throw exception when there is no internet connection
            if (urlToAdd == null) {
                throw new CommandException(MESSAGE_MISSING_URL);
            }
            toAdd = new BookmarkBuilder(toAdd).withUrl(urlToAdd.value).build();
        }

        if (model.hasBookmark(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKMARK);
        }

        if (!model.hasFolder(toAdd.getFolder())) {
            throw new CommandException(String.format(MESSAGE_FOLDER_NOT_FOUND, toAdd.getFolder()));
        }

        model.addBookmark(toAdd);
        model.applyAllTaggers();
        model.saveMark(String.format(MESSAGE_SUCCESS, toAdd));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd))
                && hasNoUrl == ((AddCommand) other).hasNoUrl;
    }
}
