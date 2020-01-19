package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_URL;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import seedu.mark.logic.commands.AddCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Remark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_URL, PREFIX_REMARK, PREFIX_TAG, PREFIX_FOLDER);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_URL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        boolean hasNoUrl = false;
        // compulsory fields
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String urlString = argMultimap.getValue(PREFIX_URL).get();
        Url url;
        if (!"this".equals(urlString)) {
            url = ParserUtil.parseUrl(urlString);
        } else {
            hasNoUrl = true;
            url = new Url(BookmarkBuilder.DEFAULT_URL);
        }

        // optional fields
        Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).orElse(Remark.DEFAULT_VALUE));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Folder folder = ParserUtil.parseFolder(argMultimap.getValue(PREFIX_FOLDER).orElse(Folder.DEFAULT_FOLDER_NAME));

        Bookmark bookmark = new Bookmark(name, url, remark, folder, tagList, new ArrayList<>());
        return new AddCommand(bookmark, hasNoUrl);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
