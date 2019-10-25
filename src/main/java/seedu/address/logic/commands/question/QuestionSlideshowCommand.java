package seedu.address.logic.commands.question;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Starts a slideshow based on the questions selected.
 */
public class QuestionSlideshowCommand extends QuestionCommand {

    public static final String MESSAGE_USAGE =
        COMMAND_WORD + " slideshow [question no(s).]: Start a questions slideshow\n"
            + "Example: slideshow 1 2 3\n"
            + "(This will start a slideshow with question 1, 2 and 3)";

    private final List<Index> questionIndexes;

    public QuestionSlideshowCommand(String questionsInput) {
        questionIndexes = Arrays.stream(questionsInput
            .split(" "))
            .map((x) -> Index.fromOneBased(Integer.parseInt(x)))
            .collect(Collectors.toList());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setSlideshowQuestions(questionIndexes);
        return new CommandResult("Starting slideshow.", false,
                true, false, false, false, false);
    }
}
