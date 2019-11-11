package seedu.address.logic.commands.suggestions;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Provides suggestions for the {@link Prefix}es of the {@link seedu.address.logic.commands.EditGroupCommand}.
 */
public class EditGroupCommandSuggester extends Suggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = List.of(
            CliSyntax.PREFIX_EDIT,
            CliSyntax.PREFIX_REMARK,
            CliSyntax.PREFIX_DESCRIPTION,
            CliSyntax.PREFIX_ROLE
    );

    protected static Optional<Group> getSelectedGroup(final Model model, final ArgumentList arguments) {
        return arguments.getFirstValueOfPrefix(CliSyntax.PREFIX_EDIT).flatMap(groupName -> {
            return getGroupByName(model, groupName);
        });
    }

    @Override
    protected List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        final Prefix prefix = commandArgument.getPrefix();
        final String value = commandArgument.getValue();

        if (!SUPPORTED_PREFIXES.contains(prefix)) {
            return null;
        }

        if (prefix.equals(CliSyntax.PREFIX_EDIT)) {
            return model.groupSuggester(value);
        }

        final Optional<Group> optionalSelectedGroup = getSelectedGroup(model, arguments);
        if (optionalSelectedGroup.isEmpty()) {
            return null;
        }

        final Group selectedGroup = optionalSelectedGroup.get();

        if (prefix.equals(CliSyntax.PREFIX_DESCRIPTION)) {
            return List.of(selectedGroup.getGroupDescription().toString());
        } else if (prefix.equals(CliSyntax.PREFIX_ROLE)) {
            return List.of(selectedGroup.getUserRole().getRole());
        }

        return null;
    }
}
