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

/**
 * Command for attempting a question. This command loads the selected question into the registered
 * ProgramSubmissionLogic instance.
 */
public class AttemptCommand implements Command {
    private final Logger logger;
    private final QuestionsLogic questionsLogic;
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final int index;

    /**
     * Instantiates a new Attempt command.
     *
     * @param index                  the index
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     */
    public AttemptCommand(int index, QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic) {
        this.logger = LogsCenter.getLogger(AttemptCommand.class);
        this.index = index - 1;
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            Question userSelection = this.questionsLogic.getQuestion(index);
            Status userSelectionStatus = userSelection.getStatus();

            if (userSelectionStatus == Status.PASSED) {
                logger.info("Reattempting question at index " + index + " : " + userSelection);

                // Set current attempting question
                this.programSubmissionLogic.setCurrentQuestion(userSelection);

                // Notify user that he has already passed this question
                String feedback = "Reattempting question " + (index + 1) + " : " + userSelection.getTitle() + " - "
                        + "You have already passed this question successfully.";
                return new CommandResult(feedback, false, false, false, false, false,
                        true);
            } else {
                // Update status of question to ATTEMPTED
                Question questionToAttempt = this.questionsLogic.getQuestion(index).withNewStatus(Status.ATTEMPTED);
                this.questionsLogic.setQuestion(index, questionToAttempt);
                logger.info("Attempting question at index " + index + " : " + questionToAttempt);

                // Set current attempting question
                this.programSubmissionLogic.setCurrentQuestion(questionToAttempt);

                String feedback = "Attempting question " + (index + 1) + " : " + questionToAttempt.getTitle();
                return new CommandResult(feedback, false, false, false, false, false,
                        true);
            }

        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("Index " + (index + 1) + " entered out of range for current list of questions.");
        }
    }
}
