package com.dukeacademy.logic.commands.submit;

import java.util.Optional;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.program.TestResult;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.state.Activity;
import com.dukeacademy.model.state.ApplicationState;
import com.dukeacademy.testexecutor.exceptions.EmptyUserProgramException;
import com.dukeacademy.testexecutor.exceptions.IncorrectCanonicalNameException;

/**
 * Submit command that submits the user's current work from the registered UserProgram channel of the
 * ProgramSubmissionLogic.
 */
public class SubmitCommand implements Command {
    private final Logger logger;
    private final QuestionsLogic questionsLogic;
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final ApplicationState applicationState;

    /**
     * Instantiates a new Submit command.
     *
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     * @param applicationState
     */
    public SubmitCommand(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic,
                         ApplicationState applicationState) {
        this.logger = LogsCenter.getLogger(SubmitCommand.class);
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
        this.applicationState = applicationState;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Optional<Question> currentlyAttemptingQuestion = this.programSubmissionLogic.getCurrentQuestion();
        UserProgram userProgram = programSubmissionLogic.getUserProgramFromSubmissionChannel();

        // The submit command cannot be executed if the user has not attempted any question
        if (currentlyAttemptingQuestion.isEmpty()) {
            logger.warning("No question being attempted at the moment, command will not be executed");
            throw new CommandException("You have not attempted a question yet.");
        }

        // Set the UI to focus on the Workspace page for display of results
        applicationState.setCurrentActivity(Activity.WORKSPACE);

        // Save the user's new program first
        logger.info("Saving user program first : " + userProgram);
        Question question = currentlyAttemptingQuestion.get();
        Question questionWithNewProgram = question.withNewUserProgram(userProgram);
        this.updateQuestion(question, questionWithNewProgram);

        // Submit the user's program
        Optional<TestResult> resultsOptional;
        try {
            logger.info("Starting test execution for : " + question + "\nUser program : " + userProgram);
            resultsOptional = this.programSubmissionLogic.submitUserProgram(userProgram);
        } catch (IncorrectCanonicalNameException e) {
            logger.warning("Main class not detected, command will not be executed");
            throw new CommandException("Please write your main method in a class called Main");
        } catch (EmptyUserProgramException e) {
            logger.warning("Program is empty, command will not be executed");
            throw new CommandException("Program must not be empty.");
        }

        // Unexpected error with the program evaluation
        if (resultsOptional.isEmpty()) {
            logger.warning("Submit command failed unexpectedly");
            throw new CommandException("Tests failed unexpectedly. Please report this bug at "
                    + "https://github.com/AY1920S1-CS2103T-F14-1/main");
        }

        // Check if the result was successful
        boolean isSuccessful = resultsOptional.get().getNumPassed() == questionWithNewProgram.getTestCases().size();

        if (isSuccessful) {
            // Save the new success status
            Question successfulQuestion = questionWithNewProgram.withNewStatus(Status.PASSED);
            this.updateQuestion(questionWithNewProgram, successfulQuestion);
        }

        // Give user feedback
        String feedback = "Submitted your program for question : " + question.getTitle() + "\nResult : ";
        if (isSuccessful) {
            feedback = feedback + "success";
        } else {
            feedback = feedback + "failed";
        }

        return new CommandResult(feedback, false);
    }

    /**
     * Helper method to automatically update the current attempting question in both the QuestionsLogic and the
     * ProgramSubmissionLogic
     * @param oldQuestion the old question
     * @param newQuestion the new question
     */
    private void updateQuestion(Question oldQuestion , Question newQuestion) {
        this.programSubmissionLogic.setCurrentQuestion(newQuestion);
        this.questionsLogic.replaceQuestion(oldQuestion, newQuestion);
    }
}
