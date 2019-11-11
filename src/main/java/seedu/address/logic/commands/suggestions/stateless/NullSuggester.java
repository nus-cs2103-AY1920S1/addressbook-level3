package seedu.address.logic.commands.suggestions.stateless;

import java.util.List;

import seedu.address.logic.commands.suggestions.Suggester;
import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.model.Model;

/**
 * Simple {@link Suggester} that doesn't suggest anything.
 */
public class NullSuggester extends Suggester {
    @Override
    protected final List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        return null;
    }
}
