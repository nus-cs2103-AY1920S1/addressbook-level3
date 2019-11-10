package com.dukeacademy.logic.commands.submit;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.state.ApplicationState;

/**
 * Class to represent the necessary components for the creation of a Submit command.
 */
public class SubmitCommandFactory implements CommandFactory {
    private final QuestionsLogic questionsLogic;
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final ApplicationState applicationState;

    /**
     * Instantiates a new Submit command factory.
     *
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     * @param applicationState       the application state
     */
    public SubmitCommandFactory(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic,
                                ApplicationState applicationState) {
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
        this.applicationState = applicationState;
    }


    @Override
    public String getCommandWord() {
        return "submit";
    }

    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        if (!commandArguments.matches("\\s*")) {
            throw new InvalidCommandArgumentsException("Submit command does not take any arguments");
        }

        return new SubmitCommand(this.questionsLogic, this.programSubmissionLogic, applicationState);
    }
}
