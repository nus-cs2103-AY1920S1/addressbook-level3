package com.dukeacademy.logic.commands.bookmark;

import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.question.Question;

/**
 * Command to delete a bookmark made on a question.
 */
public class DeleteBookmarkCommand implements Command {
    private final Logger logger;
    private final QuestionsLogic questionsLogic;
    private final int index;

    /**
     * Instantiates a new Delete Bookmark command.
     * @param index                  the index
     * @param questionsLogic         the questions logic
     */
    public DeleteBookmarkCommand(int index, QuestionsLogic questionsLogic) {
        this.logger = LogsCenter.getLogger(DeleteBookmarkCommand.class);
        this.index = index - 1;
        this.questionsLogic = questionsLogic;
    }

    /**
     * Executes the delete bookmark command.
     * Execution of this command is conditional in nature. If the question that user chooses to delete bookmark,
     * is indeed bookmarked, we will update the question to a non-bookmarked version of the same question.
     * Otherwise if the question that user selects is not bookmarked in the first place, we will simply notify
     * the user of that through the CLI feedback panel.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute() throws CommandException {
        try {
            Question userSelection = this.questionsLogic.getQuestion(index);
            boolean userSelectionIsBookmarked = userSelection.isBookmarked();

            if (userSelectionIsBookmarked) {
                // Update isBookmarked of question to false
                Question bookmarkedQuestion = userSelection.withNewIsBookmarked(false);
                this.questionsLogic.setQuestion(index, bookmarkedQuestion);
                logger.info("Deleted bookmark for question at index " + index + " : " + bookmarkedQuestion);

                // Notify user of successful bookmark action
                String feedback = "Deleted bookmark for question " + (index + 1) + " : "
                        + bookmarkedQuestion.getTitle();
                return new CommandResult(feedback, false, false
                );
            } else {
                // Simply notify user that question is not bookmarked
                String feedback = "Question " + (index + 1) + " : " + userSelection.getTitle()
                        + " - is not bookmarked.";
                return new CommandResult(feedback, false, false
                );
            }

        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("Index " + (index + 1) + " entered out of range for current list of questions.");
        }
    }

}
