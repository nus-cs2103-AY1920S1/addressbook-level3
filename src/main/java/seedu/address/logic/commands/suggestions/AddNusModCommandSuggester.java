package seedu.address.logic.commands.suggestions;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;

/**
 * Provides suggestions for the {@link Prefix}es of the {@link seedu.address.logic.commands.AddNusModCommand}.
 */
public class AddNusModCommandSuggester extends NusModSuggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = List.of(
            CliSyntax.PREFIX_NAME,
            CliSyntax.PREFIX_MODULE_CODE
    );

    @Override
    protected List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        final Prefix prefix = commandArgument.getPrefix();

        if (prefix.equals(CliSyntax.PREFIX_NAME)) {
            return getPersonNameSuggestions(model, commandArgument);
        } else if (prefix.equals(CliSyntax.PREFIX_MODULE_CODE)) {
            final String searchModuleCode = commandArgument.getValue();

            return model
                    .getModuleList()
                    .asUnmodifiableObservableList()
                    .stream()
                    .map(Module::getModuleCode)
                    .map(ModuleCode::toString)
                    .filter(moduleCode -> {
                        return moduleCode.startsWith(searchModuleCode);
                    }).collect(Collectors.toUnmodifiableList());
        }

        return null;
    }
}
