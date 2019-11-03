package seedu.module.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.module.commons.core.Messages;
import seedu.module.model.Model;
import seedu.module.model.module.Module;

/**
 * Finds and lists all modules in Module book ArchivedModuleList with names that contain any of the argument keywords
 * based on keyword predicates.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all Modules in the archive of all modules with"
            + " fields that contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters:[field]\\ KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " module\\ cs2103 ma1521";

    private final List<Predicate<Module>> listOfPredicates;

    public FindCommand(List<Predicate<Module>> listOfPredicates) {
        this.listOfPredicates = listOfPredicates;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredArchivedModuleList(listOfPredicates.stream().reduce(x -> true, Predicate::and));
        model.updateFilteredModuleList(listOfPredicates.stream().reduce(x -> true, Predicate::and));
        model.updateDisplayedList();
        return new CommandResult(
                String.format(Messages.MESSAGE_MODULES_LISTED_OVERVIEW, model.getFilteredArchivedModuleList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && listOfPredicates.equals(((FindCommand) other).listOfPredicates)); // state check
    }
}
