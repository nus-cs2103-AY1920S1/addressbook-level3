package com.dukeacademy.logic.commands.list;

import java.util.function.Predicate;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.state.Activity;
import com.dukeacademy.model.state.ApplicationState;

/**
 * Command for attempting a question. This command loads the selected question into the registered
 * ProgramSubmissionLogic instance.
 */
public class ListCommand implements Command {
    private final Logger logger;
    private final Predicate<Question> allQuestions = unused -> true;
    private final QuestionsLogic questionsLogic;
    private final ApplicationState applicationState;

    /**
     * Instantiates a new Attempt command.
     *
     * @param questionsLogic   the questions logic
     * @param applicationState the application state
     */
    public ListCommand(QuestionsLogic questionsLogic, ApplicationState applicationState) {
        this.logger = LogsCenter.getLogger(ListCommand.class);
        this.questionsLogic = questionsLogic;
        this.applicationState = applicationState;
    }

    @Override
    public CommandResult execute() {
        // Update status of question
        this.questionsLogic.filterQuestionsList(allQuestions);
        logger.info("Listing all questions...");
        String feedback = "List all questions...";

        // Update the app's current activity
        applicationState.setCurrentActivity(Activity.QUESTION);
        return new CommandResult(feedback, false);
    }
}

