package com.dukeacademy.logic.commands.exit;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;

public class ExitCommandFactory implements CommandFactory {
    private QuestionsLogic questionsLogic;
    private ProgramSubmissionLogic programSubmissionLogic;

    public ExitCommandFactory(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic) {
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
    }

    @Override
    public String getCommandWord() {
        return "exit";
    }

    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        if (!commandArguments.equals("")) {
            throw new InvalidCommandArgumentsException("Exit command does not take any arguments");
        }

        return new ExitCommand(this.questionsLogic, this.programSubmissionLogic);
    }
}
