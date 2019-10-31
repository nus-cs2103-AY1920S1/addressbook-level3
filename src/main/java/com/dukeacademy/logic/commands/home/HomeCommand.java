package com.dukeacademy.logic.commands.home;

import java.util.Optional;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.state.Activity;
import com.dukeacademy.model.state.ApplicationState;

/**
 * Home command used to exit the application. Any unsaved work is automatically
 * saved before the application is exited.
 */
public class HomeCommand implements Command {
    private final Logger logger;
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final QuestionsLogic questionsLogic;
    private final ApplicationState applicationState;

    /**
     * Instantiates a new Home command.
     *
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     * @param applicationState       the application state
     */
    public HomeCommand(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic,
                       ApplicationState applicationState) {
        this.logger = LogsCenter.getLogger(HomeCommand.class);
        this.programSubmissionLogic = programSubmissionLogic;
        this.questionsLogic = questionsLogic;
        this.applicationState = applicationState;
    }

    @Override
    public CommandResult execute() {
        Optional<Question> currentlyAttemptingQuestion = this.programSubmissionLogic.getCurrentQuestion();
        UserProgram latestUserProgram = this.programSubmissionLogic.getUserProgramFromSubmissionChannel();

        if (currentlyAttemptingQuestion.isPresent()) {
            Question oldQuestion = currentlyAttemptingQuestion.get();
            String loggerMessage = "Latest question attempt : " + oldQuestion.getTitle()
                    + "\nSaving latest user program : " + latestUserProgram.getSourceCode();

            logger.info(loggerMessage);

            Question newQuestion = oldQuestion.withNewUserProgram(latestUserProgram);
            saveQuestion(oldQuestion, newQuestion);
        } else {
            logger.info("No question attempt found. Skipping program save.");
        }

        applicationState.setCurrentActivity(Activity.HOME);

        return new CommandResult("Returning to home page...", false,
                false);
    }

    private void saveQuestion(Question oldQuestion, Question newQuestion) {
        this.questionsLogic.replaceQuestion(oldQuestion, newQuestion);
    }
}
