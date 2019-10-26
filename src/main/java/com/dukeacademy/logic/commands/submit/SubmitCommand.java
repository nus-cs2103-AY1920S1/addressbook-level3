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

    public SubmitCommand(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic) {
        this.logger = LogsCenter.getLogger(SubmitCommand.class);
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Optional<Question> currentlyAttemptingQuestion = this.programSubmissionLogic.getCurrentQuestion();
        UserProgram userProgram = programSubmissionLogic.getUserProgramFromSubmissionChannel();

        if (currentlyAttemptingQuestion.isEmpty()) {
            logger.warning("No question being attempted at the moment, command will not be executed");
            throw new CommandException("You have not attempted a question yet.");
        }

        // Save the user's program first
        logger.info("Saving user program first : " + userProgram);
        Question question = currentlyAttemptingQuestion.get();
        Question questionWithNewProgram = question.withNewStatus(Status.ATTEMPTED).withNewUserProgram(userProgram);
        this.questionsLogic.replaceQuestion(question, questionWithNewProgram);

        // Submit the user's program
        Optional<TestResult> resultsOptional;
        try {
            logger.info("Starting test execution for : " + question + "\nUser program : " + userProgram);
            resultsOptional = this.programSubmissionLogic.submitUserProgram(userProgram);
        } catch (IncorrectCanonicalNameException e) {
            logger.warning("Main class not detected, command will not be executed");
            throw new CommandException("Main class needed as entry point.");
        } catch (EmptyUserProgramException e) {
            logger.warning("Program is empty, command will not be executed");
            throw new CommandException("Program must not be empty.");
        }

        if (resultsOptional.isEmpty()) {
            logger.warning("Submit command failed unexpectedly");
            throw new CommandException("Tests failed unexpectedly. Please report this bug at "
                    + "https://github.com/AY1920S1-CS2103T-F14-1/main");
        }

        // Update question with result status
        boolean isSuccessful = resultsOptional.get().getNumPassed() == questionWithNewProgram.getTestCases().size();

        if (isSuccessful) {
            Question successfulQuestion = questionWithNewProgram.withNewStatus(Status.PASSED);
            this.questionsLogic.replaceQuestion(questionWithNewProgram, successfulQuestion);
        } else {
            Question failedQuestion = questionWithNewProgram.withNewStatus(Status.ATTEMPTED);
            this.questionsLogic.replaceQuestion(questionWithNewProgram, failedQuestion);
        }

        // Give user feedback
        String feedback = "Submitted your program for question : " + question.getTitle() + "\nResult : ";
        if (isSuccessful) {
            feedback = feedback + "success";
        } else {
            feedback = feedback + "failed";
        }

        logger.info("Test execution succeeded : " + isSuccessful);
        return new CommandResult(feedback, false, false);
    }
}
