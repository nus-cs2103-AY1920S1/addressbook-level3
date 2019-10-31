// @@author shiweing
package tagline.logic.parser.note;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tagline.logic.commands.note.KeywordFilter;
import tagline.logic.commands.note.ListNoteCommand;
import tagline.logic.commands.note.NoteFilter;
import tagline.logic.commands.note.TagFilter;
import tagline.logic.parser.Parser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.tag.TagParserUtil;
import tagline.model.tag.Tag;

/**
 * Parses input arguments and creates a new ListNoteCommand object
 */
public class ListNoteParser implements Parser<ListNoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListNoteCommand
     * and returns an ListNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        return new ListNoteCommand(NoteFilterUtil.generateFilter(args));
    }

    /**
     * Contains utility methods used for generating the *Filter classes from the argument string.
     */
    private static class NoteFilterUtil {
        private static final String HASHTAG_FILTER_FORMAT = "#";
        private static final String CONTACT_FILTER_FORMAT = "@";
        private static final String GROUP_FILTER_FORMAT = "%";
        private static final String TAG_FILTER_FORMAT =
                HASHTAG_FILTER_FORMAT + CONTACT_FILTER_FORMAT + GROUP_FILTER_FORMAT;

        /**
         * Parses {@code argString} into a {@code Filter} and returns it. Leading and trailing whitespaces will be
         * trimmed.
         */
        public static NoteFilter generateFilter(String argString) throws ParseException {
            String trimmedArg = argString.strip();

            // No filter provided, list all notes
            if (trimmedArg.isEmpty()) {
                return null;
            }

            Pattern tagFilterPattern = Pattern.compile("[" + TAG_FILTER_FORMAT + "].*");
            Matcher filterMatcher = tagFilterPattern.matcher(trimmedArg);

            if (filterMatcher.matches()) {
                return generateTagFilter(trimmedArg);
            } else {
                return generateKeywordFilter(trimmedArg);
            }
        }

        /**
         * Returns a {@code KeywordFilter} from the user input.
         */
        private static NoteFilter generateKeywordFilter(String keyword) {
            return new KeywordFilter(keyword);
        }

        /**
         * Returns a {@code TagFilter} from the user input.
         */
        private static NoteFilter generateTagFilter(String tagString) throws ParseException {
            Pattern tagFilterFormat = Pattern.compile("[" + TAG_FILTER_FORMAT + "][^" + TAG_FILTER_FORMAT + "]+");
            Matcher matcher = tagFilterFormat.matcher(tagString);
            List<Tag> tags = new ArrayList<>();

            while (matcher.find()) {
                tags.add(TagParserUtil.parseTag(matcher.group()));
            }

            return new TagFilter(tagString, tags);
        }
    }
}
