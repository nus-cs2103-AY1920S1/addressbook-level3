package com.dukeacademy.logic.commands.exit;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;

import java.util.Optional;
import java.util.logging.Logger;

public class ExitCommand implements Command {
    private final Logger logger;
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final QuestionsLogic questionsLogic;

    public ExitCommand(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic) {
        this.logger = LogsCenter.getLogger(ExitCommand.class);
        this.programSubmissionLogic = programSubmissionLogic;
        this.questionsLogic = questionsLogic;
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

        return new CommandResult("Exiting application...", false, true);
    }

    private void saveQuestion(Question oldQuestion, Question newQuestion) {
        this.questionsLogic.replaceQuestion(oldQuestion, newQuestion);
    }
}
