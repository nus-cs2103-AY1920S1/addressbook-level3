package seedu.jarvis.storage.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.planner.TypicalTasks.TODO;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.planner.tasks.Todo;
import seedu.jarvis.testutil.planner.TaskBuilder;

/**
 * Tests the behaviour of {@code JsonAdaptedTodo}.
 */
public class JsonAdaptedTodoTest {

    @Test
    public void toModelType_nullPriorityAndFrequencyNoTags_returnsTodo() throws Exception {
        JsonAdaptedTodo jsonAdaptedTodo = new JsonAdaptedTodo(TODO);
        assertEquals(TODO, jsonAdaptedTodo.toModelType());
    }

    @Test
    public void toModelType_nonNullPriorityAndFrequencyWithTags_returnsTodo() throws Exception {
        Todo todo = new TaskBuilder(TODO).withPriority("HIGH").withFrequency("DAILY").withTags("Todo").buildTodo();
        JsonAdaptedTodo jsonAdaptedTodo = new JsonAdaptedTodo(todo);
        assertEquals(todo, jsonAdaptedTodo.toModelType());
    }

}
