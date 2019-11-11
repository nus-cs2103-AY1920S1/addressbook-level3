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
     * Returns the question that the user selected to bookmark
     * @return user selected question
     */
    public Question getUserSelectedQuestion() {
        Question userSelection = this.questionsLogic.getQuestion(id);
        return userSelection;
    }

    /**
     * Returns a command result that notifies the user that no action is taken
     * @return command result
     */
    public CommandResult notifyUserNoActionTaken() {
        // Simply notify user that question is already bookmarked
        String feedback = "Question " + id + " : " + getUserSelectedQuestion().getTitle()
                + " - is already bookmarked.";
        return new CommandResult(feedback, false);
    }

    /**
     * Bookmarks the question that the user selected
     */
    public void bookmarkUserSelectedQuestion() {
        Question bookmarkedVersion = getUserSelectedQuestion().withNewIsBookmarked(true);
        this.questionsLogic.setQuestion(id, bookmarkedVersion);
    }

    /**
     * Returns a command result that notifies the user that bookmark was added successfully
     * @return
     */
    public CommandResult notifyUserBookmarkSuccess() {
        String feedback = "Bookmarked question " + id + " : " + getUserSelectedQuestion().getTitle();
        return new CommandResult(feedback, false);
    }

    /**
     * Executes the bookmark command.
     * Execution of this command is conditional in nature. If question that user chooses to bookmark
     * is already bookmarked, we will simply notify the user of that through the CLI feedback panel.
     * Otherwise if the question that user selects is not already bookmarked, we will update the question
     * with a bookmarked version of the same question and notify user of a successful bookmark action.
     * @return CommandResult
     * @throws CommandException
     */
    @Override
    public CommandResult execute() throws CommandException {
        try {
            Question userSelectedQuestion = getUserSelectedQuestion();
            boolean userSelectedQuestionIsBookmarked = userSelectedQuestion.isBookmarked();

            if (userSelectedQuestionIsBookmarked) {
                // Notify user that question selected is already bookmarked, hence no action is taken
                return notifyUserNoActionTaken();
            } else {
                bookmarkUserSelectedQuestion();

                // Logging as part of defensive programming / developer testing
                logger.info("Bookmarked question " + id);

                return notifyUserBookmarkSuccess();
            }

        } catch (QuestionNotFoundRuntimeException e) {
            throw new CommandException("No question with id  " + id + " found.");
        }
    }

}
