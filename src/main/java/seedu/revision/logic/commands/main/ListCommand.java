package seedu.revision.logic.commands.main;

import static java.util.Objects.requireNonNull;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;

import java.util.function.Predicate;

import seedu.revision.commons.core.Messages;
import seedu.revision.logic.commands.Command;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answerable;

/**
 * Lists all answerables in the revision tool to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMPLETE_COMMAND_DIFF = "list diff/";
    public static final String COMPLETE_COMMAND_CAT = "list cat/";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all questions which belong to the "
            + "specified category and difficulty (case-insensitive, optional) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_DIFFICULTY + "DIFFICULTY "
            + "Example: " + COMMAND_WORD
            + PREFIX_CATEGORY + "UML "
            + PREFIX_DIFFICULTY + "2 ";

    private Predicate<Answerable> predicate;

    public ListCommand(Predicate<Answerable> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAnswerableList(predicate);
        return new CommandResultBuilder().withFeedBack(String.format(Messages.MESSAGE_ANSWERABLES_LISTED_OVERVIEW,
                model.getFilteredAnswerableList().size())).build();
    }
}
