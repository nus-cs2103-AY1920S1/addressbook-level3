package com.dukeacademy.logic.commands.browse;

import java.util.Arrays;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.question.QuestionsLogic;

/**
 * The type Browse command.
 */
public class BrowseCommand implements Command {
    private final Logger logger;
    private final QuestionsLogic questionsLogic;
    private final AttributeContainsKeywordsPredicate predicate;

    /**
     * Instantiates a new Browse command.
     *
     * @param questionsLogic the questions logic
     * @param keywords       the keywords
     */
    public BrowseCommand(QuestionsLogic questionsLogic, String keywords) {
        this.logger = LogsCenter.getLogger(BrowseCommand.class);
        this.questionsLogic = questionsLogic;
        String[] attributeKeywords = keywords.trim().split("\\s+");
        this.predicate =
            new AttributeContainsKeywordsPredicate(Arrays.asList(attributeKeywords));
    }

    @Override
    public CommandResult execute() throws CommandException {
        // Update status of question
        this.questionsLogic.filterQuestionsList(predicate);
        logger.info("Listing questions that contains keywords specified.");
        String feedback = "List all questions that contains the corresponding"
            + " keywords as long as they appear in title, topics, description,"
            + " status or difficulty.";
        return new CommandResult(feedback, false, false, false, false, false,
                false);
    }
}
