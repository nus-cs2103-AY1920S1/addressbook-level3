package seedu.address.logic.commands.question;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
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

    public static final String MESSAGE_SUCCESS = "Starting slideshow.";

    private final List<Index> questionIndexes;

    public QuestionSlideshowCommand(String questionsInput) {
        questionsInput = questionsInput.trim().replaceAll(" +", " "); // Remove extra space
        questionIndexes = Arrays.stream(questionsInput
            .split(" "))
            .map((x) -> Index.fromOneBased(Integer.parseInt(x)))
            .collect(Collectors.toList());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        // Validating the indexes
        int noOfIndexes = model.getAllQuestions().size();
        for (int i = 0; i < questionIndexes.size(); i++) {
            int index = questionIndexes.get(i).getZeroBased();
            if (index >= noOfIndexes || index < 0) {
                throw new CommandException(Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
            }
        }
        model.setSlideshowQuestions(questionIndexes);
        return new CommandResult(MESSAGE_SUCCESS, CommandResultType.SHOW_SLIDESHOW);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof QuestionSlideshowCommand); // instanceof handles nulls
    }
}
