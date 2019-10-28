package seedu.address.logic.commands.suggestions;

import java.util.List;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Provides suggestions for the {@link Prefix}es of the {@link seedu.address.logic.commands.ShowCommand}.
 */
public class ShowCommandSuggester extends Suggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = List.of(
            CliSyntax.PREFIX_NAME,
            CliSyntax.PREFIX_GROUPNAME
    );

    @Override
    protected List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        final Prefix prefix = commandArgument.getPrefix();

        if (prefix.equals(CliSyntax.PREFIX_NAME)) {
            return getPersonNameSuggestions(model, commandArgument);
        } else if (prefix.equals(CliSyntax.PREFIX_GROUPNAME)) {
            return getGroupNameSuggestions(model, commandArgument);
        }

        return null;
    }
}
