package seedu.address.logic.commands.question;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.question.TypicalQuestions.getTypicalSavedQuestions;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;

public class QuestionEditCommandTest {

    private Model model = new ModelManager();

    public QuestionEditCommandTest() {
        model.setSavedQuestions(getTypicalSavedQuestions());
    }

    @Test
    public void execute_editQuestionWithDifferentType_success() {
        Index index = Index.fromOneBased(1);
        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "mcq");

        HashMap<String, String> options = new HashMap<>();
        options.put("optionA", "1");
        options.put("optionB", "2");
        options.put("optionC", "3");
        options.put("optionD", "4");
        QuestionEditCommand editCommand = new QuestionEditCommand(index, fields, options);

        Question expectedQuestion = new McqQuestion(fields.get("question"), fields.get("answer"),
            options.get("optionA"), options.get("optionB"), options.get("optionC"),
            options.get("optionD"));
        String expectedMessage = String
            .format(QuestionEditCommand.MESSAGE_SUCCESS, expectedQuestion);

        Model expectedModel = new ModelManager();
        expectedModel.setSavedQuestions(getTypicalSavedQuestions());
        expectedModel.setQuestion(index, expectedQuestion);

        assertCommandSuccess(editCommand, model,
            new CommandResult(expectedMessage, CommandResultType.SHOW_QUESTION), expectedModel);
    }

    @Test
    public void execute_editQuestionWithSameType_success() {
        Index index = Index.fromOneBased(1);
        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "open");
        QuestionEditCommand editCommand = new QuestionEditCommand(index, fields);

        Question expectedQuestion = new OpenEndedQuestion(fields.get("question"),
            fields.get("answer"));
        String expectedMessage = String
            .format(QuestionEditCommand.MESSAGE_SUCCESS, expectedQuestion);

        Model expectedModel = new ModelManager();
        expectedModel.setSavedQuestions(getTypicalSavedQuestions());
        expectedModel.setQuestion(index, expectedQuestion);

        assertCommandSuccess(editCommand, model,
            new CommandResult(expectedMessage, CommandResultType.SHOW_QUESTION), model);
    }

    @Test
    public void execute_invalidQuestionIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getAllQuestions().size() + 1);

        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "open");

        QuestionEditCommand editCommand = new QuestionEditCommand(outOfBoundIndex, fields);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX, ()
            -> editCommand.execute(model));
    }

    @Test
    public void execute_missingMcqOptionsParams_throwsNullPointerException() {
        Index index = Index.fromOneBased(1);

        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "mcq");

        assertThrows(NullPointerException.class, () ->
            new QuestionEditCommand(index, fields).execute(model));

        HashMap<String, String> options = new HashMap<>();
        assertThrows(NullPointerException.class, () ->
            new QuestionEditCommand(index, fields, options).execute(model));
    }

    @Test
    public void execute_emptyMcqOptions_throwsCommandException() {
        Index index = Index.fromOneBased(1);

        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "mcq");

        HashMap<String, String> options = new HashMap<>();
        options.put("optionA", "");
        options.put("optionB", "");
        options.put("optionC", "");
        options.put("optionD", "");

        QuestionEditCommand editCommand = new QuestionEditCommand(index, fields, options);

        assertThrows(CommandException.class, Messages.MESSAGE_MISSING_QUESTION_OPTIONS, ()
            -> editCommand.execute(model));
    }

    @Test
    public void execute_editQuestionWithBlank_throwsCommandException() {
        Index index = Index.fromOneBased(1);
        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "");
        fields.put("answer", "");
        fields.put("type", "");
        QuestionEditCommand editCommand = new QuestionEditCommand(index, fields);

        assertThrows(CommandException.class, Messages.MESSAGE_DUPLICATE_QUESTION, ()
            -> editCommand.execute(model));
    }

    @Test
    public void equals() {
        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "mcq");
        QuestionEditCommand editCommand = new QuestionEditCommand(Index.fromOneBased(1), fields);

        // Same object
        assertTrue(editCommand.equals(editCommand));

        // Null
        assertFalse(editCommand.equals(null));

        // Different index
        QuestionEditCommand editCommandDiffIndex = new QuestionEditCommand(Index.fromOneBased(2),
            fields);
        assertFalse(editCommand.equals(editCommandDiffIndex));

        // Different question
        HashMap<String, String> fieldsDiffQuestion = new HashMap<>();
        fields.put("question", "Test Edit Question");
        fields.put("answer", "Test Answer");
        fields.put("type", "mcq");
        QuestionEditCommand editCommandDiffQuestion = new QuestionEditCommand(Index.fromOneBased(2),
            fieldsDiffQuestion);
        assertFalse(editCommand.equals(editCommandDiffQuestion));

        // Different answer
        HashMap<String, String> fieldsDiffAnswer = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer Different");
        fields.put("type", "mcq");
        QuestionEditCommand editCommandDiffAnswer = new QuestionEditCommand(Index.fromOneBased(1),
            fieldsDiffAnswer);
        assertFalse(editCommand.equals(editCommandDiffAnswer));

        // Different type
        HashMap<String, String> fieldsDiffType = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "open");
        QuestionEditCommand editCommandDiffType = new QuestionEditCommand(Index.fromOneBased(1),
            fieldsDiffType);
        assertFalse(editCommand.equals(editCommandDiffType));
    }

}
