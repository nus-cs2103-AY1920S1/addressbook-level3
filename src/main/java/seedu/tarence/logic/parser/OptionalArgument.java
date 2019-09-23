package seedu.tarence.logic.parser;

import java.util.regex.Pattern;

/**
 * Represents possible optional arguments that may be found in user inputs. Each contains a pattern for regex
 * matching and the corresponding prefix.
 */
public class OptionalArgument {

    public static final OptionalArgument OPTIONAL_EMAIL = new OptionalArgument(ArgumentPatterns.PATTERN_EMAIL,
            CliSyntax.PREFIX_EMAIL);
    public static final OptionalArgument OPTIONAL_MATNO = new OptionalArgument(ArgumentPatterns.PATTERN_MATNO,
            CliSyntax.PREFIX_MATNO);
    public static final OptionalArgument OPTIONAL_NUSID = new OptionalArgument(ArgumentPatterns.PATTERN_NUSID,
            CliSyntax.PREFIX_NUSID);

    public final Pattern pattern;
    public final Prefix prefix;

    public OptionalArgument(Pattern pattern, Prefix prefix) {
        this.pattern = pattern;
        this.prefix = prefix;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Prefix getPrefix() {
        return prefix;
    }
}
