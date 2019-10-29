package seedu.address.logic.parser;

import java.util.regex.Pattern;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {
    public static final Pattern MODULE_PATTERN = Pattern.compile("^[a-zA-Z]{2,3}(\\d{4})[a-zA-Z]?$");
    public static final Pattern SEMESTER_PATTERN = Pattern.compile("^[yY][1-5][sS][tT]?[1-2]$");
    public static final Pattern TAG_PATTERN =
            Pattern.compile("^(?![a-zA-Z]{2,3}(\\d{4})[a-zA-Z]?)(?![yY][1-5][sS][tT]?[1-2])\\p{ASCII}+$");
}
