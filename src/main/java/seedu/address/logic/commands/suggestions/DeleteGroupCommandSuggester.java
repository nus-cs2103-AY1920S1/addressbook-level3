package seedu.address.logic.commands.suggestions;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Provides suggestions for the {@link Prefix}es of the {@link seedu.address.logic.commands.DeleteGroupCommand}.
 */
public class DeleteGroupCommandSuggester extends Suggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = List.of(
            CliSyntax.PREFIX_GROUPNAME
    );

    @Override
    protected List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        final Prefix prefix = commandArgument.getPrefix();

        if (!prefix.equals(CliSyntax.PREFIX_GROUPNAME)) {
            return null;
        }

        final List<String> groupNameSuggestions = getGroupNameSuggestions(model, commandArgument);
        final List<String> excludedGroupNames = arguments.filterByPrefix(CliSyntax.PREFIX_GROUPNAME)
                .filter(candidateCommandArgument -> {
                    return !candidateCommandArgument.equals(commandArgument);
                })
                .map(CommandArgument::getValue)
                .collect(Collectors.toUnmodifiableList());

        groupNameSuggestions.removeAll(excludedGroupNames);

        return groupNameSuggestions;
    }
}
