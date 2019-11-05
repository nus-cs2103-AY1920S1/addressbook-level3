package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.question.Question;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskForQuestion;

/**
 * Adds a revision task for question to the task list.
 */
public class AddTaskForQuestionCommand extends Command {
    public static final String COMMAND_WORD = "rq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a question revision task to NUStudy. "
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_DATE + "15/10/2019 "
            + PREFIX_TIME + "1500";

    public static final String MESSAGE_SUCCESS = "Revision task added: %1$s";
    public static final String MESSAGE_INDEX_OUT_OF_BOUND = "Index out of bound. Please key in an index within the"
            + " range of 1 and size of question list";

    private Task toAdd;

    /**
     * Creates an AddTaskForQuestionCommand to add the specified {@code Task}
     */
    public AddTaskForQuestionCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        toAdd = getQuestionTask(model);
        if (model.hasTask(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    private Task getQuestionTask(Model model) throws CommandException {
        Index targetIndex;
        try {
            targetIndex = ParserUtil.parseIndex(toAdd.getHeading().toString());
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }
        List<Question> lastShownList = model.getFilteredQuestionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INDEX_OUT_OF_BOUND);
        }

        Question question = lastShownList.get(targetIndex.getZeroBased());
        LocalDate date = toAdd.getDate();
        LocalTime time = toAdd.getTime();
        return new TaskForQuestion(question, date, time);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskForQuestionCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskForQuestionCommand) other).toAdd));
    }
}
