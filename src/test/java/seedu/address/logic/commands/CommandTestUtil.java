package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.note.EditNoteCommand;
import seedu.address.logic.commands.questioncommands.EditQuestionCommand;
import seedu.address.model.AppData;
import seedu.address.model.Model;
import seedu.address.model.note.Note;
import seedu.address.model.note.TitleContainsKeywordsPredicate;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionContainsKeywordsPredicate;
import seedu.address.testutil.EditNoteDescriptorBuilder;
import seedu.address.testutil.EditQuestionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_TITLE_AMY = "Amy Bee";
    public static final String VALID_TITLE_BOB = "Bob Choo";
    public static final String VALID_CONTENT_AMY = "Block 312, Amy Street 1";
    public static final String VALID_CONTENT_BOB = "Block 123, Bobby Street 3";

    public static final String TITLE_DESC_AMY = " " + PREFIX_TITLE + VALID_TITLE_AMY;
    public static final String TITLE_DESC_BOB = " " + PREFIX_TITLE + VALID_TITLE_BOB;
    public static final String CONTENT_DESC_AMY = " " + PREFIX_CONTENT + VALID_CONTENT_AMY;
    public static final String CONTENT_DESC_BOB = " " + PREFIX_CONTENT + VALID_CONTENT_BOB;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "f\nsecond"; // \n not allowed for titles
    public static final String INVALID_CONTENT_DESC = " " + PREFIX_CONTENT; // empty not allowed for content

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditNoteCommand.EditNoteDescriptor DESC_AMY;
    public static final EditNoteCommand.EditNoteDescriptor DESC_BOB;

    // question
    public static final String VALID_QUESTION_BODY_ALGEBRA = "1 + 1 = ";
    public static final String VALID_QUESTION_BODY_CONCEPT = "(  ) is a measure of the degree of dependence between "
            + "components, classes, methods, etc.";
    public static final String VALID_ANSWER_ALGEBRA = "2";
    public static final String VALID_ANSWER_CONCEPT = "Coupling";
    public static final String VALID_SUBJECT_ALGEBRA = "Math";
    public static final String VALID_SUBJECT_CONCEPT = "CS2103T";
    public static final String VALID_DIFFICULTY_ALGEBRA = "easy";
    public static final String VALID_DIFFICULTY_CONCEPT = "medium";

    public static final String QUESTION_BODY_DESC_ALGEBRA = " " + PREFIX_QUESTION + VALID_QUESTION_BODY_ALGEBRA;
    public static final String QUESTION_BODY_DESC_CONCEPT = " " + PREFIX_QUESTION + VALID_QUESTION_BODY_CONCEPT;
    public static final String ANSWER_DESC_ALGEBRA = " " + PREFIX_ANSWER + VALID_ANSWER_ALGEBRA;
    public static final String ANSWER_DESC_CONCEPT = " " + PREFIX_ANSWER + VALID_ANSWER_CONCEPT;

    public static final String INVALID_QUESTION_BODY_DESC = " " + PREFIX_QUESTION;
    public static final String INVALID_ANSWER_DESC = " " + PREFIX_ANSWER;

    public static final EditQuestionCommand.EditQuestionDescriptor DESC_ALGEBRA;
    public static final EditQuestionCommand.EditQuestionDescriptor DESC_CONCEPT;

    // quiz result
    public static final String CORRECT_ANSWER_ALGEBRA = "2";
    public static final String INCORRECT_ANSWER_ALGEBRA = "3";

    public static final String CORRECT_ANSWER_CONCEPT = "Coupling";
    public static final String INCORRECT_ANSWER_CONCEPT = "Dependency";

    public static final String FINISH_TIME_CONCEPT = "2019/10/30 1530";
    public static final String FINISH_TIME_ALGEBRA = "2019/10/31 1300";
    public static final String CORRECT_RESULT = "true";
    public static final String INCORRECT_RESULT = "false";

    static {
        DESC_AMY = new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_AMY).withContent(VALID_CONTENT_AMY).build();
        DESC_BOB = new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_BOB).withContent(VALID_CONTENT_BOB).build();
        DESC_ALGEBRA = new EditQuestionDescriptorBuilder()
                .withQuestionBody(VALID_QUESTION_BODY_ALGEBRA)
                .withAnswer(VALID_ANSWER_ALGEBRA)
                .withSubject(VALID_SUBJECT_ALGEBRA)
                .withDifficulty(VALID_DIFFICULTY_ALGEBRA)
                .build();
        DESC_CONCEPT = new EditQuestionDescriptorBuilder()
                .withQuestionBody(VALID_QUESTION_BODY_CONCEPT)
                .withAnswer(VALID_ANSWER_CONCEPT)
                .withSubject(VALID_SUBJECT_CONCEPT)
                .withDifficulty(VALID_DIFFICULTY_CONCEPT)
                .build();
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
     * - the application data, filtered note list and selected note in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AppData expectedAppData = new AppData(actualModel.getAppData());
        List<Note> expectedFilteredList = new ArrayList<>(actualModel.getFilteredNoteList());
        List<Question> expectedFilteredQuestionList = new ArrayList<>(actualModel.getFilteredQuestionList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAppData, actualModel.getAppData());
        assertEquals(expectedFilteredList, actualModel.getFilteredNoteList());
        assertEquals(expectedFilteredQuestionList, actualModel.getFilteredQuestionList());

    }
    /**
     * Updates {@code model}'s filtered list to show only the note at the given {@code targetIndex} in the
     * {@code model}'s application data.
     */
    public static void showNoteAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredNoteList().size());

        Note note = model.getFilteredNoteList().get(targetIndex.getZeroBased());
        final String[] splitName = note.getTitle().title.split("\\s+");
        model.updateFilteredNoteList(new TitleContainsKeywordsPredicate(Collections.singletonList(splitName[0])));

        assertEquals(1, model.getFilteredNoteList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the question at the given {@code targetIndex} in the
     * {@code model}'s application data.
     */
    public static void showQuestionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredQuestionList().size());

        Question question = model.getFilteredQuestionList().get(targetIndex.getZeroBased());
        final String[] splitName = question.getQuestionBody().body.split("\\s+");
        model.updateFilteredQuestionList(
                new QuestionContainsKeywordsPredicate(Collections.singletonList(splitName[0])));

        assertEquals(1, model.getFilteredQuestionList().size());
    }
}
