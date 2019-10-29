package seedu.address.model.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.model.DateTime;

public class TaskSourceBuilderTest {

    @Test
    void constructor_dueDateNullAsArgument_shouldThrowException() {
        Assertions.assertThrows(NullPointerException.class, (
            ) -> new TaskSourceBuilder("Test", null));
    }

    @Test
    void constructor_descriptionNullAsArgument_shouldThrowException() {
        Assertions.assertThrows(NullPointerException.class, (
        ) -> new TaskSourceBuilder(null, DateTime.now()));
    }

    @Test
    void constructor_descriptionStoredProperly_valueAsExpected() {
        String description = "Test";
        TaskSourceBuilder tsb = new TaskSourceBuilder(description, DateTime.now());
        assertEquals(tsb.getDescription(), description);
    }

    @Test
    void constructor_dueDateStoredProperly_valueAsExpected() {
        DateTime dateTime = DateTime.now();
        TaskSourceBuilder tsb = new TaskSourceBuilder("test", dateTime);
        assertEquals(dateTime, tsb.getDueDate());
    }

    @Test
    void constructor_completionStatusStoredProperly_valueAsExpected() {
        TaskSourceBuilder tsb = new TaskSourceBuilder("test", DateTime.now());
        assertEquals(false, tsb.getCompletionStatus());
    }
}
