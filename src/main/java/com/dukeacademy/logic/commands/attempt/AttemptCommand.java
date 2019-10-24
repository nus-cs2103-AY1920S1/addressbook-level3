package com.dukeacademy.logic.commands.attempt;

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
    private QuestionsLogic questionsLogic;
    private ProgramSubmissionLogic programSubmissionLogic;
    private int index;

    public AttemptCommand(int index, QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic) {
        this.index = index - 1;
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            // Update status of question
            Question questionToAttempt = this.questionsLogic.getQuestion(index).withNewStatus(Status.ATTEMPTED);
            this.questionsLogic.setQuestion(index, questionToAttempt);

            // Set current attempting question
            this.programSubmissionLogic.setCurrentQuestion(questionToAttempt);

            String feedback = "Attempting question " + (index + 1) + " : " + questionToAttempt.getTitle();
            return new CommandResult(feedback, false, false);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("Index entered out of range for current list of questions.");
        }
    }
}
