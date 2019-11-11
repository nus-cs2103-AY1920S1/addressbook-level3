package seedu.weme.logic.commands.templatecommand;

import static java.util.Objects.requireNonNull;

import seedu.weme.commons.core.Messages;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;
import seedu.weme.model.template.NameContainsKeywordsPredicate;

/**
 * Finds and lists all templates in Weme whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class TemplateFindCommand extends Command {
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": finds all templates whose names contain any of "
        + "the specified keywords (case-insensitive)";

    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
        + "\nParameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " drake spongebob doge";

    private final NameContainsKeywordsPredicate predicate;

    public TemplateFindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTemplateList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_TEMPLATES_LISTED_OVERVIEW, model.getFilteredTemplateList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TemplateFindCommand // instanceof handles nulls
            && predicate.equals(((TemplateFindCommand) other).predicate)); // state check
    }
}
