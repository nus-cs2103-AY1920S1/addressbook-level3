package seedu.algobase.logic.commands.findrule;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;

/**
 * Adds a find rule to AlgoBase.
 */
public class AddFindRuleCommand extends Command {

    public static final String COMMAND_WORD = "addfindrule";
    public static final String SHORT_COMMAND_WORD = "afr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a find rule to AlgoBase.\n"
            + "Parameters:\n"
            + "RULE_NAME "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AUTHOR + "AUTHOR] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_SOURCE + "SOURCE] "
            + "[" + PREFIX_DIFFICULTY + "LOWER_BOUND-UPPER_BOUND] "
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example:\n"
            + COMMAND_WORD
            + " MediumDifficulty"
            + " " + PREFIX_DIFFICULTY + "2.5-3.5\n";
    public static final String MESSAGE_SUCCESS =
        "New [%1$s] find rule added to AlgoBase.";
    public static final String MESSAGE_DUPLICATE_FIND_RULE =
        "Find rule [%1$s] already exists in AlgoBase.";
    public static final String MESSAGE_NO_CONSTRAINTS =
        "You should provide at least one constraint for a new find rule.";

    private final ProblemSearchRule toAdd;

    public AddFindRuleCommand(ProblemSearchRule rule) {
        requireNonNull(rule);
        this.toAdd = rule;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasFindRule(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_FIND_RULE, toAdd.getName()));
        }

        model.addFindRule(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddFindRuleCommand // instanceof handles nulls
            && toAdd.equals(((AddFindRuleCommand) other).toAdd));
    }
}
