package com.dukeacademy.logic.commands.attempt;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;

/**
 * Factory class to represent all the necessary components for creating an AttemptCommand instance.
 */
public class AttemptCommandFactory implements CommandFactory {
    private QuestionsLogic questionsLogic;
    private ProgramSubmissionLogic programSubmissionLogic;

    public AttemptCommandFactory(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic) {
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
    }

    @Override
    public String getCommandWord() {
        return "attempt";
    }

    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        try {
            int index = Integer.parseInt(commandArguments.strip());
            return new AttemptCommand(index, questionsLogic, programSubmissionLogic);
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentsException("Index should be a valid number.");
        }
    }
}
