package seedu.tarence.logic.parser;

import java.util.regex.Pattern;

/**
 * Contains Student field name patterns. Assumes each pattern is prefixed by a whitespace.
 */
public class ArgumentPatterns {

    public static final Pattern PATTERN_EMAIL = Pattern.compile("\\s[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\b");
    public static final Pattern PATTERN_MATNO = Pattern.compile("\\b[aA][0-9]{7}[a-zA-Z](?!@)");
    public static final Pattern PATTERN_NUSID = Pattern.compile("\\b[eE][0-9]{7}(?!@)");
    public static final Pattern PATTERN_MODCODE = Pattern.compile("\\b[A-Z]{2,3}[0-9]{4}[A-Z]");
    public static final Pattern PATTERN_WEEKRANGE = Pattern.compile("\\b[0-9]+-[0-9]+");

}
