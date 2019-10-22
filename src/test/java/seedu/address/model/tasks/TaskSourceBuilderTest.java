package seedu.address.model.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.model.DateTime;

public class TaskSourceBuilderTest {

    @Test
    void constructor_dueDateNullAsArgument_shouldThrowException() {
        Assertions.assertThrows(NullPointerException.class, (
            ) -> new TaskSourceBuilder("Test", null, true));
    }

    @Test
    void constructor_descriptionNullAsArgument_shouldThrowException() {
        Assertions.assertThrows(NullPointerException.class, (
        ) -> new TaskSourceBuilder(null, DateTime.now(), true));
    }

    @Test
    void constructor_descriptionStoredProperly_valueAsExpected() {
        String description = "Test";
        TaskSourceBuilder tsb = new TaskSourceBuilder(description, DateTime.now(), true);
        assertEquals(tsb.getDescription(), description);
    }

    @Test
    void constructor_dueDateStoredProperly_valueAsExpected() {
        DateTime dateTime = DateTime.now();
        TaskSourceBuilder tsb = new TaskSourceBuilder("test", dateTime, true);
        assertEquals(dateTime, tsb.getDueDate());
    }

    @Test
    void constructor_completionStatusStoredProperly_valueAsExpected() {
        boolean isCompleted = true;
        TaskSourceBuilder tsb = new TaskSourceBuilder("test", DateTime.now(), isCompleted);
        assertTrue(tsb.getCompletionStatus());
    }
}
