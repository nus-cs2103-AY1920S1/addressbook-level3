package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import mams.commons.core.Messages;
import mams.model.Model;
import mams.model.module.ModuleContainsKeywordsPredicate;
import mams.model.student.NameContainsKeywordsPredicate;

/**
 * Finds and lists all students in MAMS whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";



    @Override
    abstract public CommandResult execute(Model model);
}
