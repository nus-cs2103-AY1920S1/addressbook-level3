package com.dukeacademy.logic.commands.submit;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;

/**
 * Class to represent the necessary components for the creation of a Submit command.
 */
public class SubmitCommandFactory implements CommandFactory {
    private QuestionsLogic questionsLogic;
    private ProgramSubmissionLogic programSubmissionLogic;

    public SubmitCommandFactory(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic) {
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
    }


    @Override
    public String getCommandWord() {
        return "submit";
    }

    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        if (!commandArguments.matches("\\s*")) {
            throw new InvalidCommandArgumentsException("Exit command does not take any arguments");
        }

        return new SubmitCommand(this.questionsLogic, this.programSubmissionLogic);
    }
}
