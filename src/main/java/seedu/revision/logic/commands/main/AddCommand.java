package seedu.revision.logic.commands.main;

import static java.util.Objects.requireNonNull;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CORRECT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION_TYPE;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_WRONG;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answerable;

/**
 * Adds a answerable to the revision tool.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String COMPLETE_COMMAND = "add type/ q/ y/ x/ cat/ diff/ ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a question to the test bank. "
            + "Parameters: "
            + PREFIX_QUESTION_TYPE + "QUESTION TYPE "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_DIFFICULTY + "DIFFICULTY "
            + PREFIX_CATEGORY + "[CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION_TYPE + "mcq "
            + PREFIX_QUESTION + "Which of the following gives us the highest intensity of testing? "
            + PREFIX_WRONG + "50% statement coverage "
            + PREFIX_CORRECT + "100% path coverage "
            + PREFIX_WRONG + "70% branch coverage "
            + PREFIX_WRONG + "80% condition coverage "
            + PREFIX_DIFFICULTY + "3 "
            + PREFIX_CATEGORY + "TestCoverage " + PREFIX_CATEGORY + "Week8 ";


    public static final String MESSAGE_SUCCESS = "New question added: %1$s";
    public static final String MESSAGE_DUPLICATE_ANSWERABLE = "This question already exists in the test bank";

    private final Answerable toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Answerable}
     */
    public AddCommand(Answerable answerable) {
        requireNonNull(answerable);
        toAdd = answerable;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAnswerable(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ANSWERABLE);
        }

        model.addAnswerable(toAdd);
        return new CommandResultBuilder().withFeedBack(String.format(MESSAGE_SUCCESS, toAdd)).build();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
