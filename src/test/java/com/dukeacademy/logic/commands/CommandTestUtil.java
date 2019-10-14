package com.dukeacademy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dukeacademy.commons.core.index.Index;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.parser.CliSyntax;
import com.dukeacademy.model.Model;
import com.dukeacademy.model.QuestionBank;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.TitleContainsKeywordsPredicate;
import com.dukeacademy.testutil.Assert;
import com.dukeacademy.testutil.EditQuestionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TITLE_AMY = "Amy Bee";
    public static final String VALID_TITLE_BOB = "Bob Choo";
    public static final String VALID_TOPIC_AMY = "11111111";
    public static final String VALID_TOPIC_BOB = "22222222";
    public static final String VALID_STATUS_AMY = "amy@example.com";
    public static final String VALID_STATUS_BOB = "bob@example.com";
    public static final String VALID_DIFFICULTY_AMY = "Block 312, Amy Street 1";
    public static final String VALID_DIFFICULTY_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String TITLE_DESC_AMY = " " + CliSyntax.PREFIX_TITLE + VALID_TITLE_AMY;
    public static final String TITLE_DESC_BOB = " " + CliSyntax.PREFIX_TITLE + VALID_TITLE_BOB;
    public static final String TOPIC_DESC_AMY = " " + CliSyntax.PREFIX_TOPIC + VALID_TOPIC_AMY;
    public static final String TOPIC_DESC_BOB = " " + CliSyntax.PREFIX_TOPIC + VALID_TOPIC_BOB;
    public static final String STATUS_DESC_AMY = " " + CliSyntax.PREFIX_STATUS + VALID_STATUS_AMY;
    public static final String STATUS_DESC_BOB = " " + CliSyntax.PREFIX_STATUS + VALID_STATUS_BOB;
    public static final String DIFFICULTY_DESC_AMY = " " + CliSyntax.PREFIX_DIFFICULTY + VALID_DIFFICULTY_AMY;
    public static final String DIFFICULTY_DESC_BOB = " " + CliSyntax.PREFIX_DIFFICULTY + VALID_DIFFICULTY_BOB;
    public static final String TAG_DESC_FRIEND = " " + CliSyntax.PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + CliSyntax.PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_TITLE_DESC =
        " " + CliSyntax.PREFIX_TITLE; // '&' not allowed in names
    public static final String INVALID_TOPIC_DESC =
        " " + CliSyntax.PREFIX_TOPIC; // 'a' not allowed in phones
    public static final String INVALID_STATUS_DESC =
        " " + CliSyntax.PREFIX_STATUS; // missing '@' symbol
    public static final String INVALID_DIFFICULTY_DESC = " "
        + CliSyntax.PREFIX_DIFFICULTY; // empty string not allowed for
    // addresses
    public static final String INVALID_TAG_DESC =
        " " + CliSyntax.PREFIX_TAG; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditQuestionDescriptor DESC_AMY;
    public static final EditCommand.EditQuestionDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditQuestionDescriptorBuilder().withTitle(VALID_TITLE_AMY)
                                                      .withTopic(VALID_TOPIC_AMY)
                                                      .withStatus(VALID_STATUS_AMY)
                                                      .withDifficulty(VALID_DIFFICULTY_AMY)
                                                      .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditQuestionDescriptorBuilder().withTitle(VALID_TITLE_BOB)
                                                      .withTopic(VALID_TOPIC_BOB)
                                                      .withStatus(VALID_STATUS_BOB)
                                                      .withDifficulty(VALID_DIFFICULTY_BOB)
                                                      .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the question bank, filtered question list and selected question in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        QuestionBank expectedQuestionBank = new QuestionBank(actualModel.getQuestionBank());
        List<Question> expectedFilteredList = new ArrayList<>(actualModel.getFilteredQuestionList());

        Assert.assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedQuestionBank, actualModel.getQuestionBank());
        assertEquals(expectedFilteredList, actualModel.getFilteredQuestionList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the question at the given {@code targetIndex} in the
     * {@code model}'s question bank.
     */
    public static void showQuestionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredQuestionList().size());

        Question question = model.getFilteredQuestionList().get(targetIndex.getZeroBased());
        final String[] splitName = question.getTitle().fullTitle.split("\\s+");
        model.updateFilteredQuestionList(new TitleContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredQuestionList().size());
    }

}
