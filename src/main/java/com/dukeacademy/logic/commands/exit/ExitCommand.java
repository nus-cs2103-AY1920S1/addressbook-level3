package com.dukeacademy.logic.commands.exit;

import java.util.Optional;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.notes.NotesLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.Status;

/**
 * Exit command used to exit the application. Any unsaved work is automatically saved before the application is exited.
 */
public class ExitCommand implements Command {
    private final Logger logger;
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final QuestionsLogic questionsLogic;
    private final NotesLogic notesLogic;

    /**
     * Instantiates a new Exit command.
     *
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     */
    public ExitCommand(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic,
                       NotesLogic notesLogic) {
        this.logger = LogsCenter.getLogger(ExitCommand.class);
        this.programSubmissionLogic = programSubmissionLogic;
        this.questionsLogic = questionsLogic;
        this.notesLogic = notesLogic;
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

            Question newQuestion = oldQuestion.withNewUserProgram(latestUserProgram).withNewStatus(Status.ATTEMPTED);
            saveQuestion(oldQuestion, newQuestion);
        } else {
            logger.info("No question attempt found. Skipping program save.");
        }

        logger.info("Attempting to save latest note...");
        this.notesLogic.saveNoteFromNoteSubmissionChannel();

        return new CommandResult("Exiting application...", true);
    }

    /**
     * Helper method to save changes to a question.
     *
     * @param oldQuestion the old question to be replaced
     * @param newQuestion the new question.
     */
    private void saveQuestion(Question oldQuestion, Question newQuestion) {
        this.questionsLogic.replaceQuestion(oldQuestion, newQuestion);
    }
}
