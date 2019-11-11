package seedu.weme.model.template;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.AppUtil.checkArgument;

import java.awt.Font;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a {@code MemeText}'s text style, i.e. bold, italic, etc.
 * Guarantees: immutable; is valid as declared in {@link #isValidMemeTextStyle}}
 */
public class MemeTextStyle {

    public static final String MESSAGE_CONSTRAINTS = "Style must be one of plain, bold, italic, or their combination";
    public static final MemeTextStyle DEFAULT_MEME_TEXT_STYLE = new MemeTextStyle(Font.PLAIN);

    private static final Map<String, Integer> STRING_STYLE_MAP = Map.of(
        "plain", Font.PLAIN,
        "bold", Font.BOLD,
        "italic", Font.ITALIC
    );

    private int style;

    /**
     * Constructs a {@code MemeTextStyle}.
     *
     * @param style A valid meme text style
     */
    public MemeTextStyle(String style) {
        requireNonNull(style);
        checkArgument(isValidMemeTextStyle(style), MESSAGE_CONSTRAINTS);
        this.style = parseStyle(style).get();
    }

    /**
     * Constructs a {@code MemeTextStyle} whose value is {@code style}.
     * <p>
     * style is assumed to be a valid style, otherwise it will be replaced by plain style.
     *
     * @param style the value of this style
     */
    private MemeTextStyle(int style) {
        this.style = style;
    }

    /**
     * Parses {@code style} into an integer representing the font style, as specified in {@link java.awt.Font}.
     * @param style the string to parse
     * @return the parsed font style, or {@link Optional#empty} if parsing fails.
     */
    private static Optional<Integer> parseStyle(String style) {
        String s = style.trim().toLowerCase();
        if (STRING_STYLE_MAP.containsKey(s)) {
            return Optional.of(STRING_STYLE_MAP.get(s));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns true if a given string is a valid meme text style.
     */
    public static boolean isValidMemeTextStyle(String test) {
        return parseStyle(test).isPresent();
    }

    /**
     * Combines many styles and return the combined style.
     *
     * @param styles the styles to combine
     * @return the combined style
     */
    public static MemeTextStyle combine(Collection<MemeTextStyle> styles) {
        int result = 0;
        for (MemeTextStyle s : styles) {
            result |= s.style;
        }
        return new MemeTextStyle(result);
    }

    public int getStyle() {
        return style;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof MemeTextStyle // instanceof handles nulls
            && style == ((MemeTextStyle) other).style); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(style);
    }

}
