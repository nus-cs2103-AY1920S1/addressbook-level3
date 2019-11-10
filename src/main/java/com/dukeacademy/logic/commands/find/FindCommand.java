package com.dukeacademy.logic.commands.find;

import java.util.Arrays;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.state.Activity;
import com.dukeacademy.model.state.ApplicationState;

/**
 * The type Find command.
 */
public class FindCommand implements Command {
    private final Logger logger;
    private final QuestionsLogic questionsLogic;
    private final ApplicationState applicationState;
    private final TitleContainsKeywordsPredicate predicate;

    /**
     * Instantiates a new Find command.
     *
     * @param questionsLogic the questions logic
     * @param keywords       the keywords
     */
    public FindCommand(QuestionsLogic questionsLogic, ApplicationState applicationState, String keywords) {
        this.logger = LogsCenter.getLogger(FindCommand.class);
        this.questionsLogic = questionsLogic;
        this.applicationState = applicationState;
        String[] titleKeywords = keywords.trim().split("\\s+");
        this.predicate =
            new TitleContainsKeywordsPredicate(Arrays.asList(titleKeywords));
    }

    @Override
    public CommandResult execute() throws CommandException {
        // Update status of question
        this.questionsLogic.filterQuestionsList(predicate);
        logger.info("Listing questions that contains keywords specified.");
        String feedback = "List all questions that contains the corresponding"
            + " keywords.";
        applicationState.setCurrentActivity(Activity.QUESTION);
        return new CommandResult(feedback, false);
    }
}
