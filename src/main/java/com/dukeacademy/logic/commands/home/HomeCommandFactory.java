package com.dukeacademy.logic.commands.home;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;

/**
 * Factory class encapsulating the necessary components for the creation of a Exit command instance.
 */
public class HomeCommandFactory implements CommandFactory {
    private final QuestionsLogic questionsLogic;
    private final ProgramSubmissionLogic programSubmissionLogic;

    /**
     * Instantiates a new Home command factory.
     *
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     */
    public HomeCommandFactory(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic) {
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
    }

    @Override
    public String getCommandWord() {
        return "home";
    }

    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        if (!"".equals(commandArguments)) {
            throw new InvalidCommandArgumentsException("Home command does not "
                + "take any arguments");
        }

        return new HomeCommand(this.questionsLogic, this.programSubmissionLogic);
    }
}
