package seedu.address.logic.quiz.commands;

import static seedu.address.logic.quiz.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.quiz.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.quiz.commands.CommandTestUtil.deleteFirstQuestion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.Model;
import seedu.address.model.quiz.ModelQuizManager;
import seedu.address.model.quiz.UserPrefs;
import seedu.address.model.quiz.person.Question;
import seedu.address.testutil.QuestionBuilder;

public class RedoCommandTest {
    public static final Question ALICE = new QuestionBuilder().withName("What is alice favourite fruit?")
            .withAnswer("Watermelon").withCategory("Sec4").withType("normal").withTags("friends").build();
    public static final Question BOB = new QuestionBuilder().withName("What is bob favourite fruit?")
            .withAnswer("Banana").withCategory("PrimarySch").withType("high").withTags("owesMoney", "friends").build();

    private final Model model = new ModelQuizManager(new AddressQuizBook(), new UserPrefs());
    private final Model expectedModel = new ModelQuizManager(new AddressQuizBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model.addQuestion(ALICE);
        model.addQuestion(BOB);
        expectedModel.addQuestion(ALICE);
        expectedModel.addQuestion(BOB);

        // set up of both models' undo/redo history
        deleteFirstQuestion(model);
        deleteFirstQuestion(model);
        model.undoQuizBook();
        model.undoQuizBook();

        deleteFirstQuestion(expectedModel);
        deleteFirstQuestion(expectedModel);
        expectedModel.undoQuizBook();
        expectedModel.undoQuizBook();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoQuizBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoQuizBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }
}
