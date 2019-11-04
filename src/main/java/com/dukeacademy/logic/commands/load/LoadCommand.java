package com.dukeacademy.logic.commands.load;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.commons.util.FileUtil;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.question.Question;

/**
 * The type Load command.
 */
public class LoadCommand implements Command {

    private final QuestionsLogic questionsLogic;
    private final List<Question> questionList = new ArrayList<>();

    /**
     * Instantiates a new Load command.
     *
     * @param questionsLogic     the questions logic
     * @param sampleQuestionPath the path whereby questions are stored
     */
    public LoadCommand(QuestionsLogic questionsLogic, Path sampleQuestionPath) {
        final Logger logger = LogsCenter.getLogger(LoadCommand.class);
        this.questionsLogic = questionsLogic;
        String questions = "";
        try {
            questions = FileUtil.readFromFile(sampleQuestionPath);
        } catch (IOException e) {
            logger.info("The Question resource file cannot be opened.");
        }

        try {
            questionList.addAll(QuestionLoader.buildFromString(questions));
        } catch (IllegalValueException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        questionsLogic.addQuestions(questionList);

        // Give user feedback
        String feedback = "Added " + questionList.size() + " questions to question "
            + "bank!";
        if (questionList.size() == 0) {
            feedback = "Sorry. Couldn't manage to load any new questions.";
        }
        return new CommandResult(feedback, false);
    }

}
