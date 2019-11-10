package com.dukeacademy.logic.commands.load;

import java.nio.file.Path;

import com.dukeacademy.MainApp;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.question.QuestionsLogic;

/**
 * Class to represent the necessary components for the creation of a Load command.
 */
public class LoadCommandFactory implements CommandFactory {
    private final QuestionsLogic questionsLogic;

    /**
     * Instantiates a new Load command factory.
     *
     * @param questionsLogic the questions logic
     */
    public LoadCommandFactory(QuestionsLogic questionsLogic) {
        this.questionsLogic = questionsLogic;
    }


    @Override
    public String getCommandWord() {
        return "loadquestions";
    }

    @Override
    public Command getCommand(String commandArguments)
        throws InvalidCommandArgumentsException {
        Path sampleQuestionsFilePath =
            MainApp.getConfig().getLoadPath().resolve(commandArguments);
        if (!sampleQuestionsFilePath.toFile().exists()) {
            throw new InvalidCommandArgumentsException("We cannot find the "
                + "specified text file titled \"" + commandArguments + "\" "
                + "inside the \"DukeAcademy/newQuestions\" directory.\n"
                + "Did you type the file name wrongly?");
        }
        return new LoadCommand(this.questionsLogic, sampleQuestionsFilePath);
    }
}
