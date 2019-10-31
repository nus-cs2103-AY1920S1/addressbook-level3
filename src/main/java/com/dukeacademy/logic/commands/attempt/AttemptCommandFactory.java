package com.dukeacademy.logic.commands.attempt;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.state.ApplicationState;

/**
 * Factory class to represent all the necessary components for creating an AttemptCommand instance.
 */
public class AttemptCommandFactory implements CommandFactory {
    private final QuestionsLogic questionsLogic;
    private final ProgramSubmissionLogic programSubmissionLogic;
    private final ApplicationState applicationState;

    /**
     * Instantiates a new Attempt command factory.
     *
     * @param questionsLogic         the questions logic
     * @param programSubmissionLogic the program submission logic
     * @param applicationState       the applications state model
     */
    public AttemptCommandFactory(QuestionsLogic questionsLogic, ProgramSubmissionLogic programSubmissionLogic,
                                 ApplicationState applicationState) {
        this.questionsLogic = questionsLogic;
        this.programSubmissionLogic = programSubmissionLogic;
        this.applicationState = applicationState;
    }

    @Override
    public String getCommandWord() {
        return "attempt";
    }

    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        try {
            int index = Integer.parseInt(commandArguments.strip());
            return new AttemptCommand(index, questionsLogic, programSubmissionLogic, applicationState);
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentsException("Index should be a valid number.");
        }
    }
}
