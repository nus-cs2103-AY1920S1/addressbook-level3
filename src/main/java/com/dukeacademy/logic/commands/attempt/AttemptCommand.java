package com.dukeacademy.logic.commands.attempt;

import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.exceptions.QuestionNotFoundRuntimeException;
import com.dukeacademy.model.state.Activity;
import com.dukeacademy.model.state.ApplicationState;

/**
 * Command for attempting a question. This command loads the selected question into the registered
 * ProgramSubmissionLogic instance.
 */
public class AttemptCommand implements Command {
    private final Logger logger;
    private final QuestionsLogic questionsLogic;
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final ApplicationState applicationState;
    private final int id;

    /**
     * Instantiates a new Attempt command.
     *
     * @param id                  the index
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     */
    public AttemptCommand(int id, QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic,
                          ApplicationState applicationState) {
        this.logger = LogsCenter.getLogger(AttemptCommand.class);
        this.id = id;
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
        this.applicationState = applicationState;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            Question userSelection = this.questionsLogic.getQuestion(id);
            Status userSelectionStatus = userSelection.getStatus();

            if (userSelectionStatus == Status.PASSED) {
                logger.info("Reattempting question at index " + id + " : " + userSelection);

                // Set current attempting question
                this.programSubmissionLogic.setCurrentQuestion(userSelection);

                // Notify user that he has already passed this question
                String feedback = "Reattempting question " + id + " : " + userSelection.getTitle() + " - "
                        + "You have already passed this question successfully.";

                // Update app's current activity
                this.applicationState.setCurrentActivity(Activity.WORKSPACE);

                return new CommandResult(feedback, false);
            } else {
                // Update status of question to ATTEMPTED
                Question questionToAttempt = this.questionsLogic.getQuestion(id).withNewStatus(Status.ATTEMPTED);
                this.questionsLogic.setQuestion(id, questionToAttempt);
                logger.info("Attempting question at index " + id + " : " + questionToAttempt);

                // Set current attempting question
                this.programSubmissionLogic.setCurrentQuestion(questionToAttempt);

                String feedback = "Attempting question " + id + " : " + questionToAttempt.getTitle();

                // Update app's current activity
                this.applicationState.setCurrentActivity(Activity.WORKSPACE);

                return new CommandResult(feedback, false);
            }

        } catch (QuestionNotFoundRuntimeException e) {
            throw new CommandException("No question with id  " + id + " found.");
        }
    }
}
