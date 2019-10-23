package seedu.address.logic.commands.suggestions;

import java.util.Collections;
import java.util.List;

import seedu.address.logic.commands.suggestions.stateless.NullSuggester;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Prefix;

/**
 * Provides suggestions for the {@link Prefix}es of the {@link seedu.address.logic.commands.AddGroupCommand}.
 */
public class AddGroupCommandSuggester extends NullSuggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = Collections.unmodifiableList(List.of(
            CliSyntax.PREFIX_GROUPNAME
    ));
}
