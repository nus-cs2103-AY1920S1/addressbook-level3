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

public class UndoCommandTest {
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

        deleteFirstQuestion(expectedModel);
        deleteFirstQuestion(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoQuizBook();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoQuizBook();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }
}
