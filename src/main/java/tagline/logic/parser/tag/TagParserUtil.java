package tagline.logic.parser.tag;

import static tagline.commons.core.Messages.MESSAGE_INVALID_TAG_FORMAT;
import static tagline.commons.core.Messages.MESSAGE_UNKNOWN_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tagline.logic.parser.exceptions.ParseException;
import tagline.model.contact.ContactId;
import tagline.model.tag.ContactTag;
import tagline.model.tag.HashTag;
import tagline.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class TagParserUtil {
    private static final String TAG_USAGE = "A tag starts with '@' for contact, '%' for group, and '#' for hash tag";
    private static final Pattern BASIC_TAG_FORMAT = Pattern.compile("(?<tagKey>[%#@])(?<tagValue>.*)");

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        final Matcher matcher = BASIC_TAG_FORMAT.matcher(tag.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_TAG_FORMAT, TAG_USAGE));
        }

        final String tagKey = matcher.group("tagKey");
        final String tagValue = matcher.group("tagValue");

        switch (tagKey) {

        case HashTag.TAG_PREFIX:
            return parseHashTag(tagValue);

        case ContactTag.TAG_PREFIX:
            return parseContactTag(tagValue);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_TAG);
        }
    }

    public static List<Tag> parseTags(List<String> tags) {
        return new ArrayList<>();
    }

    private static ContactTag parseContactTag(String arg) {
        return new ContactTag(new ContactId(arg));
    }

    private static HashTag parseHashTag(String arg) {
        return new HashTag(arg);
    }
}
