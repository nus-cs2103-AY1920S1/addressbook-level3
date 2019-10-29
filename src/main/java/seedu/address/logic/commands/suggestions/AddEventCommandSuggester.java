package seedu.address.logic.commands.suggestions;

import java.util.List;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Provides suggestions for the {@link Prefix}es of the {@link seedu.address.logic.commands.AddEventCommand}.
 */
public class AddEventCommandSuggester extends Suggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = List.of(
            CliSyntax.PREFIX_NAME,
            CliSyntax.PREFIX_EVENTNAME,
            CliSyntax.PREFIX_TIMING
    );

    @Override
    protected List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        final Prefix prefix = commandArgument.getPrefix();

        if (prefix.equals(CliSyntax.PREFIX_NAME)) {
            return getPersonNameSuggestions(model, commandArgument);
        }

        return null;
    }
}
