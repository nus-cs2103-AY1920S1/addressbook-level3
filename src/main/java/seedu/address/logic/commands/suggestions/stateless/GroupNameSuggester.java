package seedu.address.logic.commands.suggestions.stateless;

import java.util.List;

import seedu.address.logic.commands.suggestions.Suggester;
import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Simple {@link Suggester} that can only suggest group names.
 * Meant to be extended/aliased for {@link seedu.address.logic.commands.Command}s that only take in a group name.
 */
public class GroupNameSuggester extends Suggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = List.of(
            CliSyntax.PREFIX_GROUPNAME
    );

    @Override
    protected final List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        final Prefix prefix = commandArgument.getPrefix();

        if (prefix.equals(CliSyntax.PREFIX_GROUPNAME)) {
            final String enteredGroupName = commandArgument.getValue();
            return model.groupSuggester(enteredGroupName);
        }

        return null;
    }
}
