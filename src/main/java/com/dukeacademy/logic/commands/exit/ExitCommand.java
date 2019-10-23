package com.dukeacademy.logic.commands.exit;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;

public class ExitCommand implements Command {
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final QuestionsLogic questionsLogic;

    public ExitCommand(ProgramSubmissionLogic programSubmissionLogic, QuestionsLogic questionsLogic) {
        this.programSubmissionLogic = programSubmissionLogic;
        this.questionsLogic = questionsLogic;
    }

    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult("Exiting application...");
    }
}
