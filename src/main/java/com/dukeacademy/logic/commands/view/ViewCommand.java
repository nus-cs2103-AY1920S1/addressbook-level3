package com.dukeacademy.logic.commands.view;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.exceptions.QuestionNotFoundRuntimeException;
import com.dukeacademy.model.state.Activity;
import com.dukeacademy.model.state.ApplicationState;

/**
 * Command for viewing a question.
 */
public class ViewCommand implements Command {
    private final QuestionsLogic questionsLogic;
    private final ApplicationState applicationState;
    private final int id;

    /**
     * Instantiates a new View command.
     *
     * @param id                 the index
     * @param questionsLogic        the questions logic
     */
    public ViewCommand(int id, QuestionsLogic questionsLogic, ApplicationState applicationState) {
        this.id = id;
        this.questionsLogic = questionsLogic;
        this.applicationState = applicationState;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            // Update status of question
            Question questionToView =
                this.questionsLogic.getQuestion(id);
            this.questionsLogic.selectQuestion(id);

            String feedback =
                "Viewing question " + id + " : " + questionToView.getTitle();

            // Update the app's current activity
            applicationState.setCurrentActivity(Activity.QUESTION);

            return new CommandResult(feedback, false
            );
        } catch (QuestionNotFoundRuntimeException e) {
            throw new CommandException("No question with id  " + id + " found.");
        }
    }
}

