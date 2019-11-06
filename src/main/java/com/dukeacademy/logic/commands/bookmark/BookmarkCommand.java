package com.dukeacademy.logic.commands.bookmark;

import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.exceptions.QuestionNotFoundRuntimeException;

/**
 * Command to bookmark a question.
 */
public class BookmarkCommand implements Command {
    private final Logger logger;
    private final QuestionsLogic questionsLogic;
    private final int id;

    /**
     * Instantiates a new Bookmark command.
     * @param id                  the index
     * @param questionsLogic         the questions logic
     */
    public BookmarkCommand(int id, QuestionsLogic questionsLogic) {
        this.logger = LogsCenter.getLogger(BookmarkCommand.class);
        this.id = id;
        this.questionsLogic = questionsLogic;
    }

    /**
     * Executes the bookmark command.
     * Execution of this command is conditional in nature. If question that user chooses to bookmark
     * is already bookmarked, we will simply notify the user of that through the CLI feedback panel.
     * Otherwise if the question that user selects is not already bookmarked, we will update the question
     * with a bookmarked version of the same question and notify user of a successful bookmark action.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute() throws CommandException {
        try {
            Question userSelection = this.questionsLogic.getQuestion(id);
            boolean userSelectionIsBookmarked = userSelection.isBookmarked();

            if (userSelectionIsBookmarked) {
                // Simply notify user that question is already bookmarked
                String feedback = "Question " + id + " : " + userSelection.getTitle()
                        + " - is already bookmarked.";
                return new CommandResult(feedback, false);
            } else {
                // Update isBookmarked of question
                Question bookmarkedQuestion = userSelection.withNewIsBookmarked(true);
                this.questionsLogic.setQuestion(id, bookmarkedQuestion);
                logger.info("Bookmarked question " + id + " : " + bookmarkedQuestion);

                // Notify user of successful bookmark action
                String feedback = "Bookmarked question " + id + " : " + bookmarkedQuestion.getTitle();
                return new CommandResult(feedback, false);
            }

        } catch (QuestionNotFoundRuntimeException e) {
            throw new CommandException("No question with id  " + id + " found.");
        }
    }

}
