package seedu.jarvis.storage.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.planner.tasks.Todo;

/**
 * Tests the behaviour of {@code JsonAdaptedTodo}.
 */
public class JsonAdaptedTodoTest {

    @Test
    public void toModelType_returnsTodo() throws Exception {
        Todo todo = new Todo("description");
        JsonAdaptedTodo jsonAdaptedTodo = new JsonAdaptedTodo(todo);
        assertEquals(todo, jsonAdaptedTodo.toModelType());
    }
}
