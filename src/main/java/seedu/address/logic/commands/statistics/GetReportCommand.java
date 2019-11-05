package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.statistics.Type.REPORT;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.question.Question;
import seedu.address.model.quiz.exceptions.EmptyQuizResultListException;

/**
 * Returns a report for the questions done.
 */
public class GetReportCommand extends Command {
    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns a report of how well you have "
            + "answered a particular question and shows all your past answers to that question.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + "5";

    public static final String MESSAGE_SUCCESS = "Here is a report of question %s:";
    public static final String MESSAGE_NO_REPORT = "There is no data available for this question. ";

    private final Index index;


    /**
     * Creates a GetReportCommand to get the specified {@code QuizResult}
     * for a question.
     * @param index The index of the question.
     */
    public GetReportCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Question> lastShownList = model.getFilteredQuestionList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }
        Question question = lastShownList.get(index.getZeroBased());
        try {
            model.generateQnsReport(question);
        } catch (EmptyQuizResultListException e) {
            throw new CommandException(MESSAGE_NO_REPORT);
        }
        CommandResult c = new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased()), 8);
        c.setType(REPORT);
        return c;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GetReportCommand // instanceof handles nulls
                && index.equals(((GetReportCommand) other).index)); // state check
    }
}
