package com.dukeacademy.logic.commands.view;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.State.Activity;
import com.dukeacademy.model.State.ApplicationState;
import com.dukeacademy.model.question.Question;

/**
 * Command for viewing a question.
 */
public class ViewCommand implements Command {
    private final QuestionsLogic questionsLogic;
    private final ApplicationState applicationState;
    private final int index;

    /**
     * Instantiates a new View command.
     *
     * @param index                 the index
     * @param questionsLogic        the questions logic
     */
    public ViewCommand(int index, QuestionsLogic questionsLogic, ApplicationState applicationState) {
        this.index = index - 1;
        this.questionsLogic = questionsLogic;
        this.applicationState = applicationState;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            // Update status of question
            Question questionToView =
                this.questionsLogic.getQuestion(index);
            this.questionsLogic.selectQuestion(index);

            String feedback =
                "Viewing question " + (index + 1) + " : " + questionToView.getTitle();

            // Update the app's current activity
            applicationState.setCurrentActivity(Activity.QUESTION);

            return new CommandResult(feedback, false, false
            );
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("Index entered out of range for current list of questions.");
        }
    }
}

