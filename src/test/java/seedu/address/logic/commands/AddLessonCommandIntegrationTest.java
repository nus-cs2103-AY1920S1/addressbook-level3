package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.LessonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddLessonCommand}.
 */
public class AddLessonCommandIntegrationTest {
    private Model model;


    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalNotebook(), new UserPrefs());
    }

    @Test
    public void execute_newLesson_success() {
        Lesson validLesson = new LessonBuilder().withClassName("Science").build();

        Model expectedModel = new ModelManager(model.getNotebook(), new UserPrefs());
        expectedModel.addLesson(validLesson);

        assertCommandSuccess(new AddLessonCommand(validLesson), model,
                String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson), expectedModel);
    }


    @Test
    public void execute_duplicateLesson_throwsCommandException() {
        Lesson lessonInList = model.getLessonList().get(0);
        assertCommandFailure(new AddLessonCommand(lessonInList), model, AddLessonCommand.MESSAGE_DUPLICATE_LESSON);
    }
}
