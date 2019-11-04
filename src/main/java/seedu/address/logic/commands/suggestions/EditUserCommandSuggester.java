package seedu.address.logic.commands.suggestions;

import java.util.List;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Provides suggestions for the {@link Prefix}es of the {@link seedu.address.logic.commands.EditUserCommand}.
 */
public class EditUserCommandSuggester extends EditPersonCommandSuggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = List.of(
            CliSyntax.PREFIX_NAME,
            CliSyntax.PREFIX_PHONE,
            CliSyntax.PREFIX_EMAIL,
            CliSyntax.PREFIX_ADDRESS,
            CliSyntax.PREFIX_REMARK,
            CliSyntax.PREFIX_TAG
    );

    @Override
    protected List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        final Prefix prefix = commandArgument.getPrefix();

        if (!SUPPORTED_PREFIXES.contains(prefix)) {
            return null;
        }

        final Person user = model.getUser();
        return getCurrentValuesOfPerson(user, prefix);

    }
}
