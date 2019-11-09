package com.dukeacademy.logic.commands.help;

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
 * Encapsulates a command used to navigate to the Help tab. Any unsaved work is automatically
 * saved before navigating to the Help tab.
 */
public class HelpCommand implements Command {
    private final Logger logger;
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final QuestionsLogic questionsLogic;
    private final ApplicationState applicationState;

    /**
     * Instantiates a new Help command.
     *
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     * @param applicationState       the application state
     */
    public HelpCommand(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic,
                       ApplicationState applicationState) {
        this.logger = LogsCenter.getLogger(HelpCommand.class);
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

        applicationState.setCurrentActivity(Activity.HELP);

        return new CommandResult("We are here to help!", false);
    }

    private void saveQuestion(Question oldQuestion, Question newQuestion) {
        this.questionsLogic.replaceQuestion(oldQuestion, newQuestion);
    }
}
