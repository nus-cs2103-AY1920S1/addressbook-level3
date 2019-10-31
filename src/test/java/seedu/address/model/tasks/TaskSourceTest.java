package seedu.address.model.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.DateTime;

public class TaskSourceTest {
    @Test
    void equality_useSameTaskSourceBuilder_valueAsExpected() {
        TaskSourceBuilder tsb = new TaskSourceBuilder("test", DateTime.now());
        TaskSource t1 = new TaskSource(tsb);
        TaskSource t2 = new TaskSource(tsb);
        assertEquals(t1, t2);
    }

    @Test
    void newBuilderCorrectness_useSameArgumentParameters_valueAsExpected() {
        String description = "test";
        DateTime dt = DateTime.now();

        TaskSourceBuilder tsb1 = new TaskSourceBuilder(description, dt);
        TaskSourceBuilder tsb2 = TaskSource.newBuilder(description, dt);

        assertEquals(tsb1.getDescription(), tsb2.getDescription());
        assertEquals(tsb1.getDueDate(), tsb2.getDueDate());
        assertEquals(tsb1.getCompletionStatus(), tsb2.getCompletionStatus());
        assertEquals(tsb1.getTags(), tsb2.getTags());
    }
}
