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
import com.dukeacademy.ui.Dashboard;

/**
 * Encapsulates a command used to navigate to the Dashboard tab. Any unsaved work is automatically
 * saved before navigating to the Dashboard tab.
 */
public class DashboardCommand implements Command {
    private final Logger logger;
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final QuestionsLogic questionsLogic;
    private final ApplicationState applicationState;

    /**
     * Instantiates a new Dashboard command.
     *
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     * @param applicationState       the application state
     */
    public DashboardCommand(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic,
                             ApplicationState applicationState) {
        this.logger = LogsCenter.getLogger(Dashboard.class);
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

        applicationState.setCurrentActivity(Activity.DASHBOARD);

        return new CommandResult("Returning to your dashboard...", false);
    }

    /**
     * Helper method to save changes to a question.
     * @param oldQuestion the old question to be replaced
     * @param newQuestion the new question.
     */
    private void saveQuestion(Question oldQuestion, Question newQuestion) {
        this.questionsLogic.replaceQuestion(oldQuestion, newQuestion);
    }
}
