package com.dukeacademy.logic.commands.list;

import java.util.function.Predicate;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.question.Question;

/**
 * Command for attempting a question. This command loads the selected question into the registered
 * ProgramSubmissionLogic instance.
 */
public class ListCommand implements Command {
    private final Logger logger;
    private final Predicate<Question> allQuestions = unused -> true;
    private final QuestionsLogic questionsLogic;

    /**
     * Instantiates a new Attempt command.
     *
     * @param questionsLogic         the questions logic
     */
    public ListCommand(QuestionsLogic questionsLogic) {
        this.logger = LogsCenter.getLogger(ListCommand.class);
        this.questionsLogic = questionsLogic;
    }

    @Override
    public CommandResult execute() throws CommandException {
        // Update status of question
        this.questionsLogic.filterQuestionsList(allQuestions);
        logger.info("Listing all questions...");
        String feedback = "List all questions...";
        return new CommandResult(feedback, false, false, false, false);
    }
}

