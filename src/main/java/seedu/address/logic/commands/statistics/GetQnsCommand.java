package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Gets questions that have been answered correctly/incorrectly by subject.
 */
public class GetQnsCommand extends Command {
    public static final String COMMAND_WORD = "questions";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets all questions that have been answered "
            + "correctly/incorrectly for a particular subject by using either -c/-i respectively.\n"
            + "You can also see past answers to the questions by adding the -a field.\n"
            + "Parameters: "
            + PREFIX_SUBJECT + "SUBJECT "
            + "[-c] [-i] "
            + "[-a]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBJECT + "CS2103T" + " -c -a";

    public static final String MESSAGE_SUCCESS = "Here are the questions: ";
    public static final String MESSAGE_NO_CORRECT_QNS = "There are no correctly answered questions. ";
    public static final String MESSAGE_NO_INCORRECT_QNS = "There are no incorrectly answered questions. ";

    private final List subjects;
    private final boolean getCorrectQns;
    private final boolean getAnswers;

    public GetQnsCommand(List subjects, boolean getCorrectQns, boolean getAnswers) {
        requireNonNull(subjects);
        requireNonNull(getCorrectQns);
        requireNonNull(getAnswers);
        this.subjects = subjects;
        this.getCorrectQns = getCorrectQns;
        this.getAnswers = getAnswers;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // need to extract data from storage
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
